package de.tautenhahn.testing.web;

/**
 * To be thrown if an image without alt attribute is found.
 * 
 * @author t.tautenhahn
 */
public class MissingAltAttributeException extends OnWebsiteException
{

  private static final long serialVersionUID = 1L;

  /**
   * Creates new instance.
   * 
   * @param url    address of the current page
   * @param source image source
   * @param text   page text
   */
  public MissingAltAttributeException(String url, String source, String text)
  {
    super(url, msg -> msg.append("\nimage with source '").append(source).append('\''), text);
  }
}
