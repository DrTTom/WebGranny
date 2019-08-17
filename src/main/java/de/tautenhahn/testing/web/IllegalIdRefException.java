package de.tautenhahn.testing.web;

/**
 * To be thrown if an idRef value does not match any id on the page.
 * 
 * @author t.tautenhahn
 */
public class IllegalIdRefException extends OnWebsiteException
{

  private static final long serialVersionUID = 1L;

  /**
   * Creates new instance.
   * 
   * @param url      address of the current page
   * @param label    label text
   * @param forValue value of the for attribute.
   * @param text     page text
   */
  public IllegalIdRefException(String url, String label, String forValue, String text)
  {
    super(url,
          msg -> msg.append("\nfor attribute '")
                    .append(forValue)
                    .append("' of label '")
                    .append(label)
                    .append('\''),
          text);
  }
}
