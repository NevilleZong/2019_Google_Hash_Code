public class Matrix
{

  private Point startPoint;
  private Point endPoint;
  private int area;
  private int noOfT;
  private int noOfM;

  public Matrix(Point requiredStartPoint, Point requiredEndPoint, int t, int m)
  {
     startPoint = requiredStartPoint;
     endPoint = requiredEndPoint;
     area = Math.abs(endPoint.getX() - startPoint.getX() + 1)
            * Math.abs(endPoint.getY() - startPoint.getY() + 1);
    noOfM = m;
    noOfT = t;
  } // Constructor

  public Matrix(int startPointRow, int startPointColumn,
                int endPointRow, int endPointColumn,
                int t, int m)
  {
    startPoint = new Point(startPointRow, startPointColumn);
    endPoint = new Point(endPointRow, endPointColumn);
    area = Math.abs(endPoint.getX() - startPoint.getX() + 1)
           * Math.abs(endPoint.getY() - startPoint.getY() + 1);
    noOfT = t;
    noOfM = m;
  } // Constructor

  public Point getStartPoint()
  {
    return startPoint;
  } // method getStartPoint

  public Point getEndPoint()
  {
    return endPoint;
  } // method getEndPoint

  public int getArea()
  {
    return area;
  } // method getArea

  @Override
  public String toString()
  {
    // Only for test.
    return "Matrix from " + startPoint + " to " + endPoint
            + " with area " + area + ", tomato: " + noOfT + " mashroom: " + noOfM;
  } // method toString

}
