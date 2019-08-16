package de.tautenhahn.testing.web.selenium;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import de.tautenhahn.testing.web.Scope;
import de.tautenhahn.testing.web.WebGranny;


/**
 * Implementation based on Selenium
 * 
 * @author t.tautenhahn
 */
public class SeleniumWebGranny implements WebGranny
{

  private static final String SCRIPT = SeleniumScope.readResource("SyntaxCheck.js");

  private final WebDriver driver;

  /**
   * used to adjust all the waits according to system speed.
   */
  public static final long WAIT_FACTOR = 100;

  /**
   * Creates instance.
   * 
   * @param config specifies which driver to use.
   */
  public SeleniumWebGranny(SeleniumConfiguration config)
  {
    config.getBrowsers().get(config.getCurrentBrowser()).installProperty();
    driver = new FirefoxDriver();
  }

  @Override
  public Scope openUrl(String url)
  {
    driver.get(url);
    return currentPage();
  }

  @Override
  public String currentUrl()
  {
    return driver.getCurrentUrl();
  }

  @Override
  public Scope currentPage()
  {
    performPageChecks();

    Map<String, Object> coversAll = Map.of("top", 0, "bottom", 20_000, "left", 0, "right", 20_000);
    return new SeleniumScope(Collections.emptyList(),
                             new SeleniumElement(driver.findElement(By.tagName("body")), coversAll), driver);
  }

  // TODO: make checks configurable, throw Exception for failing test
  private void performPageChecks()
  {
    Map<String, Object> result = (Map<String, Object>)((JavascriptExecutor)driver).executeScript(SCRIPT);
    List<String> ids = (List<String>)result.get("ids");
    Set<String> set = new HashSet<>();
    ids.stream().filter(id -> !set.add(id)).forEach(id -> System.out.println("duplicate id " + id));
    List<Map<String, String>> labels = (List<Map<String, String>>)result.get("labels");
    labels.stream()
          .filter(l -> !ids.contains(l.get("for")))
          .forEach(l -> System.out.println("label '" + l.get("label") + "' has illegal for attribute "
            + l.get("for")));
    if (!((boolean)result.get("contentType")))
    {
      System.out.println("missing content type declaration");
    }
    ((List<String>)result.get("wrongImgs")).forEach(i -> System.out.println("missing alt attribute for image "
      + i));
  }

  @Override
  public void closeAll()
  {
    driver.quit();
  }

  @Override
  public Scope getUpdatedPage(Scope oldPage, int timeout)
  {
    if (oldPage instanceof SeleniumScope)
    {
      WebElement oldRoot = ((SeleniumScope)oldPage).getRootWebElement();
      WebDriverWait wait = new WebDriverWait(driver, timeout);
      if (wait.until(ExpectedConditions.stalenessOf(oldRoot)))
      {
        return currentPage();
      }
      return oldPage;
    }
    throw new IllegalArgumentException();
  }
}
