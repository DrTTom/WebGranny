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
   * <li>
   * <ul>
   * has been triggered but not performed yet,
   * </ul>
   * <ul>
   * does not take place.
   * </ul>
   * </li>
   */
  void pagePossiblyUpdated();
}
