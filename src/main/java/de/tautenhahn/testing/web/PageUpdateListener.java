package de.tautenhahn.testing.web;

/**
 * To be implemented by classes which should do something upon a page update.
 * 
 * @author t.tautenhahn
 */
public interface PageUpdateListener
{

  /**
   * Will be called if application suspects a page update has been triggered for instance by a click of by
   * pressing enter. Must handle the cases that page update
   * <ul>
   * <li>has been performed </li>
   * <li>has been triggered but not performed yet,</li>
   * <li>does not take place.</li>
   * </ul>
   */
  void pagePossiblyUpdated();

  /**
   * Will be called before {@link #pagePossiblyUpdated()} if client expects an alert to be displayed.
   * Closes that alert, fails if alert is not found.
   *
   * @param message expected message in that alert, regex supported
   * @param input text to enter, optional. Fail test if given but alert has no input field.
   * @param doAccept true to click accept, false to cancel.
   * @return true if alert was handled, false if handling should be done with other listener.
   */
  default boolean handleAlert(String message, String input, boolean doAccept)
  {
    return false;
  }
}
