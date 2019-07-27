package de.tautenhahn.testing.web;

import java.util.List;

import de.tautenhahn.testing.web.With.Property;


/**
 * To be thrown if an element searched by the test does not exist or is not visible.
 * 
 * @author t.tautenhahn
 */
public class ElementNotFoundException extends RuntimeException
{

  private static final long serialVersionUID = 1L;

  /**
   * Creates new instance.
   * 
   * @param url address of the current page
   * @param rootElement of search context
   * @param all applied filters
   * @param text page text
   */
  public ElementNotFoundException(String url, Element rootElement, List<Property> all, String text)
  {
    super(createMessage(url, rootElement, all, text));
  }

  private static String createMessage(String url, Element rootElement, List<Property> all, String text)
  {
    return url + rootElement + all + text; // TODO
  }



}
