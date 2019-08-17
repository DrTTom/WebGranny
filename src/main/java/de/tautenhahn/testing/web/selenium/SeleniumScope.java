package de.tautenhahn.testing.web.selenium;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import de.tautenhahn.testing.web.BasicSearchScope;
import de.tautenhahn.testing.web.Element;
import de.tautenhahn.testing.web.PageUpdateListener;
import de.tautenhahn.testing.web.With.Property;


/**
 * Scope implementation based on Selenium WebDriver.
 * 
 * @author jean
 */
public class SeleniumScope extends BasicSearchScope
{

  private final WebDriver driver;

  private static final String SCRIPT = readResource("FindElement.js");

  static String readResource(String name)
  {
    try (InputStream ins = SeleniumScope.class.getResourceAsStream(name);
      Scanner sc = new Scanner(ins, StandardCharsets.UTF_8).useDelimiter("\\A"))
    {
      return sc.next();
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
  protected Stream<Element> findElements(List<Property> allFilters, int timeout)
  {
    String rootSel = Optional.ofNullable(getRootElement().getId())
                             .filter(i -> !i.isEmpty())
                             .map(i -> "*[id=\"" + i + "\"]")
                             .orElse("body");
    StringBuilder sb = new StringBuilder(SCRIPT).append("return _list_('").append(rootSel).append("', '*')");
    allFilters.forEach(f -> sb.append(".filter(e=>").append(f.getJsFilter()).append(')'));
    sb.append(".map(_desc_)");
    List<Map<String, Object>> list = getList(sb.toString(), System.currentTimeMillis() + timeout);
    WebDriverWait wait = timeout > 0 ? new WebDriverWait(driver, 1) : null;
    return list.stream()
               .filter(descr -> descr.get("top") != descr.get("bottom")
                 || descr.get("left") != descr.get("right")) // might change one but not both to visibility
               .map(descr -> fetchElement(descr, wait))
               .filter(Objects::nonNull);
  }

  private Element fetchElement(Map<String, Object> descr, WebDriverWait wait)
  {
    By selector = By.id((String)descr.get("id"));
    try
    {
      WebElement elem = wait == null ? driver.findElement(selector)
        : wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
      return elem.isDisplayed() ? new SeleniumElement(elem, descr) : null;
    }
    catch (org.openqa.selenium.TimeoutException e)
    {
      return null;
    }
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
  protected SeleniumScope createSubscope(List<Property> filters, Element root, PageUpdateListener listener)
  {
    SeleniumScope result = new SeleniumScope(filters, (SeleniumElement)root, driver);
    result.listener = listener;
    return result;

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

  WebElement getRootWebElement()
  {
    return ((SeleniumElement)getRootElement()).elem;
  }
}
