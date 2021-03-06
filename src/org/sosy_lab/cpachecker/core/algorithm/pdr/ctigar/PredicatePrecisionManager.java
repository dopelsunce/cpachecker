/*
 *  CPAchecker is a tool for configurable software verification.
 *  This file is part of CPAchecker.
 *
 *  Copyright (C) 2007-2016  Dirk Beyer
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
package org.sosy_lab.cpachecker.core.algorithm.pdr.ctigar;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.sosy_lab.cpachecker.cfa.CFA;
import org.sosy_lab.cpachecker.cfa.types.c.CSimpleType;
import org.sosy_lab.cpachecker.cfa.types.c.CType;
import org.sosy_lab.cpachecker.core.CPAcheckerResult.Result;
import org.sosy_lab.cpachecker.core.interfaces.Statistics;
import org.sosy_lab.cpachecker.core.reachedset.UnmodifiableReachedSet;
import org.sosy_lab.cpachecker.cpa.predicate.PredicateAbstractionManager;
import org.sosy_lab.cpachecker.util.predicates.AbstractionPredicate;
import org.sosy_lab.cpachecker.util.predicates.pathformula.PathFormula;
import org.sosy_lab.cpachecker.util.predicates.pathformula.PathFormulaManager;
import org.sosy_lab.cpachecker.util.predicates.pathformula.SSAMap;
import org.sosy_lab.cpachecker.util.predicates.smt.BitvectorFormulaManagerView;
import org.sosy_lab.cpachecker.util.predicates.smt.FormulaManagerView;
import org.sosy_lab.java_smt.api.BitvectorFormula;
import org.sosy_lab.java_smt.api.BooleanFormula;
import org.sosy_lab.java_smt.api.BooleanFormulaManager;
import org.sosy_lab.java_smt.api.SolverException;

/** Provides simplified and adapted versions of methods for predicate abstraction and refinement. */
public class PredicatePrecisionManager {

  private final FormulaManagerView fmgr;
  private final BooleanFormulaManager bfmgr;
  private final PredicateAbstractionManager pamgr;
  private final TransitionSystem transition;
  private final Collection<AbstractionPredicate> abstractionPredicates;
  private final AbstractionStatistics stats;

  /**
   * Creates a new PredicatePrecisionManager. The set of abstraction predicates is initialized with
   * predicates of the form (v1 &lt; v2) for all program variables given by the transition system.
   *
   * @param pFmgr The formula manager used to instantiate and manipulate formulas.
   * @param pAbstractionDelegate The component that actually computes the abstractions.
   * @param pPfmgr The path formula manager used to create variables.
   * @param pTransition The transition system that provides the initial condition, all program
   *     variables and the unprimed ssa context.
   * @param pCFA The program's CFA.
   * @param pCompStats The statistics delegator that this predicate manager should be registered at.
   *     It takes care of printing the statistics.
   */
  public PredicatePrecisionManager(
      FormulaManagerView pFmgr,
      PredicateAbstractionManager pAbstractionDelegate,
      PathFormulaManager pPfmgr,
      TransitionSystem pTransition,
      CFA pCFA,
      StatisticsDelegator pCompStats) {
    stats = new AbstractionStatistics();
    Objects.requireNonNull(pCompStats).register(stats);

    fmgr = Objects.requireNonNull(pFmgr);
    bfmgr = Objects.requireNonNull(pFmgr.getBooleanFormulaManager());
    pamgr = Objects.requireNonNull(pAbstractionDelegate);
    transition = Objects.requireNonNull(pTransition);
    abstractionPredicates = new HashSet<>();
    addDefaultPredicates(pPfmgr, pCFA);
  }

  /**
   * Extracts conjuncts from the interpolant and adds parts as new predicates. Splits equalities,
   * ignores the program-counter variable and treats disjunctive parts as a single part.
   */
  private void refinePredicates(BooleanFormula pInterpolant) throws InterruptedException {
    stats.numberOfRefinements++;
    BooleanFormula interpolant = fmgr.uninstantiate(pInterpolant);

    // Filter out program counter
    interpolant =
        fmgr.filterLiterals(
            interpolant,
            lit -> !fmgr.extractVariableNames(lit).contains(transition.programCounterName()));

    for (BooleanFormula part : bfmgr.toConjunctionArgs(interpolant, true)) {
      if (fmgr.isPurelyConjunctive(part)) {
        for (BooleanFormula split : fmgr.splitNumeralEqualityIfPossible(part)) {
          for (AbstractionPredicate ap : pamgr.getPredicatesForAtomsOf(split)) {
            addPredicate(ap);
          }
        }
      } else {
        addPredicate(pamgr.getPredicateFor(part));
      }
    }
  }

  private void addPredicate(AbstractionPredicate pPred) {
    stats.numberOfPredicates++;
    abstractionPredicates.add(pPred);
  }

  /**
   * Refines the known predicates based on the provided interpolant and computes an abstraction of
   * the given formula with the updated predicate set afterwards. Excludes the program-counter
   * literal from the process and keeps it as it is.
   *
   * <p>The given base formula as well as the interpolant are each supposed to be either primed or
   * unprimed. The result is not defined for formulas that are none of the two.
   *
   * @param pBaseFormula The formula to be abstracted.
   * @param pInterpolant The interpolant used as basis for generating new abstraction predicates.
   * @return An abstracted version of pBaseFormula.
   */
  public BooleanFormula refineAndComputeAbstraction(
      BooleanFormula pBaseFormula, BooleanFormula pInterpolant)
      throws InterruptedException, SolverException {
    refinePredicates(pInterpolant);
    return computeAbstraction(pBaseFormula);
  }

  /**
   * Computes an abstraction of a formula based on the known predicates. Excludes the program-
   * counter literal from the process and keeps it as it is.
   *
   * <p>The given base formula is supposed to be either primed or unprimed. The result is not
   * defined for formulas that are none of the two.
   *
   * @param pBaseFormula The formula to be abstracted.
   * @return An abstracted version of pBaseFormula.
   */
  public BooleanFormula computeAbstraction(BooleanFormula pBaseFormula)
      throws InterruptedException, SolverException {
    BooleanFormula base = fmgr.uninstantiate(pBaseFormula);

    // Split formula into pc part and non-pc part. Only create abstraction of non-pc part.
    BooleanFormula pcPart =
        fmgr.filterLiterals(
            base,
            literal ->
                fmgr.extractVariableNames(literal).contains(transition.programCounterName()));
    BooleanFormula nonPcPart = fmgr.filterLiterals(base, literal -> !literal.equals(pcPart));
    BooleanFormula abstracted = pamgr.computeAbstraction(nonPcPart, abstractionPredicates);
    BooleanFormula result = bfmgr.and(pcPart, abstracted);
    return PDRUtils.isPrimed(pBaseFormula, fmgr, transition)
        ? PDRUtils.asPrimed(result, fmgr, transition)
        : PDRUtils.asUnprimed(result, fmgr, transition);
  }

  /** Adds predicates (v1 &lt;v2) for all variables in the program. */
  private void addDefaultPredicates(PathFormulaManager pPfmgr, CFA pCFA) {
    List<String> varNames = new ArrayList<>(transition.allVariableNames());
    if (varNames.size() <= 1) {
      return;
    }

    PathFormula unprimedContext = transition.getUnprimedContext();
    SSAMap ssa = unprimedContext.getSsa();
    BitvectorFormulaManagerView bvfmgr = fmgr.getBitvectorFormulaManager();

    for (int i = 0; i < varNames.size() - 1; ++i) {
      for (int j = i + 1; j < varNames.size(); ++j) {
        String name1 = varNames.get(i);
        String name2 = varNames.get(j);
        CType type1 = ssa.getType(name1);
        CType type2 = ssa.getType(name2);

        if (!areComparableSimpleTypes(type1, type2)) {
          continue;
        }

        BitvectorFormula var1 =
            (BitvectorFormula)
                pPfmgr.makeFormulaForUninstantiatedVariable(
                    name1, type1, unprimedContext.getPointerTargetSet(), false);
        BitvectorFormula var2 =
            (BitvectorFormula)
                pPfmgr.makeFormulaForUninstantiatedVariable(
                    name2, type2, unprimedContext.getPointerTargetSet(), false);

        // Variables are both signed or both unsigned.
        boolean areVarsSigned = pCFA.getMachineModel().isSigned((CSimpleType) type1);
        BooleanFormula var1LessThanVar2 = bvfmgr.lessThan(var1, var2, areVarsSigned);
        AbstractionPredicate newPredicate = pamgr.getPredicateFor(var1LessThanVar2);
        stats.numberOfInitialPredicates++;
        addPredicate(newPredicate);
      }
    }
  }

  private boolean areComparableSimpleTypes(CType pType1, CType pType2) {

    // Don't compare different types or non-CSimpleTypes
    return pType1.getCanonicalType().equals(pType2.getCanonicalType())
        && (pType1 instanceof CSimpleType);
  }

  private static class AbstractionStatistics implements Statistics {

    private int numberOfPredicates = 0;
    private int numberOfRefinements = 0;
    private int numberOfInitialPredicates = 0;

    @Override
    public void printStatistics(PrintStream pOut, Result pResult, UnmodifiableReachedSet pReached) {
      pOut.println("Number of abstraction predicates:        " + numberOfPredicates);
      pOut.println("  Initial predicates:                    " + numberOfInitialPredicates);
      pOut.println(
          "  Derived from interpolants:             "
              + String.valueOf(numberOfPredicates - numberOfInitialPredicates));
      pOut.println("Number of refinements:                   " + numberOfRefinements);
    }

    @Override
    public @Nullable String getName() {
      return "Predicate abstraction";
    }
  }
}
