package de.tautenhahn.testing.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import de.tautenhahn.testing.web.selenium.SeleniumConfiguration;
import de.tautenhahn.testing.web.selenium.SeleniumWebGranny;


/**
 * Some unit tests, should be sorted into other classes later
 * 
 * @author t.tautenhahn
 */
public class ExampleTest
{

  private WebGranny createGranny() throws IOException
  {
    try (InputStream ins = ExampleTest.class.getResourceAsStream("/config.json"))
    {
      SeleniumConfiguration conf = SeleniumConfiguration.parse(ins);
      return new SeleniumWebGranny(conf);
    }
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
  @Disabled("no own test infrastructure, do not disturb google with senseless searches")
  public void searchSomething() throws Exception
  {
    WebGranny granny = createGranny();
    granny.openUrl("http://www.google.de");
    Scope page = granny.currentPage();

    // short for "click your mouse in that field and then on the keyboard ... press Enter."
    page.findElement("Suche").type("Computer für Senioren");
    page.pressEnter();

    // TODO: sort out the wait;
    Thread.sleep(1000);
    page = granny.currentPage();
    page.after("Ungefähr .* Egebnisse .*").findLink(".* Die Anleitung in Bildern .*").click();

    assertThat(granny.currentUrl()).contains("amazon");

    // would be better to ask for an element "which is a header"
    String found = page.findElement("Kunden, die diesen Artikel .*", With.tagName("h2")).text();
    assertThat(found).endsWith("kauften auch");
    // granny.quit();
  }

  /**
   * In case IDE does not support JUnit 5.
   * 
   * @param args
   * @throws IOException
   */
  public static void main(String[] args) throws IOException
  {
    new ExampleTest().findElements();
  }

  /**
   * Selects some elements on a local example page.
   * 
   * @throws IOException
   */
  @Test
  public void findElements() throws IOException
  {
    WebGranny granny = createGranny();
    Scope page = granny.openUrl("file://"
                                + Paths.get("src", "test", "resources", "testPage.html").toAbsolutePath());
    assertThat(page).isNotNull();
    granny.closeAll();
  }
}
