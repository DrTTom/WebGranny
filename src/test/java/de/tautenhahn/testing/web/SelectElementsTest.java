package de.tautenhahn.testing.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.tautenhahn.testing.web.WebGranny.Check;
import de.tautenhahn.testing.web.selenium.SeleniumConfiguration;
import de.tautenhahn.testing.web.selenium.SeleniumWebGranny;


/**
 * Some unit tests, should be sorted into other classes later
 * 
 * @author t.tautenhahn
 */
public class SelectElementsTest
{

  private static final String URL = "file://"
    + Paths.get("src", "test", "resources", "testPage.html").toAbsolutePath();

  private static WebGranny granny;

  @BeforeAll
  static void openBrowser() throws IOException
  {
    try (InputStream ins = SelectElementsTest.class.getResourceAsStream("/config.json"))
    {
      SeleniumConfiguration conf = SeleniumConfiguration.parse(ins);
      granny = new SeleniumWebGranny(conf);
    }
  }

  @AfterAll
  static void closeBrowser()
  {
    granny.closeAll();
  }


  /**
   * Selects some elements on a local example page. Assert that always the nearest matching element is found.
   * 
   * @throws IOException
   */
  @Test
  public void findElements() throws IOException
  {
    granny.setGenericPageChecks();
    Scope page = granny.openUrl(URL);

    assertThat(page.findHeader("Example.*").getText()).isEqualTo("Example page");

    page.findElement("Enter some text .*", With.tagName("INPUT")).doType("hello!");
    assertThat(page.findElement("FINDME.*").getText()).isEqualTo("FINDME#1");
    assertThat(page.in("A special container.*").findElement("FINDME.*").getText()).isEqualTo("FINDME#3");

    // should revert search preference to last matching:
    // assertThat(page.before("A special container").findElement("FINDME.*").getText()).isEqualTo("FINDME#2");
  }

  /**
   * Selecting an element which does not exist should throw an Exception.
   */
  @Test
  public void elementNotFound()
  {
    granny.setGenericPageChecks();
    Scope page = granny.openUrl(URL);
    assertThatThrownBy(() -> page.findElement("jibbetnich",
                                              With.tagName("NOSUCHTAG"))).isInstanceOf(ElementNotFoundException.class);
  }

  /**
   * Loading an invalid HTML while generic check is enabled should throw an Exception.
   */
  @Test
  public void htmlErrors()
  {
    granny.setGenericPageChecks(Check.ILLEGAL_FOR);
    assertThatThrownBy(() -> granny.openUrl(URL)).hasMessageContaining("for attribute '456' of label 'Here is some stuff violating HTML syntax!'");
  }
}
