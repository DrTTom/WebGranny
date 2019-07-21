package de.tautenhahn.testing.web.selenium;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import de.tautenhahn.testing.web.BasicSearchScope;
import de.tautenhahn.testing.web.Element;


/**
 * Scope implementation based on Selenium WebDriver.
 * 
 * @author jean
 */
public class SeleniumScope extends BasicSearchScope
{

  private final WebDriver driver;

  private final WebElement root;

  public SeleniumScope(List<Predicate<Element>> filters, WebElement rootElement, WebDriver driver)
  {
    super(filters, new SeleniumElement(rootElement));
    this.driver = driver;
    this.root = rootElement;
  }

  @Override
  protected Element findElement(List<Predicate<Element>> allFilters)
  {
    long start = System.currentTimeMillis();
    try
    {
      List list = (ArrayList)((JavascriptExecutor)driver).executeScript("return [...document.querySelectorAll(\"*\")].map(e=>{return {tagName:(e.tagName==undefined?null:e.tagName),class:(e.className==undefined?null:e.className),id:(e.id==undefined?null:e.id),href:(e.href==undefined?null:e.href)}})");

      System.out.println("got js list after " + (System.currentTimeMillis() - start));
      // list.forEach(System.out::println);
      // TODO: Selenium seems not to be able to provide efficient way to list all elements. Implement all
      // relevant filters in JS, re-select
      // element after it is found
      return root.findElements(By.cssSelector("input"))
                 .stream()
                 // .peek(x -> System.out.println(x.getTagName()))
                 .map(SeleniumElement::new)
                 .filter(e -> allFilters.stream().allMatch(p -> p.test(e)))
                 .findFirst()
                 .orElseThrow(() -> new NoSuchElementException());
    }
    finally
    {
      System.out.println("done in " + (System.currentTimeMillis() - start));
    }
  }

  @Override
  protected BasicSearchScope createSubscope()
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void pressEnter()
  {
    driver.switchTo().activeElement().sendKeys(Keys.ENTER);

  }

}
