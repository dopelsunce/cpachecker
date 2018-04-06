/*
 *  CPAchecker is a tool for configurable software verification.
 *  This file is part of CPAchecker.
 *
 *  Copyright (C) 2007-2014  Dirk Beyer
 *  All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 *  CPAchecker web page:
 *    http://cpachecker.sosy-lab.org
 */
package org.sosy_lab.cpachecker.cfa.postprocessing.function;

import static com.google.common.collect.FluentIterable.from;
import static org.sosy_lab.cpachecker.util.CFAUtils.leavingEdges;

import com.google.common.base.Functions;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import org.sosy_lab.common.configuration.Configuration;
import org.sosy_lab.common.configuration.InvalidConfigurationException;
import org.sosy_lab.common.configuration.Option;
import org.sosy_lab.common.configuration.Options;
import org.sosy_lab.common.log.LogManager;
import org.sosy_lab.cpachecker.cfa.MutableCFA;
import org.sosy_lab.cpachecker.cfa.ast.ADeclaration;
import org.sosy_lab.cpachecker.cfa.ast.AStatement;
import org.sosy_lab.cpachecker.cfa.ast.c.CDeclaration;
import org.sosy_lab.cpachecker.cfa.ast.c.CExpression;
import org.sosy_lab.cpachecker.cfa.ast.c.CFieldReference;
import org.sosy_lab.cpachecker.cfa.ast.c.CFunctionCall;
import org.sosy_lab.cpachecker.cfa.ast.c.CFunctionCallExpression;
import org.sosy_lab.cpachecker.cfa.ast.c.CIdExpression;
import org.sosy_lab.cpachecker.cfa.ast.c.CPointerExpression;
import org.sosy_lab.cpachecker.cfa.ast.c.CSimpleDeclaration;
import org.sosy_lab.cpachecker.cfa.ast.c.CVariableDeclaration;
import org.sosy_lab.cpachecker.cfa.model.CFAEdge;
import org.sosy_lab.cpachecker.cfa.model.CFANode;
import org.sosy_lab.cpachecker.cfa.model.FunctionEntryNode;
import org.sosy_lab.cpachecker.cfa.model.c.CFunctionEntryNode;
import org.sosy_lab.cpachecker.cfa.model.c.CStatementEdge;
import org.sosy_lab.cpachecker.cfa.types.c.CFunctionType;
import org.sosy_lab.cpachecker.cfa.types.c.CFunctionTypeWithNames;
import org.sosy_lab.cpachecker.cfa.types.c.CPointerType;
import org.sosy_lab.cpachecker.cfa.types.c.CTypes;
import org.sosy_lab.cpachecker.util.CFATraversal;
import org.sosy_lab.cpachecker.util.Pair;

/**
 * This class is responsible for replacing calls via function pointers like (*fp)() with code
 * similar to the following: if (fp == &f) f(); else if (fp == &g) f(); else (*fp)();
 *
 * <p>The set of candidate functions used is configurable. No actual call edges to the other
 * functions are introduced. The inserted function call statements look just like regular functions
 * call statements. The edge in the "else" branch is optional and configurable.
 */
@Options
public class CFunctionPointerResolver {

  @Option(
    secure = true,
    name = "analysis.matchAssignedFunctionPointers",
    description =
        "Use as targets for call edges only those shich are assigned to the particular expression (structure field)."
  )
  private boolean matchAssignedFunctionPointers = false;

  @Option(
    secure = true,
    name = "analysis.matchAssignedFunctionPointers.ignoreUnknownAssignments",
    description =
        "If a no target function was assigned to a function pointer,"
            + " use the origin heuristic instead of replacing with empty calls"
  )
  private boolean ignoreUnknownAssignments = false;

  static enum FunctionSet {
    // The items here need to be declared in the order they should be used when checking function.
    ALL, // all defined functions considered (Warning: some CPAs require at least EQ_PARAM_SIZES)
    USED_IN_CODE, // includes only functions which address is taken in the code
    EQ_PARAM_COUNT, // all functions with matching number of parameters considered
    EQ_PARAM_SIZES, // all functions with parameters with matching sizes
    EQ_PARAM_TYPES, // all functions with matching number and types of parameters considered
    RETURN_VALUE, // void functions are not considered for assignments
  }

  @Option(
    secure = true,
    name = "analysis.replaceFunctionWithParameterPointer",
    description = "Use if you are going to change function with function pionter parameter"
  )
  private boolean replaceFunctionWithParameterPointer = false;

  @Option(
    secure = true,
    name = "analysis.functionPointerTargets",
    description = "potential targets for call edges created for function pointer calls"
  )
  private Set<FunctionSet> functionSets =
      ImmutableSet.of(
          FunctionSet.USED_IN_CODE,
          FunctionSet.RETURN_VALUE,
          FunctionSet.EQ_PARAM_TYPES,
          FunctionSet.EQ_PARAM_SIZES,
          FunctionSet.EQ_PARAM_COUNT);

  @Option(
    secure = true,
    name = "analysis.functionPointerParameterTargets",
    description = "potential targets for call edges created for function pointer parameter calls"
  )
  private Set<FunctionSet> functionParameterSets =
      ImmutableSet.of(
          FunctionSet.USED_IN_CODE, FunctionSet.RETURN_VALUE, FunctionSet.EQ_PARAM_TYPES);

  private final TargetFunctionsProvider targetFunctionsProvider;
  private final TargetFunctionsProvider targetParameterFunctionsProvider;

  private final MutableCFA cfa;
  private final LogManager logger;
  private final Configuration pConfig;

  public CFunctionPointerResolver(
      MutableCFA pCfa,
      List<Pair<ADeclaration, String>> pGlobalVars,
      Configuration config,
      LogManager pLogger)
      throws InvalidConfigurationException {
    cfa = pCfa;
    logger = pLogger;
    pConfig = config;

    config.inject(this);

    targetFunctionsProvider = createMatchingFunctions(pGlobalVars, functionSets);
    targetParameterFunctionsProvider = createMatchingFunctions(pGlobalVars, functionParameterSets);
  }

  private TargetFunctionsProvider createMatchingFunctions(
      List<Pair<ADeclaration, String>> pGlobalVars, Collection<FunctionSet> functionSets) {
    if (functionSets.contains(FunctionSet.USED_IN_CODE)) {
      CReferencedFunctionsCollector varCollector;
      Collection<FunctionEntryNode> candidateFunctions;

      if (matchAssignedFunctionPointers) {
        varCollector = new CReferencedFunctionsCollectorWithFieldsMatching();
      } else {
        varCollector = new CReferencedFunctionsCollector();
      }
      for (CFANode node : cfa.getAllNodes()) {
        for (CFAEdge edge : leavingEdges(node)) {
          varCollector.visitEdge(edge);
        }
      }
      for (Pair<ADeclaration, String> decl : pGlobalVars) {
        if (decl.getFirst() instanceof CVariableDeclaration) {
          CVariableDeclaration varDecl = (CVariableDeclaration) decl.getFirst();
          varCollector.visitDeclaration(varDecl);
        }
      }
      Set<String> addressedFunctions = varCollector.getCollectedFunctions();
      candidateFunctions =
          from(Sets.intersection(addressedFunctions, cfa.getAllFunctionNames()))
              .transform(Functions.forMap(cfa.getAllFunctions()))
              .toList();

      if (logger.wouldBeLogged(Level.ALL)) {
        logger.log(
            Level.ALL,
            "Possible target functions of function pointers:\n",
            Joiner.on('\n').join(candidateFunctions));
      }

      if (matchAssignedFunctionPointers) {
        return new TargetFunctionsProvider(
            cfa.getMachineModel(),
            logger,
            functionSets,
            candidateFunctions,
            ((CReferencedFunctionsCollectorWithFieldsMatching) varCollector).getFieldMatching(),
            ((CReferencedFunctionsCollectorWithFieldsMatching) varCollector).getGlobalsMatching());
      } else {
        return new TargetFunctionsProvider(
            cfa.getMachineModel(), logger, functionSets, candidateFunctions);
      }
    } else {
      return new TargetFunctionsProvider(
          cfa.getMachineModel(), logger, functionSets, cfa.getAllFunctionHeads());
    }
  }

  /**
   * This method traverses the whole CFA, potentially replacing function pointer calls with regular
   * function calls.
   */
  public void resolveFunctionPointers() throws InvalidConfigurationException {

    // 1.Step: get all function calls
    final FunctionPointerCallCollector visitor = new FunctionPointerCallCollector();
    for (FunctionEntryNode functionStartNode : cfa.getAllFunctionHeads()) {
      CFATraversal.dfs().traverseOnce(functionStartNode, visitor);
    }

    // 2.Step: replace functionCalls with functioncall- and return-edges
    // This loop replaces function pointer calls inside the given function with regular function
    // calls.

    final EdgeReplacerFunctionPointer edgeReplacerFunctionPointer =
        new EdgeReplacerFunctionPointer(cfa, pConfig, logger);
    for (final CStatementEdge edge : visitor.functionPointerCalls) {
      CFunctionCall functionCall = (CFunctionCall) edge.getStatement();
      CFunctionCallExpression fExp = functionCall.getFunctionCallExpression();
      CExpression nameExp = fExp.getFunctionNameExpression();
      CFunctionType func = (CFunctionType) nameExp.getExpressionType().getCanonicalType();
      logger.log(Level.FINEST, "Function pointer call", fExp);
      Collection<CFunctionEntryNode> funcs = getTargets(nameExp, func, targetFunctionsProvider);

      // need only to remove the symbol "*"
      if (nameExp instanceof CPointerExpression) {
        CExpression operand = ((CPointerExpression) nameExp).getOperand();
        if (CTypes.isFunctionPointer(operand.getExpressionType())) {
          nameExp = operand;
        }
      }
      edgeReplacerFunctionPointer.instrument(edge, funcs, nameExp);
    }

    EdgeReplacerParameterFunctionPointer edgeReplacerParameterFunctionPointer =
        new EdgeReplacerParameterFunctionPointer(cfa, pConfig, logger);
    for (final CStatementEdge edge : visitor.functionParameterPointerCalls) {
      CExpression param = getParameter((CFunctionCall) edge.getStatement());
      CFunctionType func =
          (CFunctionType) ((CPointerType) param.getExpressionType()).getType().getCanonicalType();
      logger.log(Level.FINEST, "Function pointer param", param);
      Collection<CFunctionEntryNode> funcs =
          getTargets(param, func, targetParameterFunctionsProvider);
      edgeReplacerParameterFunctionPointer.instrument(edge, funcs, param);
    }
  }

  private CExpression getParameter(CFunctionCall call) {
    for (CExpression param : call.getFunctionCallExpression().getParameterExpressions()) {
      if (param.getExpressionType() instanceof CPointerType
          && ((CPointerType) param.getExpressionType()).getType() instanceof CFunctionTypeWithNames
          && ((param instanceof CIdExpression
                  && ((CIdExpression) param).getDeclaration().getType() instanceof CPointerType)
              || (param instanceof CFieldReference))) {
        return param;
      }
    }
    return null;
  }

  private boolean isFunctionPointerCall(CFunctionCall call) {
    CFunctionCallExpression callExpr = call.getFunctionCallExpression();
    if (callExpr.getDeclaration() != null) {
      // "f()" where "f" is a declared function
      return false;
    }

    CExpression nameExpr = callExpr.getFunctionNameExpression();
    if (nameExpr instanceof CIdExpression && ((CIdExpression) nameExpr).getDeclaration() == null) {
      // "f()" where "f" is an undefined identifier
      // Someone calls an undeclared function.
      return false;
    }

    // Either "exp()" where "exp" is a more complicated expression,
    // or "f()" where "f" is a variable.
    return true;
  }

  private Collection<CFunctionEntryNode> getTargets(
      CExpression nameExp, CFunctionType func, TargetFunctionsProvider targetFunctions) {
    Collection<CFunctionEntryNode> funcs = targetFunctions.getFunctionSet(func);

    if (matchAssignedFunctionPointers) {
      CExpression expression = nameExp;
      if (expression instanceof CPointerExpression) {
        expression = ((CPointerExpression) expression).getOperand();
      }

      final Set<String> matchedFuncs = targetFunctions.getMatchedFunc(expression);
      if (matchedFuncs.isEmpty() && !ignoreUnknownAssignments) {
        CSimpleDeclaration decl = null;
        if (expression instanceof CIdExpression) {
          decl = ((CIdExpression) expression).getDeclaration();
        }
        if (decl == null) {
          funcs = Collections.emptySet();
        } else if (decl instanceof CDeclaration && ((CDeclaration) decl).isGlobal()) {
          // TODO means, that our heuristics missed something
          funcs = Collections.emptySet();
        }
      } else {
        funcs = from(funcs).filter(f -> matchedFuncs.contains(f.getFunctionName())).toSet();
      }
    }

    if (funcs.isEmpty()) {
      // no possible targets, we leave the CFA unchanged and print a warning
      logger.logf(
          Level.WARNING,
          "%s: Function pointer %s with type %s is called,"
              + " but no possible target functions were found.",
          nameExp.getFileLocation(),
          nameExp.toASTString(),
          nameExp.getExpressionType().toASTString("*"));
    } else {
      logger.log(
          Level.FINEST,
          "Inserting edges for the function pointer",
          nameExp.toASTString(),
          "with type",
          nameExp.getExpressionType().toASTString("*"),
          "to the functions",
          from(funcs).transform(CFunctionEntryNode::getFunctionName));
    }

    return funcs;
  }

  /**
   * This Visitor collects all functioncalls for functionPointers. It should visit the CFA of each
   * functions before creating super-edges (functioncall- and return-edges).
   */
  private class FunctionPointerCallCollector extends CFATraversal.DefaultCFAVisitor {
    final List<CStatementEdge> functionPointerCalls = new ArrayList<>();
    final List<CStatementEdge> functionParameterPointerCalls = new ArrayList<>();

    @Override
    public CFATraversal.TraversalProcess visitEdge(final CFAEdge pEdge) {
      if (pEdge instanceof CStatementEdge) {
        final CStatementEdge edge = (CStatementEdge) pEdge;
        final AStatement stmt = edge.getStatement();
        if (checkEdge(stmt)) {
          functionPointerCalls.add(edge);
        }
        if (replaceFunctionWithParameterPointer && checkParameterEdge(stmt)) {
          functionParameterPointerCalls.add(edge);
        }
      }
      return CFATraversal.TraversalProcess.CONTINUE;
    }

    private boolean checkEdge(AStatement stmt) {
      if (stmt instanceof CFunctionCall && isFunctionPointerCall((CFunctionCall) stmt)) {
        return true;
      }
      return false;
    }

    private boolean checkParameterEdge(AStatement stmt) {
      if (stmt instanceof CFunctionCall
          && !isFunctionPointerCall((CFunctionCall) stmt)
          && getParameter((CFunctionCall) stmt) != null) {
        return true;
      }
      return false;
    }
  }
}
