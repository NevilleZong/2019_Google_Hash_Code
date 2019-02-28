public class Point
{

  private int x;
  private int y;

  public Point(int requiredX, int requiredY)
  {
    x = requiredX;
    y = requiredY;
  } // Constructor

  public int getX()
  {
    return x;
  } // method getX

  public int getY()
  {
    return y;
  } // method getY

  public String toString()
  {
    return "" + x + " " + y;
  }
} // class Point
