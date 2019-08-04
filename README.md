# WebGranny - library to test web applications ![Logo](granny.png)

This just wraps another test framework like Selenuim or HTTPUnit, but supplies an API focused on user interaction instead of on technical details of the respective tested application.

Existing test frameworks are built around technical terms like XPath, CSS selectors, element IDs and so on. However, in most cases the eventual user does not even know what an id attribute is. The user is more interested in things like "When I click on 'edit shipping address' then there should appear some element(s) where I can type in my address". If a web application is really good, then it should be possible to explain its usage in simple words, even to your granny.

This library tries to make the test source code look like you are explaining total layperson what to do and expect, so instead of 

```java
driver.findElement(By.cssSelector("h2[class=actionHeader]~div button[id=performAction]").click()
```
it should be possible to write something like:

```java
after("Actions").clickOn("perform");
```

This approach provides:
- better testing of user experience
- easier to understand test sources
- tests are more stable when implementation details of application change
- allows test first approach
- supports building application with better accessibility for disabled persons

## Prerequisites
- install a Selenium driver for your favorite browser
- edit the file config.json

## Start reading source code at
de.tautenhahn.testing.web.ExampleTest

## But ...
This is not a finished test library - yet. This project mainly contains sketches and ideas on how to build it based on one existing test framework. 