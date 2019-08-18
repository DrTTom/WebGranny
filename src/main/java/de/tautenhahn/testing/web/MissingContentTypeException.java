package de.tautenhahn.testing.web;

/**
 * To be thrown if there is no content type specified on the page.
 * 
 * @author t.tautenhahn
 */
public class MissingContentTypeException extends OnWebsiteException
{

  private static final long serialVersionUID = 1L;

  /**
   * Creates new instance.
   * 
   * @param url  address of the current page
   * @param text page text
   */
  public MissingContentTypeException(String url, String text)
  {
    super(url, msg -> { // no further details
    }, text);
  }
}
