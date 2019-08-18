package de.tautenhahn.testing.web;

/**
 * Describes a rectangle a web element is displayed in.
 * 
 * @author t.tautenhahn
 */
public class BoundingRectangle
{

  private final int top;

  private final int bottom;

  private final int left;

  private final int right;

  /**
   * Creates immutable instance.
   * 
   * @param top    coordinate of upper border
   * @param bottom coordinate of lower border
   * @param left   coordinate of left border
   * @param right  guess
   */
  public BoundingRectangle(int top, int bottom, int left, int right)
  {
    this.top = top;
    this.bottom = bottom;
    this.left = left;
    this.right = right;
  }

  /**
   * @return value
   */
  public int getTop()
  {
    return top;
  }

  /**
   * @return value
   */
  public int getBottom()
  {
    return bottom;
  }

  /**
   * @return value
   */
  public int getLeft()
  {
    return left;
  }

  /**
   * @return value
   */
  public int getRight()
  {
    return right;
  }

}
