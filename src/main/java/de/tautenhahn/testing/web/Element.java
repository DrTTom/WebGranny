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
   * @param name attribute name
   * @return the value of the specified attribute of the element
   */
  String getAttribute(String name);

  /**
   * Gain focus and type text. Method is in this interface because keyboard exists independently of element
   * type.
   * 
   * @param content text to type in. Note that special chars as ENTER and TAB require separate methods.
   */
  void doType(String content);

  /**
   * @return the tag name (capitalized)
   */
  String getTagName();

  /**
   * @return the readable text
   */
  String getText();

  /**
   * @return position this element is displayed at. If driver does not support that value, return an
   *         estimation which allows sorting.
   */
  BoundingRectangle getPosition();

  /**
   * @return id of element
   */
  String getId();

  /**
   * Adds a listener.
   * 
   * @param listener to be notified when a page update is expected
   */
  void addListener(PageUpdateListener listener);

}
