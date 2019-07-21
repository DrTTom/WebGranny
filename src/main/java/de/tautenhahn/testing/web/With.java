package de.tautenhahn.testing.web;

import java.util.function.Predicate;


// TODO: return pairs of element Predicates and JS filter expressions!
public class With
{

  public static Predicate<Element> tagName(String name)
  {
    return e -> name.equals(e.getTagName()) || e.getTagName().matches(name);
  }

  public static Predicate<Element> marker(String marker)
  {
    return e -> e.isRecognizableBy(marker);
  }

}
