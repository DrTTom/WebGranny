package de.tautenhahn.testing.web;

import java.util.List;

import de.tautenhahn.testing.web.With.Property;


/**
 * Defines selection methods.
 *
 * @author t.tautenhahn
 */
public interface Scope
{

  /**
   * Returns element specified by zero or more filters
   *
   * @param filter Any requirement for the wanted element
   * @return selected element
   */
  Element findElement(Property... filter);

  /**
   * Returns the list of currently visible elements matching the filters. Note that there is no implicit wait
   * here. One typical usage is to check that some element is not present.
   *
   * @param filter Any requirement for the wanted element
   * @return selected element
   */
  List<Element> listElements(Property... filter);

  /**
   * Returns element specified by marker String.
   *
   * @param marker Visible text, text of a label for an element, help text, image source string, anything the
   *               user may recognize an element by. Regular expressions supported.
   * @param filter any additional filters to recognize the single element by
   * @return selected element
   */
  Element findElement(String marker, Property... filter);

  /**
   * Several convenience methods allow selecting elements of specified abstract type.
   *
   * @param marker see {@link #findElement(String, Property...)}
   * @param filter any additional filters to recognize the single element by
   * @return see {@link #findElement(String, Property...)}
   */
  Element findLink(String marker, Property... filter);

  /**
   * Short for find and click
   *
   * @param marker see {@link #findElement(String, Property...)}
   * @param filter see {@link #findElement(String, Property...)}
   */
  default void clickOn(String marker, Property... filter)
  {
    findElement(marker, filter).click();
  }

  /**
   * Returns a scope selecting only elements after specified element.
   *
   * @param marker Visible text, text of a label for an element, help text, image source string, anything the
   *               user may recognize an element by. Regular expressions supported.
   * @param filter any additional filters to recognize the single element by
   * @return fails test if undefined.
   */
  Scope after(String marker, Property... filter);

  /**
   * Returns subscope ...
   *
   * @param marker Visible text, text of a label for an element, help text, image source string, anything the
   *               user may recognize an element by. Regular expressions supported.
   * @param filter any additional filters to recognize the single element by
   * @return fails test if undefined.
   */
  Scope before(String marker, Property... filter);

  /**
   * Returns subscope ...
   *
   * @param marker Visible text, text of a label for an element, help text, image source string, anything the
   *               user may recognize an element by. Regular expressions supported.
   * @param filter any additional filters to recognize the single element by
   * @return fails test if undefined.
   */
  Scope inSameLineAs(String marker, Property... filter);

  /**
   * Returns subscope ...
   *
   * @param string any text user can recognize element by
   * @param filter additional properties specifying root element of new scope
   * @return new sub scope
   */
  Scope in(String string, Property... filter);

  /**
   * Simulate pressing the enter key.
   */
  void pressEnter();

  /**
   * Same as {@link #findElement(String, Property...)} but selects only headers.
   *
   * @param marker see above
   * @param filter see above
   * @return header element
   */
  Element findHeader(String marker, Property... filter);
}
