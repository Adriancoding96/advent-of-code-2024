import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
  public static void main(String[] args) throws IOException {
  
    // Read order data from file
    ArrayList<ArrayList<Integer>> orderData = new ArrayList<>();

    try (Stream<String> order = Files.lines(Paths.get("../order_data.txt"))) {
      orderData = order 
        .map(line -> Arrays.stream(line.split("\\|"))
          .map(Integer::valueOf)
          .collect(Collectors.toCollection(ArrayList::new)))
        .collect(Collectors.toCollection(ArrayList::new));
    } catch(IOException e) {
      System.err.println("Error reading file");
    }
    
    // Read data input from file
    ArrayList<String> lines = new ArrayList<>();
    BufferedReader reader = new BufferedReader(new FileReader("../input_data.txt"));
    String line = reader.readLine();
    while(line != null) {
      lines.add(line);
      line = reader.readLine();
    }
    reader.close();

    // Convert string to array of integers
    ArrayList<ArrayList<Integer>> inputData = new ArrayList<>();
    for(String str : lines) {
      ArrayList<Integer> values = new ArrayList<>();
      String[] strValues = str.split(",");
      for(String strVal : strValues) values.add(Integer.parseInt(strVal));
      inputData.add(values);
    }

    // Create a map where the key is the number that needs to come first,
    // and the value is a list of values that this applies to
    HashMap<Integer, ArrayList<Integer>> ruleSets = new HashMap<>();
    for(ArrayList<Integer> ruleSet : orderData) {
      if(ruleSets.containsKey(ruleSet.get(0))) {
        ruleSets.get(ruleSet.get(0)).add(ruleSet.get(1));
        continue;
      } 
      ArrayList<Integer> values = new ArrayList<>();
      values.add(ruleSet.get(1));
      ruleSets.put(ruleSet.get(0), values);
    }

    ArrayList<Integer> middleValues = new ArrayList<>();
    // Loop through line if adhering to rules get middle value and put in to list
    for(int i = 0, n = inputData.size(); i < n; i++) {
      boolean rulesFollowed = true;
      for(int j = 0, k = inputData.get(i).size(); j < k; j++) {
          if(!ruleSets.containsKey(inputData.get(i).get(j))) continue; // Continues because if key does not exists no order rules apply to the value
          ArrayList<Integer> orderRules = ruleSets.get(inputData.get(i).get(j));
        // Go back through the list and check if values in hasmap exists before current value
        // if it does, flag it as not valid line, if line never flagged get middle value
        for(int x = j - 1; x >= 0; x--) {

          if(orderRules.contains(inputData.get(i).get(x))) {
            rulesFollowed = false;
            break;
          }
        }
        if(!rulesFollowed) break;
      }
      if(rulesFollowed) middleValues.add(inputData.get(i).get(inputData.get(i).size() / 2));
    }
    Integer result = 0;
    for(Integer value : middleValues) {
      result += value;
    }

    System.out.println(result);

  }
}
