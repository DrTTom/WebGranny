package de.tautenhahn.testing.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import de.tautenhahn.testing.web.selenium.SeleniumConfiguration;
import de.tautenhahn.testing.web.selenium.SeleniumWebGranny;


/**
 * Especially checks timing and reload issues if {@link UpdatingSearchScope}.
 * 
 * @author t.tautenhahn
 */
public class UpdatingSearchScopeTest extends UpdatingSearchScope
{

  // TODO: implement me
  private static final String URL = "file://"
    + Paths.get("src", "test", "resources", "testPage.html").toAbsolutePath();

  private static final WebGranny GRANNY;

  static
  {
    try (InputStream ins = ExampleTest.class.getResourceAsStream("/config.json"))
    {
      SeleniumConfiguration conf = SeleniumConfiguration.parse(ins);
      GRANNY = new SeleniumWebGranny(conf);
      GRANNY.setGenericPageChecks();
    }
    catch (IOException e)
    {
      throw new IllegalStateException(e);
    }
  }

  UpdatingSearchScopeTest()
  {
    super(GRANNY, URL);
  }


  /**
   * Performs web site operations without explicitly waiting for anything to reload. Especially, select an
   * element which is added to the page only some time after rendering is completed.
   */
  @Test
  void changePage()
  {
    clickOn("Just a link.");
    assertThat(findElement("delayed element").getTagName()).isEqualTo("label");
    // TODO: implement me
    GRANNY.closeAll();
  }
}
