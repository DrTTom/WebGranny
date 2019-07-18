package de.tautenhahn.testing.web;

/**
 * Web site element shown by browser and selectable by the user.
 * 
 * @author t.tautenhahn
 */
public interface Element
{

  /**
   * Simulates clicking on this element. May trigger events, gaining focus or whatever the element can do.
   */
  void click();
}
