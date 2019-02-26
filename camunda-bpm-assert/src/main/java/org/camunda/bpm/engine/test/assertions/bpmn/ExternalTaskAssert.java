package org.camunda.bpm.engine.test.assertions.bpmn;

import org.assertj.core.api.Assertions;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.externaltask.ExternalTask;
import org.camunda.bpm.engine.task.Task;

public class ExternalTaskAssert extends AbstractProcessAssert<ExternalTaskAssert, ExternalTask> {

  protected ExternalTaskAssert(final ProcessEngine engine, final ExternalTask actual) {
    super(engine, actual, ExternalTaskAssert.class);
  }

  public static ExternalTaskAssert assertThat(ProcessEngine engine, ExternalTask actual) {
    return new ExternalTaskAssert(engine, actual);
  }

  @Override
  protected ExternalTask getCurrent() {
    return externalTaskQuery().externalTaskId(actual.getId()).singleResult();
  }

  /**
   * Verifies the topic name of a {@link ExternalTask}.
   *
   * @param topicName the expected value of the topic
   * @return this {@link ExternalTaskAssert}
   */
  public ExternalTaskAssert hasTopicName(final String topicName) {
    final ExternalTask current = getExistingCurrent();
    Assertions.assertThat(topicName).isNotNull();
    Assertions.assertThat(current.getTopicName())
      .overridingErrorMessage("Expecting %s to have topic name '%s', but found it to be '%s'!",
        toString(current),
        topicName,
        current.getTopicName())
      .isEqualTo(topicName);
    return this;
  }

  /**
   * Verifies the activity id an {@link ExternalTask}.
   *
   * @param activityId the expected value of the externalTask/@id attribute
   * @return this {@link ExternalTaskAssert}
   */
  public ExternalTaskAssert hasActivityId(final String activityId) {
    ExternalTask current = getExistingCurrent();
    Assertions.assertThat(activityId).isNotNull();
    Assertions.assertThat(current.getActivityId())
      .overridingErrorMessage("Expecting %s to have activity id '%s', but found it to have '%s'!",
        toString(current),
        activityId,
        current.getActivityId()
      ).isEqualTo(activityId);
    return this;
  }


  @Override
  protected String toString(ExternalTask task) {
    return task != null ?
      String.format("%s {" +
          "id='%s', " +
          "processInstanceId='%s', " +
          "topicName='%s'}",
        ExternalTask.class.getSimpleName(),
        task.getId(),
        task.getProcessInstanceId(),
        task.getTopicName()
      ) : null;
  }

}
