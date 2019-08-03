package de.tautenhahn.testing.web.selenium;

import java.util.Collections;
import java.util.Map;

import org.openqa.selenium.By;
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
    Map<String, Object> coversAll = Map.of("top", 0, "bottom", 20_000, "left", 0, "right", 20_000);
    return new SeleniumScope(Collections.emptyList(),
                             new SeleniumElement(driver.findElement(By.tagName("body")), coversAll), driver);
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
