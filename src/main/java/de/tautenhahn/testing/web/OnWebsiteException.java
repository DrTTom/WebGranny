package de.tautenhahn.testing.web;

import java.util.function.Consumer;


/**
 * To be thrown if an element searched by the test does not exist or is not visible.
 * 
 * @author t.tautenhahn
 */
public class OnWebsiteException extends RuntimeException
{

  private static final long serialVersionUID = 1L;

  /**
   * Creates new instance.
   * 
   * @param url        address of the current page
   * @param text       page text
   * @param addDetails adds any details to message.
   */
  public OnWebsiteException(String url, Consumer<StringBuilder> addDetails, String text)
  {
    super(createMessage(url, addDetails, text));
  }

  private static String createMessage(String url, Consumer<StringBuilder> addDetails, String text)
  {
    StringBuilder result = new StringBuilder("on page ");
    result.append(url);
    addDetails.accept(result);
    String separator = "\n-----------------------------------------------";
    result.append(separator).append('\n').append(text).append(separator);
    return result.toString();
  }



}
