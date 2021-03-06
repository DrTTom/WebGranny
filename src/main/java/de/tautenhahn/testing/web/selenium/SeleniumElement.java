package de.tautenhahn.testing.web.selenium;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;

import de.tautenhahn.testing.web.BoundingRectangle;
import de.tautenhahn.testing.web.Element;
import de.tautenhahn.testing.web.PageUpdateListener;


/**
 * Element implementation based on Selenium
 * 
 * @author t.tautenhahn
 */
public class SeleniumElement implements Element
{

  final WebElement elem;

  private final String id;

  private final BoundingRectangle position;

  private final List<PageUpdateListener> listeners = new ArrayList<>();

  SeleniumElement(WebElement elem, Map<String, Object> descr)
  {
    this.elem = elem;
    position = new BoundingRectangle(((Number)descr.get("top")).intValue(),
                                     ((Number)descr.get("bottom")).intValue(),
                                     ((Number)descr.get("left")).intValue(),
                                     ((Number)descr.get("right")).intValue());
    id = (String)descr.get("id");
  }

  @Override
  public void click()
  {
    elem.click();
    listeners.forEach(PageUpdateListener::pagePossiblyUpdated);
  }

  @Override
  public void clickAndHandleAlert(String message, String input, boolean accept)
  {
    elem.click();
    if (listeners.stream().noneMatch(l-> l.handleAlert(message, input, accept)))
    {
      throw new IllegalStateException("No listener to handle alert");
    }
    listeners.forEach(PageUpdateListener::pagePossiblyUpdated);
  }

  @Override
  public String getAttribute(String name)
  {
    return elem.getAttribute(name);
  }

  @Override
  public void doType(String content)
  {
    elem.sendKeys(content);
  }

  @Override
  public String getTagName()
  {
    return elem.getTagName();
  }

  @Override
  public String getText()
  {
    return elem.getText();
  }

  @Override
  public BoundingRectangle getPosition()
  {
    return position;
  }

  @Override
  public String getId()
  {
    return id;
  }

  @Override
  public void addListener(PageUpdateListener listener)
  {
    listeners.add(listener);
  }

  @Override
  public String toString()
  {
    return elem.toString();
  }
}
