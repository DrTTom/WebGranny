package de.tautenhahn.testing.web;

import java.util.List;

import de.tautenhahn.testing.web.With.Property;


/**
 * Scope instance which tries to follow the respective current page even if it changes. Useful as base class
 * for tests so the test does not to have take care of client and current page but simply inherits all the
 * useful methods.
 * 
 * @author jean
 */
public class UpdatingSearchScope extends BasicSearchScope
{

  BasicSearchScope page;

  WebGranny client;

  /**
   * Creates instance around an existing page.
   * 
   * @param initialPage should represent whole page.
   * @param client      driver
   */
  public UpdatingSearchScope(BasicSearchScope initialPage, WebGranny client)
  {
    super(initialPage.filters, initialPage.rootElement);
    page = initialPage;
    this.client = client;
  }

  @Override
  public void pressEnter()
  {
    page.pressEnter();
  }

  @Override
  protected String getUrl()
  {
    return client.currentUrl();
  }

  @Override
  protected List<Element> findElements(List<Property> allFilters, int timeout)
  {
    return page.findElements(allFilters, timeout);
  }

  @Override
  protected BasicSearchScope createSubscope(List<Property> filters, Element newRoot)
  {
    return page.createSubscope(filters, newRoot);
  }

  /**
   * TODO: create listener pattern to call this method with each click and enter!
   */
  void expectPageUpdate()
  {
    page = (BasicSearchScope)client.getUpdatedPage(page, 1);
  }

}