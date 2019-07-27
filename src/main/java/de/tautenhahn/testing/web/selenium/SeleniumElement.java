package de.tautenhahn.testing.web.selenium;

import org.openqa.selenium.WebElement;

import de.tautenhahn.testing.web.Element;


/**
 * Element implementation based on Selenium
 * 
 * @author t.tautenhahn
 */
public class SeleniumElement implements Element
{

  final WebElement elem;


  SeleniumElement(WebElement elem)
  {
    this.elem = elem;
  }

  @Override
  public void click()
  {
    elem.click();
  }

  @Override
  public String getAttribute(String name)
  {
    return elem.getAttribute(name);
  }

  @Override
  public void type(String content)
  {
    elem.sendKeys(content);
  }

  @Override
  public String getTagName()
  {
    return elem.getTagName();
  }

  @Override
  public String text()
  {
    return elem.getText();
  }



}
