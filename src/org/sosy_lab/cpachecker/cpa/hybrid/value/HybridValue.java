/*
 *  CPAchecker is a tool for configurable software verification.
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
package org.sosy_lab.cpachecker.cpa.hybrid.value;

import org.sosy_lab.cpachecker.cfa.types.c.CType;
import org.sosy_lab.cpachecker.cpa.value.type.Value;

/**
 * Wrapper class for {@code Value}
 */
public class HybridValue {

  private final Value value;
  private final boolean generated;
  private final CType type;

  /**
   * Constructs a new instance of this class
   * @param pValue The internal vaue to represent
   */
  public HybridValue(
      Value pValue,
      boolean pGenerated,
      CType pType) {
    value = pValue;
    generated = pGenerated;
    type = pType;
  }

  /**
   * @return The represented Value instance
   */
  public Value getValue() {
    return value; // no need for a copy, implementations of Value are immutable
  }

  /**
   * Determines whether the hybrid value was generated by the HybridAnalysisCPA
   * or is parsed from an SMT-Solver Formula
   * @return true, if generated, else false
   */
  public boolean isGenerated() {
    return generated;
  }

  /**
   * @return The respective type correlated to the value
   */
  public CType getType() {
    return type;
  }
}