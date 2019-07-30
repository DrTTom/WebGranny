package de.tautenhahn.testing.web;

/**
 * Enables a test access to a web application like you would explain to your granny.
 * 
 * @author t.tautenhahn
 */
public interface WebGranny
{

  /**
   * Points the browser to specified URL, blocks the current thread until page is loaded (be aware that some
   * scripts may still run).
   * 
   * @param url to open
   * @return this for fluent call
   */
  Scope openUrl(String url);

  /**
   * @return current URL as result of web site interactions.
   */
  String currentUrl();

  /**
   * @return the current page which might change as result of interactions
   */
  Scope currentPage();

  /**
   * Same as {@link #currentPage()} but synchronizes browser and java test.
   * 
   * @param oldPage scope starting with old body element which can be used to recognize the old page by.
   * @param timeout number of seconds to wait for a new page to start loading. The method should return as
   *                soon as a new page is detected.
   * @return new page if it has changed during wait, old page if it is still valid.
   */
  Scope getUpdatedPage(Scope oldPage, int timeout);

  /**
   * Closes all resources like connections or browser windows.
   */
  void closeAll();

}
