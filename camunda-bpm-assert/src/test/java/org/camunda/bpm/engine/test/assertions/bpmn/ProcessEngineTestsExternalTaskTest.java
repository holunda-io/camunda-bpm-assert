package org.camunda.bpm.engine.test.assertions.bpmn;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.helpers.Failure;
import org.camunda.bpm.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

/**
 * Tests for external tasks
 *
 * @author Simon Zambrovski
 */
@Deployment(resources = {"bpmn/ProcessEngineTests-externaltask.bpmn"})
public class ProcessEngineTestsExternalTaskTest extends ProcessAssertTestCase {

  private static final String PROCESS_ENGINE_TESTS_EXTERNALTASK = "ProcessEngineTests-externaltask";

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @After
  public void tearDown() {
    reset();
  }

  @Test
  public void testExternalTask_OnlyActivity_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(PROCESS_ENGINE_TESTS_EXTERNALTASK);
    // And
    runtimeService().startProcessInstanceByKey(PROCESS_ENGINE_TESTS_EXTERNALTASK);
    // When
    assertThat(processInstance).isNotNull();
    // Then
    assertThat(externalTask()).isNotNull().hasActivityId("ExternalTask1");
  }

  @Test
  public void testExternalTask_OnlyActivity_Failure() {
    // Given
    runtimeService().startProcessInstanceByKey(PROCESS_ENGINE_TESTS_EXTERNALTASK);
    // And
    runtimeService().startProcessInstanceByKey(PROCESS_ENGINE_TESTS_EXTERNALTASK);
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        externalTask();
      }
    }, IllegalStateException.class);
  }

  @Test
  public void testExternalTask_taskDefinitionKey_OnlyActivities_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(PROCESS_ENGINE_TESTS_EXTERNALTASK);
    // And
    runtimeService().startProcessInstanceByKey(PROCESS_ENGINE_TESTS_EXTERNALTASK);
    // When
    assertThat(processInstance).isNotNull();
    complete(externalTask(), "workerId");
    // Then
    assertThat(externalTask("ExternalTask2")).isNotNull().hasActivityId("ExternalTask2");
  }

  @Test
  public void testExternalTask_taskDefinitionKey_OnlyActivity_Failure() {
    // Given
    runtimeService().startProcessInstanceByKey(PROCESS_ENGINE_TESTS_EXTERNALTASK);
    // And
    runtimeService().startProcessInstanceByKey(PROCESS_ENGINE_TESTS_EXTERNALTASK);
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        externalTask("ExternalTask1");
      }
    }, IllegalStateException.class);
  }

  @Test
  public void testExternalTask_taskQuery_OnlyActivity_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(PROCESS_ENGINE_TESTS_EXTERNALTASK);
    // And
    runtimeService().startProcessInstanceByKey(PROCESS_ENGINE_TESTS_EXTERNALTASK);
    // When
    assertThat(processInstance).isNotNull();
    // Then
    assertThat(externalTask(externalTaskQuery().activityId("ExternalTask1"))).isNotNull().hasActivityId("ExternalTask1");
  }

  @Test
  public void testExternalTask_taskQuery_OnlyActivity_Failure() {
    // Given
    runtimeService().startProcessInstanceByKey(PROCESS_ENGINE_TESTS_EXTERNALTASK);
    // And
    runtimeService().startProcessInstanceByKey(PROCESS_ENGINE_TESTS_EXTERNALTASK);
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        externalTask(externalTaskQuery().activityId("ExternalTask1"));
      }
    }, IllegalStateException.class);
  }

  @Test
  public void testExternalTask_taskDefinitionKey_processInstance_OnlyActivity_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(PROCESS_ENGINE_TESTS_EXTERNALTASK);
    // And
    runtimeService().startProcessInstanceByKey(PROCESS_ENGINE_TESTS_EXTERNALTASK);
    // Then
    assertThat(externalTask("ExternalTask1", processInstance)).isNotNull().hasActivityId("ExternalTask1");
  }

  @Test
  public void testExternalTask_taskQuery_processInstance_OnlyActivity_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(PROCESS_ENGINE_TESTS_EXTERNALTASK);
    // And
    runtimeService().startProcessInstanceByKey(PROCESS_ENGINE_TESTS_EXTERNALTASK);
    // Then
    assertThat(externalTask(externalTaskQuery().activityId("ExternalTask1"), processInstance)).isNotNull().hasActivityId("ExternalTask1");
  }

  @Test
  public void testExternalTask_complete_without_worker() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(PROCESS_ENGINE_TESTS_EXTERNALTASK);
    // And
    runtimeService().startProcessInstanceByKey(PROCESS_ENGINE_TESTS_EXTERNALTASK);
    assertThat(externalTask(externalTaskQuery().activityId("ExternalTask1"), processInstance)).isNotNull().hasActivityId("ExternalTask1");
    // When
    complete(externalTask(), withVariables("key", "value"));
    // Then
    assertThat(processInstance).isWaitingAt("ExternalTask2");
    assertThat(processInstance).hasVariables("key");
    assertThat(processInstance).variables().containsEntry("key", "value");
  }

  @Test
  public void testExternalTask_complete_with_worker() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(PROCESS_ENGINE_TESTS_EXTERNALTASK);
    // And
    runtimeService().startProcessInstanceByKey(PROCESS_ENGINE_TESTS_EXTERNALTASK);
    assertThat(externalTask(externalTaskQuery().activityId("ExternalTask1"), processInstance)).isNotNull().hasActivityId("ExternalTask1");
    // When
    complete(externalTask(), "worker2", withVariables("key2", "value2"));
    // Then
    assertThat(processInstance).isWaitingAt("ExternalTask2");
    assertThat(processInstance).hasVariables("key2");
    assertThat(processInstance).variables().containsEntry("key2", "value2");
  }

}
