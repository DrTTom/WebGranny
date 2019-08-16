package de.tautenhahn.testing.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import de.tautenhahn.testing.web.selenium.SeleniumConfiguration;
import de.tautenhahn.testing.web.selenium.SeleniumWebGranny;


/**
 * Some unit tests, should be sorted into other classes later
 * 
 * @author t.tautenhahn
 */
public class SelectElementsTest
{

  private WebGranny createGranny() throws IOException
  {
    try (InputStream ins = SelectElementsTest.class.getResourceAsStream("/config.json"))
    {
      SeleniumConfiguration conf = SeleniumConfiguration.parse(ins);
      return new SeleniumWebGranny(conf);
    }
  }

  /**
   * Selects some elements on a local example page. Assert that always the nearest matching element is found.
   * 
   * @throws IOException
   */
  @Test
  public void findElements() throws IOException
  {
    WebGranny granny = createGranny();
    Scope page = granny.openUrl("file://"
      + Paths.get("src", "test", "resources", "testPage.html").toAbsolutePath());

    assertThat(page.findHeader("Example.*").getText()).isEqualTo("Example page");

    page.findElement("Enter some text .*", With.tagName("INPUT")).doType("hello!");
    assertThat(page.findElement("FINDME.*").getText()).isEqualTo("FINDME#1");
    assertThat(page.in("A special container.*").findElement("FINDME.*").getText()).isEqualTo("FINDME#3");

    // should revert search preference to last matching:
    // assertThat(page.before("A special container").findElement("FINDME.*").getText()).isEqualTo("FINDME#2");
    granny.closeAll();
  }
}
