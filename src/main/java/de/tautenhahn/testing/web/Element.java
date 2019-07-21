package de.tautenhahn.testing.web;

/**
 * Web site element shown by browser and selectable by the user. There will be sub-interfaces for elements
 * supporting special user interaction.
 * 
 * @author t.tautenhahn
 */
public interface Element
{

  /**
   * Simulates clicking on this element. May trigger events, gaining focus or whatever the element can do.
   */
  void click();

  /**
   * @param name
   * @return the value of the specified attribute of the element
   */
  String getAttribute(String name);

  /**
   * Gain focus and type text. Method is in this interface because keyboard exists independently of element
   * type.
   * 
   * @param content
   */
  void type(String content);

  String getTagName();

  String text();

  boolean isRecognizableBy(String marker);

  boolean isSameLineAs(Element boundary);

  boolean isBelow(Element boundary);

  boolean isRightOf(Element boundary);


}
