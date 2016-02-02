/*
 *  CPAchecker is a tool for configurable software verification.
 *  This file is part of CPAchecker.
 *
 *  Copyright (C) 2007-2015  Dirk Beyer
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
package org.sosy_lab.cpachecker.core.algorithm.tiger.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sosy_lab.common.configuration.InvalidConfigurationException;
import org.sosy_lab.cpachecker.core.algorithm.tiger.goals.Goal;
import org.sosy_lab.cpachecker.core.algorithm.tiger.util.TestSuite;
import org.sosy_lab.cpachecker.util.Pair;
import org.sosy_lab.cpachecker.util.predicates.smt.BooleanFormulaManagerView;
import org.sosy_lab.solver.SolverException;
import org.sosy_lab.solver.api.BooleanFormula;

import junit.framework.AssertionFailedError;

public class TigerTestHelper {

  public static Map<String, String> getConfigurationFromPropertiesFile(
      File propertiesFile) {
    Map<String, String> configuration = new HashMap<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(propertiesFile))) {
      String line = null;
      while ((line = reader.readLine()) != null) {
        line = line.trim();
        if (line.startsWith("# ") || line.isEmpty()) {
          continue;
        }

        if (line.startsWith("#include")) {
          String[] pair = line.split(" ");
          if (pair.length != 2) {
            continue;
          } else {
            pair[0] = pair[0].trim();
            pair[1] = pair[1].trim();
          }

          configuration.putAll(getConfigurationFromPropertiesFile(
              new File(propertiesFile.getPath().substring(0,
                  propertiesFile.getPath().lastIndexOf(File.separator)) + "/" + pair[1])));
        }

        String[] pair = line.split("=");
        if (pair.length != 2) {
          continue;
        } else {
          pair[0] = pair[0].trim();
          pair[1] = pair[1].trim();
        }

        if (pair[0].equals("tiger.algorithmConfigurationFile")) {
          pair[1] = "config/" + pair[1];
        }

        configuration.put(pair[0], pair[1]);
      }
      reader.close();
    } catch (IOException e) {
      return null;
    }

    return configuration;
  }

  public static boolean validPresenceConditions(TestSuite pSuite,
      List<Pair<String, String>> pExpectedTestSuite, String pFeatureModel)
          throws InvalidConfigurationException, SolverException, InterruptedException {

    SolverHelper helper = new SolverHelper();
    BooleanFormulaManagerView bfm = helper.getFormulaManager().getBooleanFormulaManager();
    BooleanFormula fm = helper.parseFormula(pFeatureModel);

    for (Goal goal : pSuite.getGoals()) {
      Pair<String, String> expectedGoal = getGoalFromExpectedTestSuite(goal, pExpectedTestSuite);
      if (expectedGoal == null) {
        throw new AssertionFailedError("Expected result for goal " + goal.getName() + " is missing!");
      }
      BooleanFormula expectedPC = helper.parseFormula(expectedGoal.getSecond());

      if (pSuite.isGoalCovered(goal)) {
        // Goal is (partially) feasible
        BooleanFormula goalPC = null;
        if (pSuite.isVariabilityAware()) {
          goalPC = helper.parseFormula(pSuite.dumpRegion(pSuite.getGoalCoverage(goal)).toString());
        } else {
          goalPC = bfm.makeBoolean(true);
        }

        if (fm != null) {
          expectedPC = appendFeatureModel(helper, fm, expectedPC);
          goalPC = appendFeatureModel(helper, fm, goalPC);
        }

        if (!helper.equivalent(expectedPC, goalPC)) {
          throw new AssertionFailedError("Feasible presence condition of "
                + goal.getName() + " does not match with expected feasible presence condition.");
        }
      }

      if (pSuite.isGoalInfeasible(goal)) {
        // Goal is (partially) infeasible
        BooleanFormula goalPC = null;
        if (pSuite.isVariabilityAware()) {
          expectedPC = bfm.not(expectedPC);
          goalPC = helper.parseFormula(pSuite.dumpRegion(pSuite.getInfeasiblePresenceCondition(goal)).toString());
        } else {
          goalPC = bfm.makeBoolean(false);
        }

        if (fm != null) {
          expectedPC = appendFeatureModel(helper, fm, expectedPC);
          goalPC = appendFeatureModel(helper, fm, goalPC);
        }

        if (!helper.equivalent(expectedPC, goalPC)) {
          throw new AssertionFailedError("Infeasible presence condition of " + goal.getName()
                    + " does not match with expected infeasible presence condition.");
        }
      }
    }

    return true;
  }

  private static BooleanFormula appendFeatureModel(SolverHelper helper, BooleanFormula fm,
      BooleanFormula expectedPresenceCondition) {
    expectedPresenceCondition =
        helper.getFormulaManager().getBooleanFormulaManager().and(expectedPresenceCondition,
            fm);
    return expectedPresenceCondition;
  }

  private static Pair<String, String> getGoalFromExpectedTestSuite(Goal pGoal,
      List<Pair<String, String>> pExpectedTestSuite) {
    for (Pair<String, String> pair : pExpectedTestSuite) {
      if (pGoal.getName().equals(pair.getFirst())) { return pair; }
    }
    return null;
  }

}
