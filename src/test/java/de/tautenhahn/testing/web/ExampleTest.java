package de.tautenhahn.testing.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import de.tautenhahn.testing.web.selenium.SeleniumConfiguration;
import de.tautenhahn.testing.web.selenium.SeleniumWebGranny;


/**
 * Examples how web application tests can look like.
 * 
 * @author t.tautenhahn
 */
public class ExampleTest extends UpdatingSearchScope
{

  private static final String START_PAGE = "http://www.google.de";

  private static final WebGranny granny;

  static
  {
    try (InputStream ins = ExampleTest.class.getResourceAsStream("/config.json"))
    {
      SeleniumConfiguration conf = SeleniumConfiguration.parse(ins);
      granny = new SeleniumWebGranny(conf);
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
    super(granny, START_PAGE);
  }

  @AfterAll
  static void tearDown()
  {
    granny.closeAll();
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
  @Disabled("this is just an example, do not disturb google with senseless searches")
  public void searchSomething() throws Exception
  {
    // short for "click your mouse in that field and then on the keyboard ..."
    findElement("Suche").doType("Computer für Senioren");
    pressEnter();

    after("Ungefähr .* Ergebnisse .*").findLink(".*Anleitung in Bildern.*").click();

    assertThat(granny.currentUrl()).contains("amazon");
    String found = page.findHeader("Kunden, die diesen Artikel .*").getText();
    assertThat(found).endsWith("kauften auch");
  }

}
