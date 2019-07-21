package de.tautenhahn.testing.web;

import java.util.Collections;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import de.tautenhahn.testing.web.selenium.SeleniumScope;


public class WebGranny
{

  private final WebDriver driver;

  /**
   * Creates instance. TODO: Underlying driver should be defined by system properties.
   */
  public WebGranny()
  {
    System.setProperty("webdriver.gecko.driver", "/home/jean/bin/geckodriver");
    driver = new FirefoxDriver();
  }

  public WebGranny openUrl(String url)
  {
    driver.get(url);
    return this;
  }

  /**
   * @return current URL as result of web site interactions.
   */
  public String currentUrl()
  {
    return driver.getCurrentUrl();
  }

  public Scope currentPage()
  {
    return new SeleniumScope(Collections.EMPTY_LIST, driver.findElement(By.tagName("body")), driver);
  }

}
