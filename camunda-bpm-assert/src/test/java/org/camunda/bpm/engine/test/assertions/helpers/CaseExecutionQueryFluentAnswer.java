package org.camunda.bpm.engine.test.assertions.helpers;

import org.camunda.bpm.engine.query.Query;
import org.camunda.bpm.engine.runtime.CaseExecutionQuery;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * Default Answer for CaseExecutionQuery mocks that returns the mock as default answer.
 * Eases mocking of this class as most of its methods support the fluent API and therefore return the invoked instance.
 *
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 */
public class CaseExecutionQueryFluentAnswer implements Answer<CaseExecutionQuery> {

  @Override
  public CaseExecutionQuery answer(InvocationOnMock invocationOnMock) {
    final Class<?> methodReturnType = invocationOnMock.getMethod().getReturnType();
    if (CaseExecutionQuery.class.isAssignableFrom(methodReturnType) // fluent api methods
      || Query.class.isAssignableFrom(methodReturnType) // asc/desc
    ) {
      return (CaseExecutionQuery) invocationOnMock.getMock();
    } else {
      return null;
    }
  }
}
