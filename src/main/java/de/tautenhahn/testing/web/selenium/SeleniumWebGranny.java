package de.tautenhahn.testing.web.selenium;

import java.util.Collections;
import java.util.Map;

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
    Map<String, Object> coversAll = Map.of("top", 0, "bottom", 20_000, "left", 0, "right", 20_000);
    return new SeleniumScope(Collections.emptyList(),
                             new SeleniumElement(driver.findElement(By.tagName("body")), coversAll), driver);
  }

  @Override
  public void closeAll()
  {
    driver.quit();
  }
}
