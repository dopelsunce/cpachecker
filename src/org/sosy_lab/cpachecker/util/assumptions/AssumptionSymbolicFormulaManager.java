/*
 *  CPAchecker is a tool for configurable software verification.
 *  This file is part of CPAchecker.
 *
 *  Copyright (C) 2007-2010  Dirk Beyer
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
package org.sosy_lab.cpachecker.util.assumptions;

import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.sosy_lab.cpachecker.exceptions.UnrecognizedCCodeException;
import org.sosy_lab.cpachecker.util.symbpredabstraction.interfaces.Formula;

/**
 * Extension of predicate abstraction's symbolic formula manager
 * with new makeAnd method that uses an expression rather
 * than CFA edge
 *
 * @author g.theoduloz
 */
public interface AssumptionSymbolicFormulaManager {

  /**
   * Creates a formula representing an AND of the two argument
   * @param f1 a SymbolicFormula
   * @param p an invariant predicate
   * @return The formula (f1 & e)
   */
  public Formula makeAnd(Formula f1, IASTNode p, String function) throws UnrecognizedCCodeException;

  /**
   * @return a SymbolicFormula representing logical truth
   */
  public Formula makeTrue();
}
