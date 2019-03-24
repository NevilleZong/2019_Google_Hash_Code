/*
This is the solver programe for 2019 Google Hash Code online qulification. The
problem is a photo question. I didn't finish my idea during the required time,
but I really want to finish this. So, here it is.
*/
import java.util.Scanner;
import java.io.File;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.io.IOException;

public class Solver
{
  private static int noOfPhotos;
  private static Photo[] photos;
  private static Scanner inputScanner;
  private static int noOfResults = 0;
  private static String results = "";
  private static int score = 0;
  private static int temp;

  // The main method.
  public static void main(String[] argv)
  {
    try { inputScanner = new Scanner(new File(argv[0])); }
    catch(Exception e) { System.err.println(e + "\n" + e.getCause() + "\n"); }
    noOfPhotos = Integer.parseInt(inputScanner.nextLine());
    photos = new Photo[noOfPhotos];

    // Read the whole input file.
    readInputAndSortInDescendingOrder();
    System.out.println("File read");

    // Merge two vertical photos.
    mergeVerticalPhotos();
    System.out.println("All vertical photos have been merged");

    // Now, we have many null pointers in photos array, and merged vertical
    // photos have more tags, our initial order is wrong now, so we need
    // re-order the whole array and clear all null pointers.
    clearNullAndResortInDescendingOrder();
    System.out.println("Null cleared");
    temp = photos.length;
    // Most interesting part, calculate the result. I don't have much idea about
    // how to generate a good result, right now, this naive method is the only
    // way that I can come up with.

    // TODO: Optimize this stupid solution.
    results += "0\n";
    calculateResults(0);

    // All job done, report the final result.
    reportResults();

    for (Photo photo : photos)
      System.out.println(photo);
    System.out.println("Score: " + score);
  } // main method

  // A recusion method.
  private static void calculateResults(int startValue)
  {
    // Base case, there is no more unsued photos.
    // Base case is when if is false, do not need to write.
    if (thereIsStillAvailablePhotos(startValue))
    {
      int nextSlide = findBestSolutionWithStartValue(startValue);
      System.out.print("Photos left:" + (--temp) + "\r");

      // A photo could only be used once, so destroy used photo.
      photos[startValue] = null;
      noOfResults++;
      if (photos[nextSlide] != null)
      {
        results += photos[nextSlide].getNthPhoto() + "\n";
        calculateResults(nextSlide);
      } // if, next slide exist.
    } // if, true only when there is no other unsued photos.
  } // method calculateResults

  private static int findBestSolutionWithStartValue(int startValue)
  {
    int bestPairIndex = 0;
    int bestResult = 0;
    for (int i = 0; i < photos.length; i++)
    {
      if (photos[i] != null && i != startValue)
      {
        int currentResult = findInterset(photos[startValue], photos[i]);
        if (bestResult < currentResult)
        {
          bestResult = currentResult;
          bestPairIndex = i;
        } // a better result found
      } // if, a photo cannot pair with null or pair with itself.
    } // for, find the best pair.
    score += bestResult;
    return bestPairIndex;
  } // method findBestSolutionWithStartValue

  private static int findInterset(Photo photo1, Photo photo2)
  {
    int noOfTagsInPhoto1 = photo1.getNoOfTags();
    int noOfTagsInPhoto2 = photo2.getNoOfTags();
    int commonTags = 0;
    if (noOfTagsInPhoto1 >= noOfTagsInPhoto2)
    {
      for (int i = 0; i < noOfTagsInPhoto1; i++)
        if (photo2.getTags().contains(photo1.getTags().get(i)))
          commonTags++;
    } // if
    else
    {
      for (int i = 0; i < noOfTagsInPhoto2; i++)
        if (photo1.getTags().contains(photo2.getTags().get(i)))
          commonTags++;
    } // else

    return findMin(noOfTagsInPhoto1 - commonTags, commonTags,
      noOfTagsInPhoto2 - commonTags);
  } // method findInterset

  private static int findMin(int a, int b, int c)
  {
    if (a <= b)
      return a <= c ? a : c;
    else
      return b <= c ? b : c;
  } // method findMin

  private static boolean thereIsStillAvailablePhotos(int thisPhoto)
  {
    boolean thereIsStillAvailablePhotos = false;
    for (int i =0; i < photos.length; i++)
      if (photos[i] != null && i != thisPhoto)
      {
        thereIsStillAvailablePhotos = true;
        break;
      } // if, found a not null photo (available photo)
    return thereIsStillAvailablePhotos;
  } // method thereIsStillAvailablePhotos

  private static void reportResults()
  {
    System.out.println(noOfResults);
    System.out.println(results);
  } // method reportResults

  private static void clearNullAndResortInDescendingOrder()
  {
    int noOfNotNullElement = 0;

    // First, calculate the number of all not null element.
    for (Photo photo : photos)
      if (photo != null)
        noOfNotNullElement++;

    // Now, copy all non-null elements to a new array.
    Photo[] temp = new Photo[noOfNotNullElement];
    int i = 0;
    for (Photo photo : photos)
      if (photo != null)
        temp[i++] = photo;

    // Now, sort the new array.
    Arrays.sort(temp);
    photos = temp;
  } // method clearNullAndReorder

  private static void readInputAndSortInDescendingOrder()
  {
    int indexForReadFile = 0;
    while (inputScanner.hasNextLine())
    {
      String[] temp = inputScanner.nextLine().split(" ", 3);
      photos[indexForReadFile++] = new Photo(temp[0], temp[2]);
    } // while, read the whole input file.

    // And sort the array in descending order.
    Arrays.sort(photos);
  } // method readInputAndSortInDescendingOrder

  private static void mergeVerticalPhotos()
  {
    for (int firstPhotoIndex = 0; firstPhotoIndex < noOfPhotos;
      firstPhotoIndex++)
    {
      if (photos[firstPhotoIndex] != null
        && photos[firstPhotoIndex].getIsVertical())
        for (int secondPhotoIndex = noOfPhotos - 1;
          secondPhotoIndex > firstPhotoIndex; secondPhotoIndex--)
        {
          if (photos[secondPhotoIndex] != null
            && photos[secondPhotoIndex].getIsVertical())
          {
            photos[firstPhotoIndex].merge(photos[secondPhotoIndex]);
            photos[secondPhotoIndex] = null;
            break;
          } // if, the two Photos are both vertical.
        } // for, find the neighbour vertical photo
    } // for, merge two vertical photos.
  } // method mergeVerticalPhotos

} // class Solver
