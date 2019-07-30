package de.tautenhahn.testing.web;

import de.tautenhahn.testing.web.With.Property;


/**
 * Defines selection methods. TODO: replace predicates by properties
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
   * @param marker
   * @return
   */
  Element findLink(String marker);

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
   * @param string
   * @param filter
   * @return
   */
  Scope before(String string, Property... filter);

  /**
   * Returns subscope ...
   * 
   * @param string
   * @param filter
   * @return
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
