package de.tautenhahn.testing.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;


/**
 * Implements all the convenience methods base on the ones really needed to implement.
 * 
 * @author t.tautenhahn
 */
public abstract class BasicSearchScope implements Scope
{

  final List<Predicate<Element>> filters;

  final Element rootElement;

  /**
   * Creates immutable instance. Instance will become stale after page reload.
   * 
   * @param filters
   * @param rootElement
   */
  protected BasicSearchScope(List<Predicate<Element>> filters, Element rootElement)
  {
    this.filters = filters;
    this.rootElement = rootElement;
  }

  @Override
  public Element findElement(Predicate<Element>... filter)
  {
    List<Predicate<Element>> allFilters = new ArrayList<>(filters);
    Arrays.stream(filter).forEach(allFilters::add);
    return findElement(allFilters);
  }

  /**
   * Return first element matching all filters.
   * 
   * @param allFilters
   * @return null if no such element
   */
  protected abstract Element findElement(List<Predicate<Element>> allFilters);

  /**
   * Similar to clone(), but may return value of other class.
   * 
   * @return lightweight instance.
   */
  protected abstract BasicSearchScope createSubscope();

  @Override
  public Element findElement(String marker, Predicate<Element>... filter)
  {
    List<Predicate<Element>> allFilters = new ArrayList<>(filters);
    allFilters.add(With.marker(marker));
    Arrays.stream(filter).forEach(allFilters::add);
    return findElement(allFilters);
  }

  @Override
  public Element findLink(String marker)
  {
    return findElement(marker); // TODO Auto-generated method stub, filter for "looks like link"
  }

  @Override
  public Scope after(String marker, Predicate<Element>... filter)
  {
    Element boundary = findElement(marker, filter);
    BasicSearchScope result = createSubscope();
    result.filters.add(e -> e.isSameLineAs(boundary) && e.isRightOf(boundary) || e.isBelow(boundary));
    return result;
  }


}
