package de.tautenhahn.testing.web;

import java.util.List;
import java.util.function.Predicate;


/**
 * Context in which an element may be selected. Typically containing a root DOM node and some boundary
 * elements/filters
 * 
 * @author t.tautenhahn
 */
public interface SelectionContext
{

  /**
   * Finds the most prominent (usually first) element specified by search criteria.
   * 
   * @param marker Visible text, text of a label for an element, help text, image source string, anything the
   *          user may recognize an element by. Regular expressions supported.
   * @param filters any filters further specifying an element
   * @return found element.
   */
  Element find(String marker, Predicate<Element>... filters);

  /**
   * Same as {@link #find(String, Predicate...)} but without the String marker.
   * 
   * @param first specifies the element
   * @param filters any filters further specifying an element
   * @return found element
   */
  Element find(Predicate<Element> first, Predicate<Element>... filters);

  /**
   * Same as {@link #find(String, Predicate...)} but returns all matching elements.
   *
   * @param first specifies the element
   * @param filters any filters further specifying an element
   * @return all matching elements
   */
  List<Element> list(String marker, Predicate<Element>... filters);

  /**
   * Another convenience method.
   * 
   * @param filters any filters specifying an element
   * @return matching elements
   */
  List<Element> list(Predicate<Element>... filters);

  /**
   * Returns a context providing sub-elements of selected element.
   * 
   * @param first specifies the element
   * @param filters any filters further specifying an element
   * @return new context
   */
  SelectionContext in(String marker, Predicate<Element>... filters);

  /**
   * Returns a context providing sub-elements of selected element.
   * 
   * @param first specifies the element
   * @param filters any filters further specifying an element
   * @return new context
   */
  SelectionContext in(Predicate<Element> first, Predicate<Element>... filters);

  /**
   * Returns a context providing only elements which are visible to the right in same line or below selected
   * element.
   * 
   * @param first specifies the element
   * @param filters any filters further specifying an element
   * @return new context
   */
  SelectionContext after(String marker, Predicate<Element>... filters);

  /**
   * Returns a context providing only elements which are visible to the right in same line or below selected
   * element.
   * 
   * @param first specifies the element
   * @param filters any filters further specifying an element
   * @return new context
   */
  SelectionContext after(Predicate<Element> first, Predicate<Element>... filters);

  /**
   * Returns a context providing only elements which are visible on the left in same line or above the
   * specified element. Reverts the priority, i.e. the last matching element is treated as most prominent one.
   * 
   * @param first specifies the element
   * @param filters any filters further specifying an element
   * @return new context
   */
  SelectionContext before(String marker, Predicate<Element>... filters);

  /**
   * Returns a context providing only elements which are visible on the left in same line or above the
   * specified element.
   * 
   * @param first specifies the element
   * @param filters any filters further specifying an element
   * @return new context
   */
  SelectionContext before(Predicate<Element> first, Predicate<Element>... filters);

  /**
   * Returns a context which by default returns the last matching element instead of the first one.
   * 
   * @return new context
   */
  SelectionContext last();
}
