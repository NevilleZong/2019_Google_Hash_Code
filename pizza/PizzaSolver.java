import java.io.File;
import java.util.Scanner;

public class PizzaSolver
{
  private static final int ARRAY_INITIAL_SIZE = 10;
  private static final int ARRAY_RESIZE_FACTOR = 2;
  private static Scanner pizzaScanner;
  private static boolean[][] available;
  private static char[][] pizza;
  private static Matrix[] possibleResult = new Matrix[ARRAY_INITIAL_SIZE];
  private static int noOfPossibleResult = 0;
  private static int rows;
  private static int columns;
  // min for each ingredient pre slice
  private static int min;
  // max is the max number of cells per slice
  private static int max;
  private static String factorisedNumber = "";
  private static String[] factorisedNumberArray;
  private static int noOfSlice = 0;
  private static String result = "";

  public static void main(String args[]) throws Exception
  {
    pizzaScanner = new Scanner(new File(args[0]));
    // Collect basic requirements
    String[] requirementLine = pizzaScanner.nextLine().split("\\s+");
    rows = Integer.parseInt(requirementLine[0]);
    columns = Integer.parseInt(requirementLine[1]);
    min = Integer.parseInt(requirementLine[2]);
    max = Integer.parseInt(requirementLine[3]);
    // Convert the pizza into a form that the program can understand
    pizza = new char[rows][columns];
    available = new boolean[rows][columns];

    // for a better color, TODO: remember to delete
    for (int rowIndex = 0; rowIndex < rows; rowIndex++)
    {
      String currentRow = pizzaScanner.nextLine();
      for (int columnIndex = 0; columnIndex < columns; columnIndex++)
      {
        pizza[rowIndex][columnIndex] = currentRow.charAt(columnIndex);
        available[rowIndex][columnIndex] = true;
      } // for
    } // for

    // Factoring each number from max to min
    for (int matrixArea = max; matrixArea >= min * 2; matrixArea--)
    {
      factoring(matrixArea);
    } // for, to factoring each number
    factorisedNumberArray = factorisedNumber.split("\t");

    // Now, we creat some matrix, first we calculate the start point and end
    // point of each matrix. Start from the largest matrix (greedy algorithem)

    for (int index = 0; index < factorisedNumberArray.length; index++)
    {
      for (int startPointRow = 0; startPointRow < rows; startPointRow++)
      {
        for (int startPointColumn = 0; startPointColumn < columns; startPointColumn++)
        {
          int noOfT = 0;
          int noOfM = 0;
          int endPointRow = startPointRow
                     + (factorisedNumberArray[index].charAt(0) - '0' - 1);
          int endPointColumn = startPointColumn
                     + (factorisedNumberArray[index].charAt(1) - '0' - 1);
          for (int pointRow = startPointRow;pointRow <= endPointRow; pointRow++)
            for (int pointColumn = startPointColumn;
                                    pointColumn <= endPointColumn; pointColumn++)
            {
              if (endPointRow < rows && endPointColumn < columns)
              {
                if (pizza[pointRow][pointColumn] == 'T')
                  noOfT++;
                if (pizza[pointRow][pointColumn] == 'M')
                  noOfM++;
              } // if the matrix exist (fit in the pizza)
            } // for, determing if the matrix matches the requirement

            if (noOfM >= min && noOfT >= min && noOfT + noOfM <= max)
            {
              if (noOfPossibleResult == possibleResult.length)
              {
                Matrix[] bigger =
                        new Matrix[possibleResult.length * ARRAY_RESIZE_FACTOR];
                for (int i = 0; i < noOfPossibleResult; i++)
                  bigger[i] = possibleResult[i];
                possibleResult = bigger;
              } // boring array expansion
              possibleResult[noOfPossibleResult] = new Matrix(startPointRow,
                                 startPointColumn, endPointRow, endPointColumn,
                                 noOfT, noOfM);
              noOfPossibleResult++;
            } // if, the matrix matches requirement, record it
          } // inner for
        } // for each start point
    } // for, create all posible matrix and determing whether to record

    // Now, we put those slice into one pizza (may not be the whole pizza)
    // TODO: This is where the code need improvement, the current greedy
    // algorithem may not produce the best solusion
    for (int i = 0; i < noOfPossibleResult; i++)
    {
      if (thisMatrixOk(possibleResult[i]))
      {
        noOfSlice++;
        setFalseForThisMatrix(possibleResult[i]);
        result += possibleResult[i].getStartPoint() + " "
                  + possibleResult[i].getEndPoint() + "\n";
      } // if the matrix space is not already occupied
    } // for, for every possible result

    // report the final result
    System.out.println(noOfSlice);
    System.out.println(result);
  } // main method

  public static void setFalseForThisMatrix(Matrix matrix)
  {
    for (int r = matrix.getStartPoint().getX();
         r <= matrix.getEndPoint().getX(); r++)
      for (int c = matrix.getStartPoint().getY();
           c <= matrix.getEndPoint().getY(); c++)
        available[r][c] = false;
  } // method setFalseForThisMatrix

  private static boolean thisMatrixOk(Matrix matrix)
  {
    boolean okSoFar = true;
    for (int r = matrix.getStartPoint().getX();
         r <= matrix.getEndPoint().getX(); r++)
      for (int c = matrix.getStartPoint().getY();
           c <= matrix.getEndPoint().getY(); c++)
      {
        if (!available[r][c])
          okSoFar = false;
      } // for, to check each cell
    return okSoFar;
  } // method thisMatrixOk

  private static void factoring(int numberToFactoring)
  {
    for (int index = numberToFactoring; index >= 1; index--)
    {
      if (numberToFactoring % index == 0 && index <= rows && index <= columns
          && (numberToFactoring / index) <= rows
          && (numberToFactoring / index) <= columns)
      {
        factorisedNumber += numberToFactoring / index;
        factorisedNumber += index;
        factorisedNumber += "\t";
      } // if
    } // for
  } // method factoring
} // class PizzaSolver
