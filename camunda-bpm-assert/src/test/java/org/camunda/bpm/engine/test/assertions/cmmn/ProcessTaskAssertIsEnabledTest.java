package org.camunda.bpm.engine.test.assertions.cmmn;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.processInstanceQuery;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.task;
import static org.camunda.bpm.engine.test.assertions.cmmn.CmmnAwareTests.assertThat;
import static org.camunda.bpm.engine.test.assertions.cmmn.CmmnAwareTests.caseExecution;
import static org.camunda.bpm.engine.test.assertions.cmmn.CmmnAwareTests.caseService;
import static org.camunda.bpm.engine.test.assertions.cmmn.CmmnAwareTests.complete;
import static org.camunda.bpm.engine.test.assertions.cmmn.CmmnAwareTests.disable;

import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.helpers.Failure;
import org.camunda.bpm.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.junit.Rule;
import org.junit.Test;

public class ProcessTaskAssertIsEnabledTest extends ProcessAssertTestCase {

  public static final String TASK_A = "PI_TaskA";
  public static final String TASK_B = "PI_TaskB";
  public static final String USER_TASK = "UserTask_1";
  public static final String CASE_KEY = "Case_ProcessTaskAssertIsEnabledTest";

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = { "cmmn/ProcessTaskAssertIsEnabledTest.cmmn", "cmmn/ProcessTaskAssert-calledProcess.bpmn" })
  public void testIsEnabled_Success() {
    // Given
    CaseInstance caseInstance = givenCaseIsCreated();
    // When
    complete(task(USER_TASK, calledProcessInstance(caseInstance)));
    // Then
    assertThat(caseInstance).processTask(TASK_B).isEnabled();
  }

  @Test
  @Deployment(resources = { "cmmn/ProcessTaskAssertIsEnabledTest.cmmn", "cmmn/ProcessTaskAssert-calledProcess.bpmn" })
  public void testIsEnabled_Failure() {
    // Given
    final CaseInstance caseInstance = givenCaseIsCreated();
    // When
    complete(task(USER_TASK, calledProcessInstance(caseInstance)));
    disable(caseExecution(TASK_B, caseInstance));
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(caseInstance).processTask(TASK_B).isEnabled();
      }
    });
  }

  private ProcessInstance calledProcessInstance(CaseInstance caseInstance) {
    return processInstanceQuery().superCaseInstanceId(caseInstance.getId()).singleResult();
  }

  private CaseInstance givenCaseIsCreated() {
    return caseService().createCaseInstanceByKey(CASE_KEY);
  }
}