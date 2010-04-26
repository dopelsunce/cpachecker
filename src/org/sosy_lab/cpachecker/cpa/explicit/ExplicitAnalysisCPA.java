/*
 *  CPAchecker is a tool for configurable software verification.
 *  This file is part of CPAchecker. 
 *
 *  Copyright (C) 2007-2008  Dirk Beyer and Erkan Keremoglu.
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
 *    http://www.cs.sfu.ca/~dbeyer/CPAchecker/
 */
package org.sosy_lab.cpachecker.cpa.explicit;

import org.sosy_lab.cpachecker.cfa.objectmodel.CFAFunctionDefinitionNode;

import org.sosy_lab.common.configuration.Configuration;
import org.sosy_lab.common.configuration.InvalidConfigurationException;
import org.sosy_lab.common.configuration.Option;
import org.sosy_lab.common.configuration.Options;

import org.sosy_lab.cpachecker.core.defaults.AbstractCPAFactory;
import org.sosy_lab.cpachecker.core.defaults.MergeJoinOperator;
import org.sosy_lab.cpachecker.core.defaults.MergeSepOperator;
import org.sosy_lab.cpachecker.core.defaults.StaticPrecisionAdjustment;
import org.sosy_lab.cpachecker.core.defaults.StopSepOperator;
import org.sosy_lab.cpachecker.core.interfaces.AbstractDomain;
import org.sosy_lab.cpachecker.core.interfaces.AbstractElement;
import org.sosy_lab.cpachecker.core.interfaces.CPAFactory;
import org.sosy_lab.cpachecker.core.interfaces.ConfigurableProgramAnalysis;
import org.sosy_lab.cpachecker.core.interfaces.MergeOperator;
import org.sosy_lab.cpachecker.core.interfaces.Precision;
import org.sosy_lab.cpachecker.core.interfaces.PrecisionAdjustment;
import org.sosy_lab.cpachecker.core.interfaces.StopOperator;
import org.sosy_lab.cpachecker.core.interfaces.TransferRelation;

@Options(prefix="cpas.explicit")
public class ExplicitAnalysisCPA implements ConfigurableProgramAnalysis {

  private static class ExplicitAnalysisCPAFactory extends AbstractCPAFactory {
    
    @Override
    public ConfigurableProgramAnalysis createInstance() throws InvalidConfigurationException {
      return new ExplicitAnalysisCPA(getConfiguration());
    }
  }
  
  public static CPAFactory factory() {
    return new ExplicitAnalysisCPAFactory();
  }
  
  @Option(name="merge", toUppercase=true, values={"SEP", "JOIN"})
  private String mergeType = "SEP";
  
  private AbstractDomain abstractDomain;
  private MergeOperator mergeOperator;
  private StopOperator stopOperator;
  private TransferRelation transferRelation;
  private PrecisionAdjustment precisionAdjustment;

  private ExplicitAnalysisCPA(Configuration config) throws InvalidConfigurationException {
    config.inject(this);
    
    ExplicitAnalysisDomain explicitAnalysisDomain = new ExplicitAnalysisDomain ();
    MergeOperator explicitAnalysisMergeOp = null;
    if (mergeType.equals("SEP")){
      explicitAnalysisMergeOp = MergeSepOperator.getInstance();
    } else if (mergeType.equals("JOIN")){
      explicitAnalysisMergeOp = new MergeJoinOperator(explicitAnalysisDomain.getJoinOperator());
    }

    StopOperator explicitAnalysisStopOp = new StopSepOperator(explicitAnalysisDomain.getPartialOrder());

    TransferRelation explicitAnalysisTransferRelation = new ExplicitAnalysisTransferRelation(config);
    
    this.abstractDomain = explicitAnalysisDomain;
    this.mergeOperator = explicitAnalysisMergeOp;
    this.stopOperator = explicitAnalysisStopOp;
    this.transferRelation = explicitAnalysisTransferRelation;
    this.precisionAdjustment = StaticPrecisionAdjustment.getInstance();
  }

  public AbstractDomain getAbstractDomain ()
  {
    return abstractDomain;
  }

  public MergeOperator getMergeOperator ()
  {
    return mergeOperator;
  }

  public StopOperator getStopOperator ()
  {
    return stopOperator;
  }

  public TransferRelation getTransferRelation ()
  {
    return transferRelation;
  }

  @Override
  public AbstractElement getInitialElement (CFAFunctionDefinitionNode node)
  {
    return new ExplicitAnalysisElement();
  }

  @Override
  public Precision getInitialPrecision(CFAFunctionDefinitionNode pNode) {
    return null;
  }

  @Override
  public PrecisionAdjustment getPrecisionAdjustment() {
    return precisionAdjustment;
  }

}
