package com.plexiti.activiti.test.fluent.engine;

import org.activiti.engine.identity.User;
import org.activiti.engine.repository.DiagramLayout;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.List;
import java.util.Map;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public interface FluentBpmnProcessInstance extends FluentBpmnDelegate<ProcessInstance> {

    void claim(Task task, String userId);

    void claim(Task task, User user);

    void complete(Task task, Object... variables);

    Task currentTask();

    List<Task> currentTasks();

    DiagramLayout diagramLayout();

    Execution execution();

    List<Execution> executions();

    void moveAlong();

    void moveTo(String targetActivity);

    FluentBpmnProcessInstance start();

    FluentBpmnProcessInstanceImpl withVariable(String name, Object value);

    FluentBpmnProcessInstance withVariables(Map<String, Object> variables);

    FluentBpmnProcessVariable variable(String variableName);

}