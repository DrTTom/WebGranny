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
   * Closes all resources like connections or browser windows.
   */
  void closeAll();
}
