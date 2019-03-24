/*
This is the Photo class. A Photo has several tags, and the number of tags is
stored in a Photo object too.

This class also include a static method, merge(), which will take two photos and
merge them into one, and destroy the second photo.
*/
import java.util.ArrayList;
public class Photo implements Comparable<Photo>
{
  private int noOfTags;
  private boolean isVertical;
  private ArrayList<String> tags;
  private boolean hasBeenUsed;

  // Constructor
  public Photo(String requiredIsVertical, String requiredTags)
  {
    isVertical = requiredIsVertical.equals("V") ? true : false;
    tags = new ArrayList<String>();
    // Add all tags into the tags ArrayList.
    String[] temp = requiredTags.split(" ");
    for (int i = 0; i <temp.length; i++)
      tags.add(temp[i]);
    noOfTags = tags.size();
    hasBeenUsed = false;
  } // Constructor

  // Those tag that the other has but this one doesn't has will be added to this
  // Photo.
  public void merge(Photo other)
  {
    for (int i = 0; i < other.tags.size(); i++)
    {
      if (!tags.contains(other.tags.get(i)))
        tags.add(other.tags.get(i));
    } // for, to check each tag
    noOfTags = tags.size();
  } // method merge

  // Two accessor methods and one set method
  public boolean getHasBeenUsed()
  {
    return hasBeenUsed;
  } // method getHasBeenUsed

  public boolean getIsVertical()
  {
    return isVertical;
  } // method getIsVertical

  public void setHasBeenUsed(boolean requiredHasBeenUsed)
  {
    hasBeenUsed = requiredHasBeenUsed;
  } // method setHasBeenUsed

  @Override
  public int compareTo(Photo other)
  {
    return other.noOfTags - noOfTags;
  } // method compareTo

  @Override
  public boolean equals(Object other)
  {
    return other instanceof Photo ? compareTo((Photo)other) == 0 :
      super.equals(other);
  } // method equals

  @Override
  public String toString()
  {
    return (isVertical ? "V" : "H") + " with " + noOfTags + " tags";
  } // method toString
} // class Photo
