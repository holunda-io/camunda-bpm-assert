package org.camunda.bpm.engine.test.assertions.cmmn;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.MapAssert;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.history.HistoricCaseActivityInstance;
import org.camunda.bpm.engine.history.HistoricCaseActivityInstanceQuery;
import org.camunda.bpm.engine.impl.cmmn.entity.runtime.CaseExecutionEntity;
import org.camunda.bpm.engine.impl.cmmn.execution.CaseExecutionState;
import org.camunda.bpm.engine.impl.persistence.entity.HistoricCaseActivityInstanceEntity;
import org.camunda.bpm.engine.runtime.CaseExecution;
import org.camunda.bpm.engine.runtime.CaseExecutionQuery;
import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.test.assertions.bpmn.AbstractProcessAssert;
import org.camunda.bpm.engine.test.assertions.bpmn.TaskAssert;
import org.camunda.bpm.model.cmmn.impl.CmmnModelConstants;

import java.util.Arrays;
import java.util.Map;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 * @author Martin Günther <martin.guenther@holisticon.de>
 */
public abstract class AbstractCaseAssert<S extends AbstractCaseAssert<S, A>, A extends CaseExecution> extends AbstractProcessAssert<S, A> {

  /**
   * Delivers the the actual object under test.
   */
  public A getActual() {
    return actual;
  }

  protected AbstractCaseAssert(ProcessEngine engine, A actual, Class<?> selfType) {
    super(engine, actual, selfType);
  }

  /**
   * Verifies the expectation that the {@link CaseExecution} is 'available'.
   * <p>
   * Change visibility to public for those inheriting classes for whose
   * underlying objects under test this status makes sense
   *
   * @return this
   */
  protected S isAvailable() {
    return hasState(CaseExecutionState.AVAILABLE);
  }

  /**
   * Verifies the expectation that the {@link CaseExecution} is 'enabled'.
   * <p>
   * Change visibility to public for those inheriting classes for whose
   * underlying objects under test this status makes sense
   *
   * @return this
   */
  protected S isEnabled() {
    return hasState(CaseExecutionState.ENABLED);
  }

  /**
   * Verifies the expectation that the {@link CaseExecution} is 'disabled'.
   * <p>
   * Change visibility to public for those inheriting classes for whose
   * underlying objects under test this status makes sense
   *
   * @return this
   */
  protected S isDisabled() {
    return hasState(CaseExecutionState.DISABLED);
  }

  /**
   * Verifies the expectation that the {@link CaseExecution} is 'active'.
   * <p>
   * Change visibility to public for those inheriting classes for whose
   * underlying objects under test this status makes sense
   *
   * @return this
   */
  protected S isActive() {
    return hasState(CaseExecutionState.ACTIVE);
  }

  /**
   * Verifies the expectation that the {@link CaseExecution} is 'suspended'.
   * <p>
   * Change visibility to public for those inheriting classes for whose
   * underlying objects under test this status makes sense
   *
   * @return this
   */
  protected S isSuspended() {
    return hasState(CaseExecutionState.SUSPENDED);
  }

  /**
   * Verifies the expectation that the {@link CaseExecution} is 'completed'.
   * <p>
   * Change visibility to public for those inheriting classes for whose
   * underlying objects under test this status makes sense
   *
   * @return this
   */
  protected S isCompleted() {
    return hasState(CaseExecutionState.COMPLETED);
  }

  /**
   * Verifies the expectation that the {@link CaseExecution} is 'closed'.
   * <p>
   * Change visibility to public for those inheriting classes for whose
   * underlying objects under test this status makes sense
   *
   * @return this
   */
  protected S isClosed() {
    return hasState(CaseExecutionState.CLOSED);
  }

  /**
   * Verifies the expectation that the {@link CaseExecution} is 'failed'.
   * <p>
   * Change visibility to public for those inheriting classes for whose
   * underlying objects under test this status makes sense
   *
   * @return this
   */
  protected S isFailed() {
    return hasState(CaseExecutionState.FAILED);
  }

  /**
   * Verifies the expectation that the {@link CaseExecution} is 'terminated'.
   * <p>
   * Change visibility to public for those inheriting classes for whose
   * underlying objects under test this status makes sense
   *
   * @return this
   */
  protected S isTerminated() {
    return hasState(CaseExecutionState.TERMINATED);
  }

  /**
   * Verifies the expectation that the {@link CaseExecution} is the
   * 'case' instance.
   * <p>
   * Change visibility to public for those inheriting classes for whose
   * underlying objects under test this assertion makes sense
   *
   * @return this
   */
  protected CaseInstanceAssert isCaseInstance() {
    Assertions.assertThat(actual).isNotNull();
    Assertions.assertThat(actual.getCaseInstanceId())
      .overridingErrorMessage("Expected %s to be the case instance, but found it not to be!").isEqualTo(actual.getId());
    return CaseInstanceAssert.assertThat(engine, (CaseInstance) actual);
  }

  /**
   * Verifies the expectation that the {@link CaseExecution} is a 'stage'
   * <p>
   * Change visibility to public for those inheriting classes for whose
   * underlying objects under test this assertion makes sense
   *
   * @return this
   */
  protected StageAssert isStage() {
    hasType(CmmnModelConstants.CMMN_ELEMENT_STAGE);
    return StageAssert.assertThat(engine, actual);
  }

  /**
   * Verifies the expectation that the {@link CaseExecution} is a 'humanTask'
   * <p>
   * Change visibility to public for those inheriting classes for whose
   * underlying objects under test this assertion makes sense
   *
   * @return this
   */
  protected HumanTaskAssert isHumanTask() {
    hasType(CmmnModelConstants.CMMN_ELEMENT_HUMAN_TASK);
    return HumanTaskAssert.assertThat(engine, actual);
  }

  /**
   * Verifies the expectation that the {@link CaseExecution} is a 'processTask'
   * <p>
   * Change visibility to public for those inheriting classes for whose
   * underlying objects under test this assertion makes sense
   *
   * @return this
   */
  protected ProcessTaskAssert isProcessTask() {
    hasType(CmmnModelConstants.CMMN_ELEMENT_PROCESS_TASK);
    return ProcessTaskAssert.assertThat(engine, actual);
  }

  /**
   * Verifies the expectation that the {@link CaseExecution} is a 'caseTask'
   * <p>
   * Change visibility to public for those inheriting classes for whose
   * underlying objects under test this assertion makes sense
   *
   * @return this
   */
  protected CaseTaskAssert isCaseTask() {
    hasType(CmmnModelConstants.CMMN_ELEMENT_CASE_TASK);
    return CaseTaskAssert.assertThat(engine, actual);
  }

  /**
   * Verifies the expectation that the {@link CaseExecution} is a 'milestone'
   * <p>
   * Change visibility to public for those inheriting classes for whose
   * underlying objects under test this assertion makes sense
   *
   * @return this
   */
  protected MilestoneAssert isMilestone() {
    hasType(CmmnModelConstants.CMMN_ELEMENT_MILESTONE);
    return MilestoneAssert.assertThat(engine, actual);
  }

  /**
   * Retrieve a child {@link CaseExecution} currently available in the context of
   * the actual caseExecution under test of this AbstractCaseAssert. The query is
   * automatically narrowed down to the actual caseExecution under test of this
   * assertion.
   *
   * @param query CaseExecutionQuery further narrowing down the search for
   *              caseExecutions. The query is automatically narrowed down to the
   *              actual 'parent' CaseExecution under test of this assertion.
   * @return the only CaseExecution resulting  from the given search. A 'null'
   * CaseExecution in case no such CaseExecution is available.
   * @throws org.camunda.bpm.engine.ProcessEngineException in case more than
   *                                                       one CaseExecution is delivered by the query (after being narrowed
   *                                                       to the actual 'parent' CaseExecution)
   * @see #descendantCaseExecution to find grandchilds and other descendants, too.
   */
  protected CaseExecutionAssert caseExecution(final CaseExecutionQuery query) {
    CaseExecutionAssert caseExecutionAssert = descendantCaseExecution(query);
    CaseExecution caseExecution = caseExecutionAssert.getActual();
    if (caseExecution != null)
      Assertions.assertThat(caseExecution.getParentId()).isEqualTo(actual.getId());
    return new CaseExecutionAssert(engine, caseExecution);
  }

  /**
   * Retrieve any descendant {@link CaseExecution} currently available in the context of
   * the actual caseInstance under test of this AbstractCaseAssert. The query is
   * automatically narrowed down to the actual caseExecution under test of this
   * assertion.
   *
   * @param query CaseExecutionQuery further narrowing down the search for
   *              caseExecutions. The query is automatically narrowed down to the
   *              actual 'parent' CaseExecution under test of this assertion.
   * @return the only CaseExecution resulting  from the given search. A 'null'
   * CaseExecution in case no such CaseExecution is available.
   * @throws org.camunda.bpm.engine.ProcessEngineException in case more than
   *                                                       one CaseExecution is delivered by the query (after being narrowed
   *                                                       to the actual 'parent' CaseExecution)
   * @see #caseExecution to find only children of this {@link CaseExecution}
   */
  protected CaseExecutionAssert descendantCaseExecution(CaseExecutionQuery query) {
    if (query == null)
      throw new IllegalArgumentException("Illegal call of caseExecution(query = 'null') - but must not be null!");
    isNotNull();
    @SuppressWarnings("unchecked")
    A caseExecution = (A) query.singleResult();
    return new CaseExecutionAssert(engine, caseExecution);
  }

  /**
   * Retrieve a chained {@link CaseExecution} currently available in the context of
   * the actual caseExecution under test of this AbstractCaseAssert. The query is
   * automatically narrowed down to the actual caseExecution under test of this
   * assertion.
   *
   * @param activityId activity id further narrowing down the search for
   *                   caseExecutions. The query is automatically narrowed down to the
   *                   actual 'parent' CaseExecution under test of this assertion.
   * @return the only CaseExecution with the given activity id. A 'null'
   * CaseExecution in case no such CaseExecution is available.
   * @throws org.camunda.bpm.engine.ProcessEngineException in case more than
   *                                                       one CaseExecution is delivered by the query (after being narrowed
   *                                                       to the actual 'parent' CaseExecution)
   */
  protected CaseExecutionAssert caseExecution(final String activityId) {
    return caseExecution(caseExecutionQuery().activityId(activityId));
  }

  /**
   * Retrieve a chained {@link CaseExecution} currently available in the context of
   * the actual caseExecution under test of this AbstractCaseAssert. The query is
   * automatically narrowed down to the actual caseExecution under test of this
   * assertion.
   *
   * @return the only CaseExecution existing in the context of this case execution.
   * A 'null' CaseExecution in case no such CaseExecution is available.
   * @throws org.camunda.bpm.engine.ProcessEngineException in case more than
   *                                                       one CaseExecution is delivered by the query (after being narrowed
   *                                                       to the actual 'parent' CaseExecution)
   */
  protected CaseExecutionAssert caseExecution() {
    return caseExecution(caseExecutionQuery());
  }

  /**
   * Enter into a chained {@link HumanTaskAssert} inspecting the one and mostly
   * one 'humanTask' currently available in the context of the actual caseExecution
   * under test of this AbstractCaseAssert. The query is automatically narrowed down
   * to the actual caseExecution under test of this assertion as well as to the type
   * 'humanTask'.
   * <p>
   * Change visibility to public for those inheriting classes for whose
   * underlying objects under test this method makes sense
   *
   * @param query CaseExecutionQuery further narrowing down the search for
   *              caseExecutions. The query is automatically narrowed down to the
   *              actual 'parent' CaseExecution under test of this assertion as well as
   *              to the type 'humanTask'.
   * @return the only CaseExecution resulting from the given search. A 'null'
   * CaseExecution in case no such CaseExecution is available.
   * @throws org.camunda.bpm.engine.ProcessEngineException in case more than
   *                                                       one CaseExecution is delivered by the query (after being narrowed
   *                                                       to the actual 'parent' CaseExecution)
   */
  protected HumanTaskAssert humanTask(final CaseExecutionQuery query) {
    CaseExecution caseExecution = caseExecution(query).getActual(); //TODO extract and use lookup code from caseExecution(query)
    return CaseExecutionAssert.assertThat(engine, caseExecution).isHumanTask();
  }

  /**
   * Enter into a chained {@link HumanTaskAssert} inspecting the one and mostly
   * one 'humanTask' currently available in the context of the actual caseExecution
   * under test of this AbstractCaseAssert. The query is automatically narrowed down
   * to the actual caseExecution under test of this assertion as well as to the type
   * 'humanTask'.
   * <p>
   * Change visibility to public for those inheriting classes for whose
   * underlying objects under test this method makes sense
   *
   * @param activityId activity if further narrowing down the search for
   *                   caseExecutions. The query is automatically narrowed down to the
   *                   actual 'parent' CaseExecution under test of this assertion as well as
   *                   to the type 'humanTask'.
   * @return the only CaseExecution with the given activitiy id. A 'null'
   * CaseExecution in case no such CaseExecution is available.
   * @throws org.camunda.bpm.engine.ProcessEngineException in case more than
   *                                                       one CaseExecution is delivered by the query (after being narrowed
   *                                                       to the actual 'parent' CaseExecution)
   */
  protected HumanTaskAssert humanTask(final String activityId) {
    return humanTask(caseExecutionQuery().activityId(activityId));
  }

  /**
   * Enter into a chained {@link HumanTaskAssert} inspecting the one and mostly
   * one 'humanTask' currently available in the context of the actual caseExecution
   * under test of this AbstractCaseAssert. The query is automatically narrowed down
   * to the actual caseExecution under test of this assertion as well as to the type
   * 'humanTask'.
   * <p>
   * Change visibility to public for those inheriting classes for whose
   * underlying objects under test this method makes sense
   *
   * @return the only CaseExecution existing in the context of this case execution.
   * A 'null' CaseExecution in case no such CaseExecution is available.
   * @throws org.camunda.bpm.engine.ProcessEngineException in case more than
   *                                                       one CaseExecution is delivered by the query (after being narrowed
   *                                                       to the actual 'parent' CaseExecution)
   */
  protected HumanTaskAssert humanTask() {
    return humanTask(caseExecutionQuery());
  }

  /**
   * Enter into a chained {@link CaseTaskAssert} inspecting the one and mostly
   * one 'caseTask' currently available in the context of the actual caseExecution
   * under test of this AbstractCaseAssert. The query is automatically narrowed down
   * to the actual caseExecution under test of this assertion as well as to the type
   * 'caseTask'.
   * <p>
   * Change visibility to public for those inheriting classes for whose
   * underlying objects under test this method makes sense
   *
   * @param query CaseExecutionQuery further narrowing down the search for
   *              caseExecutions. The query is automatically narrowed down to the
   *              actual 'parent' CaseExecution under test of this assertion as well as
   *              to the type 'caseTask'.
   * @return the only CaseExecution resulting from the given search. A 'null'
   * CaseExecution in case no such CaseExecution is available.
   * @throws org.camunda.bpm.engine.ProcessEngineException in case more than
   *                                                       one CaseExecution is delivered by the query (after being narrowed
   *                                                       to the actual 'parent' CaseExecution)
   */
  protected CaseTaskAssert caseTask(final CaseExecutionQuery query) {
    CaseExecution caseExecution = caseExecution(query).getActual(); //TODO extract and use lookup code from caseExecution(query)
    return CaseExecutionAssert.assertThat(engine, caseExecution).isCaseTask();
  }

  /**
   * Enter into a chained {@link CaseTaskAssert} inspecting the one and mostly
   * one 'caseTask' currently available in the context of the actual caseExecution
   * under test of this AbstractCaseAssert. The query is automatically narrowed down
   * to the actual caseExecution under test of this assertion as well as to the type
   * 'caseTask'.
   * <p>
   * Change visibility to public for those inheriting classes for whose
   * underlying objects under test this method makes sense
   *
   * @param activityId activity if further narrowing down the search for
   *                   caseExecutions. The query is automatically narrowed down to the
   *                   actual 'parent' CaseExecution under test of this assertion as well as
   *                   to the type 'caseTask'.
   * @return the only CaseExecution with the given activitiy id. A 'null'
   * CaseExecution in case no such CaseExecution is available.
   * @throws org.camunda.bpm.engine.ProcessEngineException in case more than
   *                                                       one CaseExecution is delivered by the query (after being narrowed
   *                                                       to the actual 'parent' CaseExecution)
   */
  protected CaseTaskAssert caseTask(final String activityId) {
    return caseTask(caseExecutionQuery().activityId(activityId));
  }

  /**
   * Enter into a chained {@link CaseTaskAssert} inspecting the one and mostly
   * one 'caseTask' currently available in the context of the actual caseExecution
   * under test of this AbstractCaseAssert. The query is automatically narrowed down
   * to the actual caseExecution under test of this assertion as well as to the type
   * 'caseTask'.
   *
   * @return the only CaseExecution existing in the context of this case execution.
   * A 'null' CaseExecution in case no such CaseExecution is available.
   * @throws org.camunda.bpm.engine.ProcessEngineException in case more than
   *                                                       one CaseExecution is delivered by the query (after being narrowed
   *                                                       to the actual 'parent' CaseExecution)
   */
  protected CaseTaskAssert caseTask() {
    return caseTask(caseExecutionQuery());
  }

  /**
   * Enter into a chained {@link ProcessTaskAssert} inspecting the one and mostly
   * one 'processTask' currently available in the context of the actual caseExecution
   * under test of this AbstractCaseAssert. The query is automatically narrowed down
   * to the actual caseExecution under test of this assertion as well as to the type
   * 'processTask'.
   * <p>
   * Change visibility to public for those inheriting classes for whose
   * underlying objects under test this method makes sense
   *
   * @param query CaseExecutionQuery further narrowing down the search for
   *              caseExecutions. The query is automatically narrowed down to the
   *              actual 'parent' CaseExecution under test of this assertion as well as
   *              to the type 'processTask'.
   * @return the only CaseExecution resulting from the given search. A 'null'
   * CaseExecution in case no such CaseExecution is available.
   * @throws org.camunda.bpm.engine.ProcessEngineException in case more than
   *                                                       one CaseExecution is delivered by the query (after being narrowed
   *                                                       to the actual 'parent' CaseExecution)
   */
  protected ProcessTaskAssert processTask(final CaseExecutionQuery query) {
    CaseExecution caseExecution = caseExecution(query).getActual(); //TODO extract and use lookup code from caseExecution(query)
    return CaseExecutionAssert.assertThat(engine, caseExecution).isProcessTask();
  }

  /**
   * Enter into a chained {@link ProcessTaskAssert} inspecting the one and mostly
   * one 'processTask' currently available in the context of the actual caseExecution
   * under test of this AbstractCaseAssert. The query is automatically narrowed down
   * to the actual caseExecution under test of this assertion as well as to the type
   * 'processTask'.
   * <p>
   * Change visibility to public for those inheriting classes for whose
   * underlying objects under test this method makes sense
   *
   * @param activityId activity if further narrowing down the search for
   *                   caseExecutions. The query is automatically narrowed down to the
   *                   actual 'parent' CaseExecution under test of this assertion as well as
   *                   to the type 'processTask'.
   * @return the only CaseExecution with the given activitiy id. A 'null'
   * CaseExecution in case no such CaseExecution is available.
   * @throws org.camunda.bpm.engine.ProcessEngineException in case more than
   *                                                       one CaseExecution is delivered by the query (after being narrowed
   *                                                       to the actual 'parent' CaseExecution)
   */
  protected ProcessTaskAssert processTask(final String activityId) {
    return processTask(caseExecutionQuery().activityId(activityId));
  }

  /**
   * Enter into a chained {@link ProcessTaskAssert} inspecting the one and mostly
   * one 'processTask' currently available in the context of the actual caseExecution
   * under test of this AbstractCaseAssert. The query is automatically narrowed down
   * to the actual caseExecution under test of this assertion as well as to the type
   * 'processTask'.
   *
   * @return the only CaseExecution existing in the context of this case execution.
   * A 'null' CaseExecution in case no such CaseExecution is available.
   * @throws org.camunda.bpm.engine.ProcessEngineException in case more than
   *                                                       one CaseExecution is delivered by the query (after being narrowed
   *                                                       to the actual 'parent' CaseExecution)
   */
  protected ProcessTaskAssert processTask() {
    return processTask(caseExecutionQuery());
  }

  /**
   * Enter into a chained {@link StageAssert} inspecting the one and mostly
   * one 'stage' currently available in the context of the actual caseExecution
   * under test of this AbstractCaseAssert. The query is automatically narrowed down
   * to the actual caseExecution under test of this assertion as well as to the type
   * 'stage'.
   * <p>
   * Change visibility to public for those inheriting classes for whose
   * underlying objects under test this method makes sense
   *
   * @param query CaseExecutionQuery further narrowing down the search for
   *              caseExecutions. The query is automatically narrowed down to the
   *              actual 'parent' CaseExecution under test of this assertion as well as
   *              to the type 'stage'.
   * @return the only CaseExecution resulting from the given search. A 'null'
   * CaseExecution in case no such CaseExecution is available.
   * @throws org.camunda.bpm.engine.ProcessEngineException in case more than
   *                                                       one CaseExecution is delivered by the query (after being narrowed
   *                                                       to the actual 'parent' CaseExecution)
   */
  protected StageAssert stage(final CaseExecutionQuery query) {
    CaseExecution caseExecution = caseExecution(query).getActual(); //TODO extract and use lookup code from caseExecution(query)
    return CaseExecutionAssert.assertThat(engine, caseExecution).isStage();
  }

  /**
   * Enter into a chained {@link StageAssert} inspecting the one and mostly
   * one 'stage' currently available in the context of the actual caseExecution
   * under test of this AbstractCaseAssert. The query is automatically narrowed down
   * to the actual caseExecution under test of this assertion as well as to the type
   * 'stage'.
   * <p>
   * Change visibility to public for those inheriting classes for whose
   * underlying objects under test this method makes sense
   *
   * @param activityId activity if further narrowing down the search for
   *                   caseExecutions. The query is automatically narrowed down to the
   *                   actual 'parent' CaseExecution under test of this assertion as well as
   *                   to the type 'stage'.
   * @return the only CaseExecution with the given activitiy id. A 'null'
   * CaseExecution in case no such CaseExecution is available.
   * @throws org.camunda.bpm.engine.ProcessEngineException in case more than
   *                                                       one CaseExecution is delivered by the query (after being narrowed
   *                                                       to the actual 'parent' CaseExecution)
   */
  protected StageAssert stage(final String activityId) {
    return stage(caseExecutionQuery().activityId(activityId));
  }

  /**
   * Enter into a chained {@link StageAssert} inspecting the one and mostly
   * one 'stage' currently available in the context of the actual caseExecution
   * under test of this AbstractCaseAssert. The query is automatically narrowed down
   * to the actual caseExecution under test of this assertion as well as to the type
   * 'stage'.
   *
   * @return the only CaseExecution existing in the context of this case execution.
   * A 'null' CaseExecution in case no such CaseExecution is available.
   * @throws org.camunda.bpm.engine.ProcessEngineException in case more than
   *                                                       one CaseExecution is delivered by the query (after being narrowed
   *                                                       to the actual 'parent' CaseExecution)
   */
  protected StageAssert stage() {
    return stage(caseExecutionQuery());
  }

  /**
   * Enter into a chained {@link MilestoneAssert} inspecting the one and mostly
   * one 'milestone' currently available in the context of the actual caseExecution
   * under test of this AbstractCaseAssert. The query is automatically narrowed down
   * to the actual caseExecution under test of this assertion as well as to the type
   * 'milestone'.
   * <p>
   * Change visibility to public for those inheriting classes for whose
   * underlying objects under test this method makes sense
   *
   * @param query CaseExecutionQuery further narrowing down the search for
   *              caseExecutions. The query is automatically narrowed down to the
   *              actual 'parent' CaseExecution under test of this assertion as well as
   *              to the type 'milestone'.
   * @return the only CaseExecution resulting from the given search. A 'null'
   * CaseExecution in case no such CaseExecution is available.
   * @throws org.camunda.bpm.engine.ProcessEngineException in case more than
   *                                                       one CaseExecution is delivered by the query (after being narrowed
   *                                                       to the actual 'parent' CaseExecution)
   */
  protected MilestoneAssert milestone(final CaseExecutionQuery query) {
    CaseExecution caseExecution = caseExecution(query).getActual(); //TODO extract and use lookup code from caseExecution(query)
    return CaseExecutionAssert.assertThat(engine, caseExecution).isMilestone();
  }

  /**
   * Enter into a chained {@link MilestoneAssert} inspecting the one and mostly
   * one 'milestone' currently available in the context of the actual caseExecution
   * under test of this AbstractCaseAssert. The query is automatically narrowed down
   * to the actual caseExecution under test of this assertion as well as to the type
   * 'milestone'.
   * <p>
   * Change visibility to public for those inheriting classes for whose
   * underlying objects under test this method makes sense
   *
   * @param activityId activity if further narrowing down the search for
   *                   caseExecutions. The query is automatically narrowed down to the
   *                   actual 'parent' CaseExecution under test of this assertion as well as
   *                   to the type 'milestone'.
   * @return the only CaseExecution with the given activitiy id. A 'null'
   * CaseExecution in case no such CaseExecution is available.
   * @throws org.camunda.bpm.engine.ProcessEngineException in case more than
   *                                                       one CaseExecution is delivered by the query (after being narrowed
   *                                                       to the actual 'parent' CaseExecution)
   */
  protected MilestoneAssert milestone(final String activityId) {
    return milestone(caseExecutionQuery().activityId(activityId));
  }

  /**
   * Enter into a chained {@link MilestoneAssert} inspecting the one and mostly
   * one 'milestone' currently available in the context of the actual caseExecution
   * under test of this AbstractCaseAssert. The query is automatically narrowed down
   * to the actual caseExecution under test of this assertion as well as to the type
   * 'milestone'.
   *
   * @return the only CaseExecution existing in the context of this case execution.
   * A 'null' CaseExecution in case no such CaseExecution is available.
   * @throws org.camunda.bpm.engine.ProcessEngineException in case more than
   *                                                       one CaseExecution is delivered by the query (after being narrowed
   *                                                       to the actual 'parent' CaseExecution)
   */
  protected MilestoneAssert milestone() {
    return milestone(caseExecutionQuery());
  }

  /**
   * Verifies the expectation that the {@link CaseExecution} is currently
   * in the specified state
   *
   * @param state of the case execution
   * @return this {@link TaskAssert}
   */
  private S hasState(CaseExecutionState state) {
    Assertions.assertThat(actual).isNotNull();
    CaseExecution current = getCurrent();
    int actualState = current != null ? ((CaseExecutionEntity) current).getState()
      : ((HistoricCaseActivityInstanceEntity) getHistoric()).getCaseActivityInstanceState();
    Assertions
      .assertThat(actualState)
      .overridingErrorMessage(
        String.format(
          "Expected %s to be in state '%s', but found it to be '%s'!",
          toString(actual),
          state,
          CaseExecutionState.CASE_EXECUTION_STATES.get(actualState))).isEqualTo(state.getStateCode());
    @SuppressWarnings("unchecked")
    S self = (S) this;
    return self;
  }

  /**
   * Verifies the expectation that the {@link CaseExecution} is of specified type
   *
   * @param type of the case execution
   * @return this {@link TaskAssert}
   */
  private S hasType(String type) {
    Assertions.assertThat(actual).isNotNull();
    Assertions
      .assertThat(actual.getActivityType())
      .overridingErrorMessage(
        String.format("Expected %s to be a '%s', but found it to be a '%s'!", toString(actual), type, actual.getActivityType()))
      .isEqualTo(type);
    @SuppressWarnings("unchecked")
    S self = (S) this;
    return self;
  }

  /**
   * Retrieve refreshed status of object under test from runtime database
   **/
  @Override
  protected A getCurrent() {
    @SuppressWarnings("unchecked")
    A caseExecution = (A) caseExecutionQuery().caseExecutionId(actual.getId()).singleResult();
    return caseExecution;
  }

  /**
   * Retrieve HistoricCaseActivityInstance for object under test from history database
   **/
  protected HistoricCaseActivityInstance getHistoric() {
    Assertions.assertThat(actual).isNotNull();
    HistoricCaseActivityInstance historicCaseActivityInstance = historicCaseActivityInstanceQuery().caseExecutionId(actual.getId())
      .singleResult();
    String message = "Please make sure you have set the history service of the engine to "
      + "at least level 'activity' or a higher level before making use of this assertion!";
    Assertions.assertThat(historicCaseActivityInstance).overridingErrorMessage(message).isNotNull();
    return historicCaseActivityInstance;
  }

  /**
   * CaseExecutionQuery, automatically narrowed to {@link CaseInstance} of actual {@link CaseExecution}
   **/
  @Override
  protected CaseExecutionQuery caseExecutionQuery() {
    return super.caseExecutionQuery().caseInstanceId(actual.getCaseInstanceId());
  }

  /**
   * HistoricCaseActivityInstanceQuery, automatically narrowed to {@link CaseInstance} of actual {@link CaseExecution}
   **/
  @Override
  protected HistoricCaseActivityInstanceQuery historicCaseActivityInstanceQuery() {
    return super.historicCaseActivityInstanceQuery().caseInstanceId(actual.getCaseInstanceId());
  }

  @Override
  protected String toString(A caseExecution) {
    if (caseExecution != null) {
      return !actual.getCaseInstanceId().equals(actual.getId()) ? String.format(
        "%s {id='%s', activityId='%s' }",
        caseExecution.getActivityType(),
        caseExecution.getId(),
        caseExecution.getActivityId(),
        caseExecution.getCaseInstanceId())
        : String.format(
        "%s {id='%s', activityId='%s'" + (((CaseInstance) caseExecution).getBusinessKey() != null ? ", businessKey='%s'}"
          : "}"),
        CaseInstance.class.getSimpleName(),
        caseExecution.getId(),
        caseExecution.getActivityId(),
        ((CaseInstance) caseExecution).getBusinessKey());
    }
    return null;
  }

  /**
   * Enter into a chained map assert inspecting the variables currently available in the context of the case instance
   * under test of this AbstractCaseAssert.
   *
   * @return MapAssert<String   ,       Object> inspecting the case instance variables. Inspecting an empty map in case no such variables
   * are available.
   */
  protected MapAssert<String, Object> variables() {
    return Assertions.assertThat(vars());
  }

  /* Return variables map - independent of running/historic instance status */
  protected Map<String, Object> vars() {
    CaseExecution current = getCurrent();
    if (current != null) {
      return caseService().getVariables(current.getId());
    } else {
      return getHistoricVariablesMap();
    }
  }

  private Map<String, Object> getHistoricVariablesMap() {
    throw new UnsupportedOperationException();
  }

  protected S hasVars(String[] names) {
    boolean shouldHaveVariables = names != null;
    boolean shouldHaveSpecificVariables = names != null && names.length > 0;

    Map<String, Object> vars = vars();
    StringBuffer message = new StringBuffer();
    message.append("Expecting %s to hold ");
    message.append(shouldHaveVariables ? "case variables" + (shouldHaveSpecificVariables ? " %s, "
      : ", ")
      : "no variables at all, ");
    message.append("instead we found it to hold " + (vars.isEmpty() ? "no variables at all."
      : "the variables %s."));
    if (vars.isEmpty() && getCurrent() == null)
      message.append(" (Please make sure you have set the history " + "service of the engine to at least 'audit' or a higher level "
        + "before making use of this assertion for historic instances!)");

    MapAssert<String, Object> assertion = variables().overridingErrorMessage(
      message.toString(),
      toString(actual),
      shouldHaveSpecificVariables ? Arrays.asList(names)
        : vars.keySet(),
      vars.keySet());
    if (shouldHaveVariables) {
      if (shouldHaveSpecificVariables) {
        assertion.containsKeys(names);
      } else {
        assertion.isNotEmpty();
      }
    } else {
      assertion.isEmpty();
    }
    S self = (S) this;
    return self;
  }

}
