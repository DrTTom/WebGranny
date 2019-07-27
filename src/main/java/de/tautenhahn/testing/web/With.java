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
  public static Property tagName(String name)
  {
    return new Property("matching(e.tagname, '" + name + "')", null);
  }

  /**
   * @param marker Visible text, label text, help text, style class, tag name or whatever the user might
   *          recognize the element by
   * @return filters for elements recognizable by given marker text
   */
  public static Property description(String marker)
  {
    return new Property("describedAs(e, '" + marker + "')", null);
  }

  /**
   * Wraps all kinds of selection methods. For Selenium, java side element selection is not used.
   */
  public static class Property
  {

    String jsFilter;

    Predicate<Element> matches;

    /**
     * @param jsFilter simple expression fitting into "e=&gt;{return ...}"
     * @param matches same in java, optional
     */
    Property(String jsFilter, Predicate<Element> matches)
    {
      this.jsFilter = jsFilter;
      this.matches = matches;
    }

    /**
     * @param e
     * @return true if given element has this property.
     */
    public boolean matchesElement(Element e)
    {
      return matches.test(e);
    }

    /**
     * @return a java script statement (lambda) returning true if given element has this property.
     */
    public String getJsFilter()
    {
      return jsFilter;
    }
  }
}
