package de.tautenhahn.testing.web.selenium;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import de.tautenhahn.testing.web.DuplicateIdException;
import de.tautenhahn.testing.web.IllegalIdRefException;
import de.tautenhahn.testing.web.MissingAltAttributeException;
import de.tautenhahn.testing.web.MissingContentTypeException;
import de.tautenhahn.testing.web.Scope;
import de.tautenhahn.testing.web.WebGranny;
import de.tautenhahn.testing.web.selenium.SeleniumConfiguration.Browser;


/**
 * Implementation based on Selenium
 * 
 * @author t.tautenhahn
 */
public class SeleniumWebGranny implements WebGranny
{

  private static final String SCRIPT = SeleniumScope.readResource("SyntaxCheck.js");

  private final WebDriver driver;

  private Set<Check> activePageChecks = Set.of(Check.values());

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
    Browser browserConf = config.getBrowsers().get(config.getCurrentBrowser());
    String prop = browserConf.installProperty();
    driver = createDriver(browserConf, prop);
  }

  private WebDriver createDriver(Browser browserConf, String prop)
  {
    if (prop == null)
    {
      throw new UnsupportedOperationException("TODO: implement remote driver usage");
    }
    switch (prop)
    {
      case "webdriver.gecko.driver":
        FirefoxOptions options = new FirefoxOptions();
        Optional.ofNullable(browserConf.getArgs()).ifPresent(a -> options.addArguments(a.split(" ")));
        return new FirefoxDriver(options);
      case "webdriver.chrome.driver":
        ChromeOptions copt = new ChromeOptions();
        Optional.ofNullable(browserConf.getArgs()).ifPresent(a -> copt.addArguments(a.split(" ")));
        return new ChromeDriver(copt);
      case "webdriver.edge.driver":
        throw new UnsupportedOperationException("TODO: implement me");
      default:
        throw new IllegalStateException("unimplemented case " + prop);
    }
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

  private void performPageChecks()
  {
    if (activePageChecks.isEmpty())
    {
      return;
    }
    Map<String, Object> result = (Map<String, Object>)((JavascriptExecutor)driver).executeScript(SCRIPT);
    List<String> ids = (List<String>)result.get("ids");
    for ( Check check : activePageChecks )
    {
      switch (check)
      {
        case DUPLICATE_ID:
          Set<String> set = new HashSet<>();
          ids.stream().filter(id -> !set.add(id)).forEach(id -> {
            throw new DuplicateIdException(currentUrl(), id,
                                           driver.findElement(By.tagName("body")).getText());
          });
          break;
        case ILLEGAL_FOR:
          List<Map<String, String>> labels = (List<Map<String, String>>)result.get("labels");
          labels.stream().filter(l -> !ids.contains(l.get("for"))).forEach(l -> {
            throw new IllegalIdRefException(currentUrl(), l.get("label"), l.get("for"),
                                            driver.findElement(By.tagName("body")).getText());
          });
          break;
        case MISSING_CONTENT_TYPE:
          if (!((boolean)result.get("contentType")))
          {
            throw new MissingContentTypeException(currentUrl(),
                                                  driver.findElement(By.tagName("body")).getText());
          }
          break;
        case MISSING_ALT_ATTRIBUTE:
          ((List<String>)result.get("wrongImgs")).forEach(i -> {
            throw new MissingAltAttributeException(currentUrl(), i,
                                                   driver.findElement(By.tagName("body")).getText());
          });
          break;
        default:
          throw new UnsupportedOperationException("unimplemented check " + check);
      }
    }
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

  @Override
  public void setGenericPageChecks(Check... checks)
  {
    activePageChecks = Set.of(checks);
  }
}
