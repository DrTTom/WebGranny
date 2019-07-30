package de.tautenhahn.testing.web.selenium;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

  private static final String SCRIPT;
  static
  {
    try (InputStream ins = SeleniumScope.class.getResourceAsStream("FindElement.js");
      Scanner sc = new Scanner(ins, StandardCharsets.UTF_8).useDelimiter("\\A"))
    {
      SCRIPT = sc.next();
    }
    catch (IOException e)
    {
      throw new IllegalStateException(e);
    }
  }

  SeleniumScope(List<Property> filters, SeleniumElement root, WebDriver driver)
  {
    super(filters, root);
    this.driver = driver;
  }

  /**
   * Note that Selenium does not obtain a web page but rather observes a browser which does. Therefore, it
   * supports no efficient way to filter page elements as java objects. That is why selection is done within
   * the original web page by executing a java script. The DOM tree is changed by adding missing id attributes
   * which should not disturb the application. If it does, you probably should think about the applications
   * inner logic.
   */
  @Override
  protected List<Element> findElements(List<Property> allFilters, int timeout)
  {
    String rootSel = Optional.ofNullable(rootElement.getAttribute("id"))
                             .filter(i -> !i.isEmpty())
                             .map(i -> '#' + i)
                             .orElse("body");
    StringBuilder sb = new StringBuilder(SCRIPT).append("return _list_('").append(rootSel).append("', '*')");
    allFilters.forEach(f -> sb.append(".filter(e=>").append(f.getJsFilter()).append(')'));
    sb.append(".map(_desc_)");
    List<Map<String, Object>> list = getList(sb.toString(), System.currentTimeMillis() + timeout);
    List<Element> result = new ArrayList<>(list.size());
    WebDriverWait wait = new WebDriverWait(driver, 1);
    for ( Map<String, Object> descr : list )
    {
      try
      {
        WebElement elem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id((String)descr.get("id"))));
        result.add(new SeleniumElement(elem, descr));
      }
      catch (org.openqa.selenium.TimeoutException e)
      {
        // TODO: handle
        return null;
      }
    }
    return result;
  }

  private List<Map<String, Object>> getList(String script, long deadline)
  {
    List<Map<String, Object>> list = (List)((JavascriptExecutor)driver).executeScript(script);
    while (list.isEmpty() && System.currentTimeMillis() < deadline)
    {
      try
      {
        Thread.sleep(500);
      }
      catch (InterruptedException e1)
      {
        Thread.currentThread().interrupt();
      }
      list = (List)((JavascriptExecutor)driver).executeScript(script);
    }
    return list;
  }

  @Override
  protected BasicSearchScope createSubscope(List<Property> filters, Element root)
  {
    return new SeleniumScope(filters, (SeleniumElement)root, driver);
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

  /**
   * @return the Selenium object describing the current root element.
   */
  public WebElement getRootWebElement()
  {
    return ((SeleniumElement)rootElement).elem;
  }
}
