import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
 
  public static void main(String[] args) {

    ArrayList<Integer> list1 = new ArrayList<>();
    ArrayList<Integer> list2 = new ArrayList<>();

    //Read data from file
    String fileName = "../data.txt";
    try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

      String line;

      while((line = reader.readLine()) != null) {
        line = line.trim();

        if(line.isEmpty()) continue;

        String[] columns = line.split("\\s{3}");
 
        Integer column1 = Integer.parseInt(columns[0]);
        Integer column2 = Integer.parseInt(columns[1]);

        list1.add(column1);
        list2.add(column2);
      }
    }catch(IOException e) {
      System.err.println("Could not read columns from file, error: " + e.getMessage());
    }

    //Sort arrays containing columns
    Collections.sort(list1);
    Collections.sort(list2);

    ArrayList<Integer> distances = new ArrayList<>();

    int n = list1.size();

    //Add distances between column values
    for(int i = 0; i < n; i++) {
      if(list1.get(i) == list2.get(i)) continue;
      if(list1.get(i) > list2.get(i)) distances.add(list1.get(i) - list2.get(i));
      if(list1.get(i) < list2.get(i)) distances.add(list2.get(i) - list1.get(i));
    }

    //Add distances together
    Integer result = 0; 
    for(Integer distance : distances) result += distance;
    
    System.out.println(result);
  }
}
