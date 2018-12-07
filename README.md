# <img src="http://camunda.github.io/camunda-bpm-assert/resources/images/camunda.png" width="23" height="23"></img>&nbsp;Camunda BPM Assert


<img src="https://travis-ci.org/holunda-io/camunda-bpm-assert.svg?branch=master" align="left"/>
<a href="https://maven-badges.herokuapp.com/maven-central/io.holunda.testing/camunda-bpm-assert"><img src="https://maven-badges.herokuapp.com/maven-central/io.holunda.testing/camunda-bpm-assert/badge.svg" align="left"/></a>

<br>

The community extension **Camunda BPM Assert** makes it easy to **assert** the status of your BPMN processes and CMMN cases when **driving** them forward in your typical unit test methods. Simply write code like

```groovy
assertThat(instance).isWaitingAt("UserTask_InformCustomer");
assertThat(task).hasCandidateGroup("Sales").isNotAssigned();
```

Furthermore a set of static helper methods is provided to make it easier to drive through a process. Based on the [80/20 principle](https://en.wikipedia.org/wiki/Pareto_principle) the library reaches out to make those things simple you need really often. You will e.g. often have a a single open task instance in your process instance. Then just write
 
```groovy
complete(task(instance), withVariables("approved", true));
```

## Why a cloned version

Original Camunda BPM Assert works with **all versions of Camunda BPM** since 7.0 up to the most 
recent and the Java versions (1.6, 1.7., 1.8).

The original version is compatible with new versions, but offers no help for testing anything newer 
than Camunda BPM 7.6. In addition, it *fixes version of AssertJ to a legacy 1.7.1* and doesn't work with 
AssertJ 3 at all. So, we decided to **create a clone with newest version of used framework** :
 * assertj 3.11.1

Internally, we are running and testing with the following versions:

 * JDK 1.8
 * Camunda BPM 7.9.0
 * mockito 2.23.4 
 * powermock 2.0.0-RC.4
 * junit 4.12

So we dropped legacy support and produced a version of a well known library, in order to continue to use it in projects.

## Get started in three simple steps!


**1.** Add a maven **test dependency** to your project:

```xml  
<dependency>
    <groupId>io.holunda.testing</groupId>
    <artifactId>camunda-bpm-assert</artifactId>
    <version>2.1</version>
    <scope>test</scope>
</dependency>
```

Note however, that if you use a [Camunda BPM Maven Archetype](https://docs.camunda.org/manual/latest/user-guide/process-applications/maven-archetypes/) to create your project, Camunda BPM Assert is already included in your project setup.

**2.** Add a **static import** to your test class

Create your test case just as described in the [Camunda BPM Testing Guide](https://docs.camunda.org/manual/latest/user-guide/testing/). As recommended at the end of that guide, add Camunda BPM Assert by statically importing it in your test class:

```groovy  
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;
```

**3.** Start using the assertions in your **test methods**

You have now access to all the assertions provided by Joel Costigliola's 
[AssertJ](http://joel-costigliola.github.io/assertj/) rich assertions library - 
plus the additional Camunda BPM assertions building upon them. Now assume you want to 
assert that your process instance is actually **started**, **waiting** at a 
specific **user task** and that task should yet be **unassigned**, 
but waiting to be assigned to a user of a specific group? Then write:

```groovy
assertThat(processInstance).isStarted()
  .task().hasDefinitionKey("edit")
    .hasCandidateGroup("human-resources")
    .isNotAssigned();
```

<img src="http://camunda.github.io/camunda-bpm-assert/resources/images/green-bar.png" align="right"></img> Green bar? 

Congrats! You are successfully using Camunda BPM Assert. Find a more detailed description of the assertions and helper methods available in the Camunda BPM Assert [**User Guide**](./camunda-bpm-assert/README.md).

## Further Resources

* [User Guide](./camunda-bpm-assert/README.md) 
* [Issue Tracker](https://github.com/holunda-io/camunda-bpm-assert/issues) 
* [Roadmap](https://github.com/camunda/holunda-io/issues/milestones?state=open&with_issues=no) 
* [Download](https://github.com/holunda-io/camunda-bpm-assert/releases)
* [Continuous Integration](https://travis-ci.org/holunda-io/camunda-bpm-assert)

## Original version

If you are interested in the original version, please have a look at https://github.com/camunda/camunda-bpm-assert

## License

Apache License, Version 2.0

## Contributors

<a href="http://www.holisticon.de"><img src="https://www.holisticon.de/wp-content/uploads/2013/05/holisticon-logo-hamburg.gif" align="right" /></a><br>

[Jan Galinski](https://github.com/jangalinski) (Holisticon AG)<br>
[Martin Günther](https://github.com/margue) (Holisticon AG)<br>
[Malte Sörensen](https://github.com/malteser) (Holisticon AG)<br>
[Simon Zambrovski](https://github.com/zambrovski) (Holisticon AG)

<a href="http://plexiti.com"><img src="http://plexiti.com/en/img/logo.png" align="right" width="210"></img></a><br>
[Martin Schimak](https://github.com/martinschimak) Plexiti

... and [many others](https://github.com/camunda/camunda-bpm-assert/graphs/contributors). 

