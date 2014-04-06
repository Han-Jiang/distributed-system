package han.nds.test;
import java.util.*;

import java.util.concurrent.*;

public class UseNavigableMap

{

 public static void main (String[] arg)

 {

   System.out.println("Example of Navigable Map ");

   NavigableMap navmap=new ConcurrentSkipListMap();

    navmap.put(1, "Sunday");  

    navmap.put(2, "Monday");

    navmap.put(3, "Tuesday");

    navmap.put(4, "Wednesday");

    navmap.put(5, "Thursday");

    navmap.put(6, "Friday");

    navmap.put(7, "Saturday");

 System.out.println("Data in the navigable map: " + navmap.descendingMap()+"\n");

//Retrieving first data

    System.out.println("First data: " + navmap.firstEntry()+"\n");

    //Retrieving last data

    System.out.print("Last data: " + navmap.lastEntry()+"\n");

    //Retrieving the nearest less than or equal to the given key

    System.out.println("Nearest less than or equal to the given key: " + navmap.floorEntry(5)+"\n");

    //Retrieving the greatest key strictly less than the given key

    System.out.println("Retrieving the greatest key strictly less than the given key: " + navmap.lowerEntry(3));

    //Retrieving a key-value associated with the least key strictly greater than the given key

    System.out.println("Retriving data from navigable map greater than the given key:    " + navmap.higherEntry(5)+"\n");

    //Removing first

    System.out.println("Removing First: " + navmap.pollFirstEntry());

    //Removing last

    System.out.println("Removing Last: " + navmap.pollLastEntry()+"\n");

    //Displaying all data

    System.out.println("Now data: " + navmap.descendingMap());

  }

}
