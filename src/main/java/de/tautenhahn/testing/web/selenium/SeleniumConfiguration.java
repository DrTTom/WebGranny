package de.tautenhahn.testing.web.selenium;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import com.google.gson.Gson;


/**
 * Configuration of test framework.
 * 
 * @author t.tautenhahn
 */
public class SeleniumConfiguration
{

  private String hubUrl;

  private String currentBrowser;

  private Map<String, Browser> browsers;

  /**
   * Parses from JSON
   * 
   * @param ins contains configuration data as JSON
   * @return parsed configuration
   * @throws IOException
   */
  public static SeleniumConfiguration parse(InputStream ins) throws IOException
  {
    try (Reader r = new InputStreamReader(ins, StandardCharsets.UTF_8))
    {
      return new Gson().fromJson(r, SeleniumConfiguration.class);
    }
  }

  String getHubUrl()
  {
    return hubUrl;
  }


  String getCurrentBrowser()
  {
    return currentBrowser;
  }

  /**
   * @return the browser definitions
   */
  public Map<String, Browser> getBrowsers()
  {
    return browsers;
  }

  /**
   * Browser parameters
   */
  static class Browser
  {

    private String driverPath;

    private String driverProp;

    private String type;

    private String args;

    private String platform;

    boolean installProperty()
    {
      if (driverProp == null)
      {
        return false;
      }
      System.setProperty(driverProp, driverPath);
      return true;
    }

    String getType()
    {
      return type;
    }

    String getArgs()
    {
      return args;
    }

    String getPlatform()
    {
      return platform;
    }
  }

}
