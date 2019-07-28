package de.tautenhahn.testing.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.tautenhahn.testing.web.With.Property;


/**
 * Implements all the convenience methods base on the ones really needed to implement.
 * 
 * @author t.tautenhahn
 */
public abstract class BasicSearchScope implements Scope
{

  final List<Property> filters;

  final Element rootElement;

  /**
   * Creates immutable instance. Instance will become stale after page reload.
   * 
   * @param filters
   * @param rootElement
   */
  protected BasicSearchScope(List<Property> filters, Element rootElement)
  {
    this.filters = filters;
    this.rootElement = rootElement;
  }

  @Override
  public Element findElement(Property... filter)
  {
    return findElement(merge(filter));
  }

  @Override
  public Element findHeader(String marker, Property... filter)
  {
    return findElement(merge(filter, With.description(marker), With.tagName("H[1-6]")));
  }

  private Element findElement(List<Property> all)
  {
    return findElements(all, 2000).stream()
                                  .sorted((a, b) -> 0)
                                  .findFirst()
                                  .orElseThrow(() -> new ElementNotFoundException(getUrl(), rootElement, all,
                                                                                  rootElement.text()));
  }

  /**
   * @return the current URL this scope belongs to
   */
  protected abstract String getUrl();

  /**
   * Return first element matching all filters.
   * 
   * @param allFilters
   * @return null if no such element
   */
  protected abstract List<Element> findElements(List<Property> allFilters, int timeout);

  /**
   * Similar to clone(), but may return value of other class.
   * 
   * @return lightweight instance.
   */
  protected abstract BasicSearchScope createSubscope();

  @Override
  public Element findElement(String marker, Property... filter)
  {
    return findElement(merge(filter, With.description(marker)));
  }

  @Override
  public Element findLink(String marker)
  {
    return findElement(marker); // TODO Auto-generated method stub, filter for "looks like link"
  }

  @Override
  public Scope after(String marker, Property... filter)
  {
    return null; // TODO: implement
  }

  private List<Property> merge(Property[] a, Property... b)
  {
    List<Property> result = new ArrayList<>(filters);
    result.addAll(Arrays.asList(a));
    result.addAll(Arrays.asList(b));
    return result;
  }
}
