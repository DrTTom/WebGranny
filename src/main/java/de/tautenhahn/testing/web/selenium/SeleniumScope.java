package de.tautenhahn.testing.web.selenium;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import de.tautenhahn.testing.web.BasicSearchScope;
import de.tautenhahn.testing.web.Element;
import de.tautenhahn.testing.web.With.Property;


/**
 * Scope implementation based on Selenium WebDriver.
 * 
 * @author jean
 */
public class SeleniumScope extends BasicSearchScope
{

  private final WebDriver driver;

  private final WebElement root;

  SeleniumScope(List<Property> filters, WebElement rootElement, WebDriver driver)
  {
    super(filters, new SeleniumElement(rootElement));
    this.driver = driver;
    this.root = rootElement;
  }

  @Override
  protected List<Element> findElements(List<Property> allFilters, int timeout)
  {
    // String searchId = UUID.randomUUID().toString();
    // TODO: insert searchId, do not return whole list, SearchID is used to get Selenium elements on one
    // call.
    // List list = (ArrayList)((JavascriptExecutor)driver).executeScript("return
    // [...document.querySelectorAll(\"*\")].filter(e => { return e.tagName=='INPUT'}).map(e=>{return
    // {tagName:(e.tagName==undefined?null:e.tagName),class:(e.className==undefined?null:e.className),id:(e.id==undefined?null:e.id),href:(e.href==undefined?null:e.href)}})");

    // TODO: Selenium seems not to be able to provide efficient way to list all elements. Implement all
    // relevant filters in JS, re-select
    // element after it is found
    // return root.findElements(By.xpath("//*[pc-search-id='"+searchId+"']"));
    return root.findElements(By.cssSelector("input"))
               .stream()
               .map(SeleniumElement::new)
               .filter(e -> allFilters.stream().allMatch(p -> p.matchesElement(e)))
               .collect(Collectors.toList());

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

  @Override
  protected String getUrl()
  {
    return driver.getCurrentUrl();
  }

}
