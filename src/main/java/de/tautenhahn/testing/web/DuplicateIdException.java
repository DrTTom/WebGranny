package de.tautenhahn.testing.web;

/**
 * To be thrown if an element searched by the test does not exist or is not visible.
 * 
 * @author t.tautenhahn
 */
public class DuplicateIdException extends OnWebsiteException
{

  private static final long serialVersionUID = 1L;

  /**
   * Creates new instance.
   * 
   * @param url  address of the current page
   * @param id   duplicate value
   * @param text page text
   */
  public DuplicateIdException(String url, String id, String text)
  {
    super(url, msg -> msg.append("\nid attribute ").append(id), text);
  }
}
