package de.tautenhahn.testing.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import de.tautenhahn.testing.web.With.Property;


/**
 * Implements all the convenience methods base on the ones really needed to implement.
 * 
 * @author t.tautenhahn
 */
public abstract class BasicSearchScope implements Scope
{

  final List<Property> filters;

  protected final Element rootElement;

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
    return findElements(all, 2000).sorted((a, b) -> 0)
                                  .findFirst()
                                  .orElseThrow(() -> new ElementNotFoundException(getUrl(), rootElement, all,
                                                                                  rootElement.getText()));
  }

  /**
   * @return the current URL this scope belongs to
   */
  protected abstract String getUrl();

  @Override
  public List<Element> listElements(Property... filter)
  {
    return findElements(merge(filter), 0).collect(Collectors.toList());
  }

  /**
   * Return first element matching all filters.
   * 
   * @param allFilters
   * @return null if no such element
   */
  protected abstract Stream<Element> findElements(List<Property> allFilters, int timeout);

  /**
   * Similar to clone(), but may return value of other class.
   * 
   * @param filters
   * @param newRoot
   * @return lightweight instance.
   */
  protected abstract BasicSearchScope createSubscope(List<Property> filters, Element newRoot);

  @Override
  public Element findElement(String marker, Property... filter)
  {
    return findElement(merge(filter, With.description(marker)));
  }

  @Override
  public Element findLink(String marker, Property... filter)
  {
    return findElement(merge(filter, With.description(marker), With.tagName("A")));
  }

  @Override
  public Scope after(String marker, Property... filter)
  {
    return byBoundary(With::locationAfter, marker, filter);
  }

  @Override
  public Scope before(String marker, Property... filter)
  {
    return byBoundary(With::locationBefore, marker, filter);
  }

  @Override
  public Scope inSameLineAs(String marker, Property... filter)
  {
    return byBoundary(With::locationBeside, marker, filter);
  }

  @Override
  public Scope in(String marker, Property... filter)
  {
    return createSubscope(filters, findElement(marker, filter));
  }

  private Scope byBoundary(Function<Element, Property> prop, String marker, Property... filter)
  {
    Element boundary = findElement(marker, filter);
    return createSubscope(merge(new Property[]{prop.apply(boundary)}), rootElement);
  }


  private List<Property> merge(Property[] a, Property... b)
  {
    List<Property> result = new ArrayList<>(filters);
    result.addAll(Arrays.asList(a));
    result.addAll(Arrays.asList(b));
    return result;
  }

}
