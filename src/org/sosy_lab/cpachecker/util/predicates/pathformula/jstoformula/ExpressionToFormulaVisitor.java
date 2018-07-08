/*
 * CPAchecker is a tool for configurable software verification.
 *  This file is part of CPAchecker.
 *
 *  Copyright (C) 2007-2018  Dirk Beyer
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
package org.sosy_lab.cpachecker.util.predicates.pathformula.jstoformula;

import org.sosy_lab.cpachecker.cfa.ast.js.JSBinaryExpression;
import org.sosy_lab.cpachecker.cfa.ast.js.JSBooleanLiteralExpression;
import org.sosy_lab.cpachecker.cfa.ast.js.JSFloatLiteralExpression;
import org.sosy_lab.cpachecker.cfa.ast.js.JSFunctionCallExpression;
import org.sosy_lab.cpachecker.cfa.ast.js.JSIdExpression;
import org.sosy_lab.cpachecker.cfa.ast.js.JSIntegerLiteralExpression;
import org.sosy_lab.cpachecker.cfa.ast.js.JSNullLiteralExpression;
import org.sosy_lab.cpachecker.cfa.ast.js.JSRightHandSideVisitor;
import org.sosy_lab.cpachecker.cfa.ast.js.JSStringLiteralExpression;
import org.sosy_lab.cpachecker.cfa.ast.js.JSThisExpression;
import org.sosy_lab.cpachecker.cfa.ast.js.JSUnaryExpression;
import org.sosy_lab.cpachecker.cfa.ast.js.JSUndefinedLiteralExpression;
import org.sosy_lab.cpachecker.cfa.model.CFAEdge;
import org.sosy_lab.cpachecker.exceptions.UnrecognizedJSCodeException;
import org.sosy_lab.cpachecker.util.predicates.pathformula.SSAMap.SSAMapBuilder;
import org.sosy_lab.cpachecker.util.predicates.smt.FormulaManagerView;
import org.sosy_lab.java_smt.api.Formula;
import org.sosy_lab.java_smt.api.FormulaType;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class ExpressionToFormulaVisitor
    implements JSRightHandSideVisitor<Formula, UnrecognizedJSCodeException> {

  private final JSToFormulaConverter conv;
  private final CFAEdge       edge;
  private final String        function;
  private final Constraints   constraints;
  protected final FormulaManagerView mgr;
  protected final SSAMapBuilder ssa;

  public ExpressionToFormulaVisitor(
      JSToFormulaConverter pJSToFormulaConverter,
      FormulaManagerView pFmgr,
      CFAEdge pEdge,
      String pFunction,
      SSAMapBuilder pSsa,
      Constraints pConstraints) {

    conv = pJSToFormulaConverter;
    edge = pEdge;
    function = pFunction;
    ssa = pSsa;
    constraints = pConstraints;
    mgr = pFmgr;
  }

  @Override
  public Formula visit(final JSFunctionCallExpression pFunctionCallExpression)
      throws UnrecognizedJSCodeException {
    throw new UnrecognizedJSCodeException("Not implemented yet", pFunctionCallExpression);
  }

  @Override
  public Formula visit(final JSBinaryExpression pBinaryExpression)
      throws UnrecognizedJSCodeException {
    final Formula leftOperand = visit(pBinaryExpression.getOperand1());
    final Formula rightOperand = visit(pBinaryExpression.getOperand2());
    switch (pBinaryExpression.getOperator()) {
      case EQUAL_EQUAL_EQUAL:
        return mgr.makeEqual(leftOperand, rightOperand);
      case NOT_EQUAL_EQUAL:
        return mgr.makeNot(mgr.makeEqual(leftOperand, rightOperand));
      default:
        throw new UnrecognizedJSCodeException("Not implemented yet", pBinaryExpression);
    }
  }

  @Override
  public Formula visit(final JSStringLiteralExpression pStringLiteralExpression)
      throws UnrecognizedJSCodeException {
    throw new UnrecognizedJSCodeException("Not implemented yet", pStringLiteralExpression);
  }

  @Override
  public Formula visit(final JSFloatLiteralExpression pLiteral) {
    return mgr.getFloatingPointFormulaManager()
        .makeNumber(pLiteral.getValue(), FormulaType.getDoublePrecisionFloatingPointType());
  }

  @Override
  public Formula visit(final JSUnaryExpression pUnaryExpression)
      throws UnrecognizedJSCodeException {
    final Formula operand = visit(pUnaryExpression.getOperand());
    switch (pUnaryExpression.getOperator()) {
      case NOT:
        return mgr.makeNot(operand);
      default:
        throw new UnrecognizedJSCodeException("Not implemented yet", pUnaryExpression);
    }
  }

  @Override
  public Formula visit(final JSIntegerLiteralExpression pIntegerLiteralExpression) {
    return mgr.getIntegerFormulaManager().makeNumber(pIntegerLiteralExpression.getValue());
  }

  @Override
  public Formula visit(final JSBooleanLiteralExpression pBooleanLiteralExpression) {
    return conv.bfmgr.makeBoolean(pBooleanLiteralExpression.getValue());
  }

  @Override
  public Formula visit(final JSNullLiteralExpression pNullLiteralExpression)
      throws UnrecognizedJSCodeException {
    throw new UnrecognizedJSCodeException("Not implemented yet", pNullLiteralExpression);
  }

  @Override
  public Formula visit(final JSUndefinedLiteralExpression pUndefinedLiteralExpression)
      throws UnrecognizedJSCodeException {
    throw new UnrecognizedJSCodeException("Not implemented yet", pUndefinedLiteralExpression);
  }

  @Override
  public Formula visit(final JSThisExpression pThisExpression) throws UnrecognizedJSCodeException {
    throw new UnrecognizedJSCodeException("Not implemented yet", pThisExpression);
  }

  @Override
  public Formula visit(final JSIdExpression pIdExpression) {
    return conv.makeVariable(
        pIdExpression.getDeclaration().getQualifiedName(),
        pIdExpression.getExpressionType(),
        ssa);
  }
}