/*
 * CPAchecker is a tool for configurable software verification.
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
package org.sosy_lab.cpachecker.core.waitlist;

import org.sosy_lab.cpachecker.core.interfaces.AbstractState;
import org.sosy_lab.cpachecker.cpa.loopbound.LoopBoundState;
import org.sosy_lab.cpachecker.util.AbstractStates;

/**
 * Waitlist implementation that sorts the abstract states by the number of their loop iterations.
 * States with a more/less (depending on the used factory method) loop iterations are considered
 * first.
 */
public class LoopIterationSortedWaitlist extends AbstractSortedWaitlistWithAbstractState<Integer> {
  private final int multiplier;

  private LoopIterationSortedWaitlist(WaitlistFactory pSecondaryStrategy, int pMultiplier) {
    super(pSecondaryStrategy);
    multiplier = pMultiplier;
  }

  @Override
  protected Integer getSortKey(AbstractState pState) {
    LoopBoundState loopBoundState = AbstractStates.extractStateByType(pState, LoopBoundState.class);
    return (loopBoundState != null)
        ? (multiplier * loopBoundState.getMaxNumberOfIterationsInLoopstackFrame())
        : 0;
  }

  public static WaitlistFactory factory(final WaitlistFactory pSecondaryStrategy) {
    return () -> new LoopIterationSortedWaitlist(pSecondaryStrategy, 1);
  }

  public static WaitlistFactory reversedFactory(final WaitlistFactory pSecondaryStrategy) {
    return () -> new LoopIterationSortedWaitlist(pSecondaryStrategy, -1);
  }
}
