import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
  
  public static void main(String[] args){
    
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
      System.err.println("Could not read columns from file");
    }

    //Nested foreach loop to compare all values in list2
    //with all values in list 1 
    Integer similarityScore = 0;
    for(Integer leftVal : list1) {
      Integer sameValueOccurrance = 0;
      for(Integer rightVal : list2) {
        if(leftVal.equals(rightVal)) {
          sameValueOccurrance++; 
        } 
      }
      //Multiply occurrances with current value in list1
      similarityScore += leftVal * sameValueOccurrance;
    }
    System.out.println(similarityScore);

  }
}
