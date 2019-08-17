package de.tautenhahn.testing.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import de.tautenhahn.testing.web.WebGranny.Check;
import de.tautenhahn.testing.web.selenium.SeleniumConfiguration;
import de.tautenhahn.testing.web.selenium.SeleniumWebGranny;


/**
 * Examples how web application tests can look like.
 * 
 * @author t.tautenhahn
 */
@Disabled("this is just an example, do not disturb google with senseless searches")
public class ExampleTest extends UpdatingSearchScope
{

  private static final String START_PAGE = "http://www.google.de";

  private static final WebGranny GRANNY;

  // first some ugly technical stuff - it is over after this block
  static
  {
    try (InputStream ins = ExampleTest.class.getResourceAsStream("/config.json"))
    {
      SeleniumConfiguration conf = SeleniumConfiguration.parse(ins);
      GRANNY = new SeleniumWebGranny(conf);
      // Must de-activate most of the implicit page checks because most sides in the WWW are not correct HTML.
      // In other words, two of the biggest players in the web fail the tests.
      GRANNY.setGenericPageChecks(Check.ILLEGAL_FOR);
    }
    catch (IOException e)
    {
      throw new IllegalStateException(e);
    }
  }

  /**
   * The base class is a search scope, so in the constructor you have to supply a configured web client as
   * well as a starting page.
   * 
   * @throws IOException
   */
  public ExampleTest() throws IOException
  {
    super(GRANNY, START_PAGE);
  }

  /**
   * Close all windows after the test is done.
   */
  @AfterAll
  static void tearDown()
  {
    GRANNY.closeAll();
  }

  /**
   * Example how a test could look. Statements should describe what a user is supposed to do. Imagine telling
   * your granny on the telephone how to do it. With each action, implicitly assert that the action can be
   * performed, especially that the elements can be found. Technical actions like "select by some CSS selector
   * and check attribute XY" should be possible but discouraged.
   * 
   * @throws InterruptedException
   */
  @Test
  public void searchSomething() throws Exception
  {
    // short for "click your mouse in that field and then on the keyboard ..."
    findElement("Suche").doType("Computer für Senioren");
    pressEnter(); // waiting for the page to reload is handled implicitly

    after("Ungefähr .* Ergebnisse .*").findLink(".*Anleitung in Bildern.*").click();

    assertThat(GRANNY.currentUrl()).contains("amazon");
    String found = page.findHeader("Kunden, die diesen Artikel .*").getText();
    assertThat(found).endsWith("kauften auch");
  }

}
