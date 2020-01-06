package de.tautenhahn.testing.web;

import java.util.List;
import java.util.stream.Stream;

import de.tautenhahn.testing.web.With.Property;


/**
 * Scope instance which tries to follow the respective current page even if it changes. Useful as base class
 * for tests so the test does not to have take care of client and current page but simply inherits all the
 * useful methods.
 * 
 * @author jean
 */
public class UpdatingSearchScope extends BasicSearchScope implements PageUpdateListener
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
    super(initialPage.filters, initialPage.getRootElement());
    listener = this;
    page = initialPage;
    this.client = client;
  }

  /**
   * Alternative constructor.
   * 
   * @param client     web driver
   * @param initialUrl URL of the initial page
   */
  public UpdatingSearchScope(WebGranny client, String initialUrl)
  {
    this((BasicSearchScope)client.openUrl(initialUrl), client);

  }

  @Override
  public void pressEnter()
  {
    page.pressEnter();
    pagePossiblyUpdated();
  }

  @Override
  protected String getUrl()
  {
    return client.currentUrl();
  }

  @Override
  protected Stream<Element> findElements(List<Property> allFilters, int timeout)
  {
    return page.findElements(allFilters, timeout);
  }

  @Override
  protected BasicSearchScope createSubscope(List<Property> filters,
                                            Element newRoot,
                                            PageUpdateListener listener)
  {
    return page.createSubscope(filters, newRoot, listener);
  }

  @Override
  public boolean handleAlert(String message, String input, boolean doAccept)
  {
    client.handleAlert(message, input, doAccept);
    return true;
  }

  @Override
  public void pagePossiblyUpdated()
  {
    page = (BasicSearchScope)client.getUpdatedPage(page, 1);
  }

}
