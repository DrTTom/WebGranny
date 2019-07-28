package de.tautenhahn.testing.web.selenium;

import java.util.Collections;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

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
    return new SeleniumScope(Collections.emptyList(), driver.findElement(By.tagName("body")), driver);
  }

  @Override
  public void closeAll()
  {
    driver.quit();
  }
}
