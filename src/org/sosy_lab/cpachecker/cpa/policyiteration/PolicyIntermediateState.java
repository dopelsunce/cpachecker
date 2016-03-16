package org.sosy_lab.cpachecker.cpa.policyiteration;

import java.util.Objects;

import org.sosy_lab.cpachecker.cfa.model.CFANode;
import org.sosy_lab.cpachecker.util.predicates.pathformula.PathFormula;

public final class PolicyIntermediateState extends PolicyState {

  /**
   * Formula + SSA associated with the state.
   */
  private final PathFormula pathFormula;

  /**
   * Abstract state representing start of the trace.
   */
  private final PolicyAbstractedState startingAbstraction;

  /**
   * Meta-information for determining the coverage.
   */
  private transient PolicyIntermediateState mergedInto;

  private PolicyIntermediateState(
      CFANode node,
      PathFormula pPathFormula,
      PolicyAbstractedState pStartingAbstraction
      ) {
    super(node);

    pathFormula = pPathFormula;
    startingAbstraction = pStartingAbstraction;
  }

  public static PolicyIntermediateState of(
      CFANode node,
      PathFormula pPathFormula,
      PolicyAbstractedState generatingState
  ) {
    return new PolicyIntermediateState(
        node, pPathFormula, generatingState);
  }

  public void setMergedInto(PolicyIntermediateState other) {
    mergedInto = other;
  }

  public boolean isMergedInto(PolicyIntermediateState other) {
    return other == mergedInto;
  }

  /**
   * @return Starting {@link PolicyAbstractedState} for the starting location.
   */
  public PolicyAbstractedState getGeneratingState() {
    return startingAbstraction;
  }

  public PathFormula getPathFormula() {
    return pathFormula;
  }

  @Override
  public boolean isAbstract() {
    return false;
  }

  @Override
  public String toDOTLabel() {
    return "";
  }

  @Override
  public String toString() {
    return pathFormula.toString() + "\nLength: " + pathFormula.getLength();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getNode(), pathFormula, startingAbstraction, mergedInto);
  }

  @Override
  public boolean equals(Object pObj) {
    if (this == pObj) {
      return true;
    }
    if (pObj instanceof PolicyIntermediateState) {
      PolicyIntermediateState other = (PolicyIntermediateState) pObj;
      return Objects.equals(getNode(), other.getNode())
          && Objects.equals(pathFormula, other.pathFormula)
          && Objects.equals(startingAbstraction, other.startingAbstraction)
          && Objects.equals(mergedInto, other.mergedInto);
    }
    return false;
  }
}
