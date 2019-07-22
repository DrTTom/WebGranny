package de.tautenhahn.testing.web;

import java.util.function.Predicate;


/**
 * Factory for selectors.
 * 
 * @author t.tautenhahn
 */
public final class With
{

  private With()
  {
    // utility class
  }

  /**
   * @param name
   * @return filters elements of given tag name.
   */
  public static Predicate<Element> tagName(String name)
  {
    return e -> name.equals(e.getTagName()) || e.getTagName().matches(name);
  }

  // TODO: change types to Property!
  public static Predicate<Element> marker(String marker)
  {
    return e -> e.isRecognizableBy(marker);
  }


  /**
   * Wraps all kinds of selection methods. For Selenium, java side element selection is not used.
   */
  public class Property
  {

    String jsFilter;

    Predicate<Element> matches;

    Property(String jsFilter, Predicate<Element> matches)
    {
      this.jsFilter = jsFilter;
      this.matches = matches;
    }

    /**
     * @param e
     * @return true if given element has this property.
     */
    boolean matchesElement(Element e)
    {
      return matches.test(e);
    }

    /**
     * @return a java script statement (lambda) returning true if given element has this property.
     */
    String getJsFilter()
    {
      return jsFilter;
    }
  }
}
