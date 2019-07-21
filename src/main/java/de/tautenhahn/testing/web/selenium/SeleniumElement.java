package de.tautenhahn.testing.web.selenium;

import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;

import de.tautenhahn.testing.web.Element;


public class SeleniumElement implements Element
{

  final WebElement elem;


  public SeleniumElement(WebElement elem)
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

  @Override
  public boolean isRecognizableBy(String marker)
  {
    // TODO: implement all the other cases here:
    return matches(elem.getText(), marker) || matches(elem.getAttribute("title"), marker);
  }

  private boolean matches(String value, String requirement)
  {
    return requirement.equals(value) || value != null && value.matches(requirement);
  }

  @Override
  public boolean isSameLineAs(Element boundary)
  {
    Rectangle boundaryPos = ((SeleniumElement)boundary).elem.getRect();
    Rectangle myPos = elem.getRect();
    System.out.println(boundaryPos + ", " + myPos);
    return false;
  }

  @Override
  public boolean isBelow(Element boundary)
  {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isRightOf(Element boundary)
  {
    // TODO Auto-generated method stub
    return false;
  }

}
