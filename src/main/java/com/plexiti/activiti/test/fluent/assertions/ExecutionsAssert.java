package com.plexiti.activiti.test.fluent.assertions;

import com.plexiti.activiti.test.fluent.ActivitiFluentTestHelper;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ExecutionQuery;
import org.fest.assertions.api.AbstractAssert;
import org.fest.assertions.api.Assertions;

import java.util.List;

/**
 *
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 *
 */
public class ExecutionsAssert extends AbstractAssert<ExecutionsAssert, List<Execution>> {

    protected ExecutionsAssert(List<Execution> actual) {
        super(actual, ExecutionsAssert.class);
    }

    public static ExecutionsAssert assertThat(List<Execution> actual) {
        return new ExecutionsAssert(actual);
    }

    public ExecutionsAssert areFinished() {
        /*
         * TODO: we need to review this
         * If the incomming Execution instance is null we consider the execution finished
         */
        if (actual == null) {
            return this;
        }

        /*
         * If it is not null we make sure that it is actually finished.
         */
        //ExecutionQuery executionQuery = ActivitiFluentTestHelper.getRuntimeService().createExecutionQuery();
        //executionQuery.processInstanceId(actual.getActualProcessInstance().getId()).list();
        //Assertions.assertThat(actual.get(0).is).
        //        overridingErrorMessage("Expected execution %s to be finished but it is not!", actual.getId()).
        //        isTrue();

        return this;
    }

}