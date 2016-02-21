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
package org.sosy_lab.cpachecker.pcc.strategy.partialcertificate;


public class WeightedEdge {

  private final WeightedNode startNode;
  private final WeightedNode endNode;
  private final int weight;

  public WeightedNode getStartNode() {
    return startNode;
  }

  public WeightedNode getEndNode() {
    return endNode;
  }

  public int getWeight() {
    return weight;
  }

  public WeightedEdge(WeightedNode pStartNode, WeightedNode pEndNode, int pWeight) {
    super();
    startNode = pStartNode;
    endNode = pEndNode;
    weight = pWeight;
  }



}
