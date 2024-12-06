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

    ArrayList<ArrayList<Integer>> incorrectUpdates = new ArrayList<>();
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
            incorrectUpdates.add(inputData.get(i));
            break;
          }
        }
        if(!rulesFollowed) break;
      }
    }

    for(ArrayList<Integer> page : incorrectUpdates) {
      sortPage(page, ruleSets);
    } 
    
    Integer result = 0;
    for(ArrayList<Integer> page : incorrectUpdates) {
      result += page.get(page.size() / 2);
    }

    System.out.println(result);
  }


  private static void sortPage(ArrayList<Integer> page, HashMap<Integer, ArrayList<Integer>> valueSequences) {
    if(isPageSorted(page, valueSequences)) return;
    while(!isPageSorted(page, valueSequences)) {
      ArrayList<Integer> pageCopy = new ArrayList<>(page); // To avoid concurrent modification exception in foreach loop
      for(Integer value : pageCopy) {
        ArrayList<Integer> valueSequence = valueSequences.get(value);
        shiftPage(value, page, valueSequence);
      } 
    }
  }


  private static void shiftPage(Integer value, ArrayList<Integer> page, ArrayList<Integer> valueSequence) {
    // Value in is not allowed to have any of the values inside of value Sequence infront of it 
    if(page.indexOf(value) == 0) return; // Assumin there is no duplicate values on a page
    int indexOfValue = page.indexOf(value);
    ArrayList<Integer> shiftedPage = new ArrayList<>();
    
    ArrayList<Integer> leftValues = new ArrayList<>();
    // Get values to the left of value
    for(int i = 0; i < indexOfValue; i++) {
      leftValues.add(page.get(i));
    }

    ArrayList<Integer> rightValues = new ArrayList<>();
    // Get value right of value
    for(int i = indexOfValue + 1; i < page.size(); i++) {
      rightValues.add(page.get(i));
    }

    ArrayList<Integer> valuesToBeRemoved = new ArrayList<>();
    // Loop through left values if not allowed add to right values
    for(Integer leftValue : leftValues) {
      if(valueSequence.contains(leftValue)) {
        valuesToBeRemoved.add(leftValue);
        rightValues.add(leftValue);
      }
    }
    leftValues.removeAll(valuesToBeRemoved);

    // Add left side of page to shifted page
    shiftedPage.addAll(leftValues);
    shiftedPage.add(value);
    shiftedPage.addAll(rightValues);
    page.clear();
    page.addAll(shiftedPage);
  }

  private static boolean isPageSorted(ArrayList<Integer> page, HashMap<Integer, ArrayList<Integer>> valueSequences) {
    // No value in page is allowed to have any of their respective values in their value sequence infront of them
    for(Integer value : page) {
      ArrayList<Integer> valueSequence = valueSequences.get(value);
      if(!isValueSequenceCorrect(value, page, valueSequence)) return false;
    }
    return true; 
  }

  private static boolean isValueSequenceCorrect(Integer value, ArrayList<Integer> page, ArrayList<Integer> valueSequence) {
    // Put values left of value in page in to separate array, check if value sequence contains any of those values
    int indexOfValue = page.indexOf(value);
    ArrayList<Integer> leftValues = new ArrayList<>();
    for(int i = 0; i < indexOfValue; i++) leftValues.add(page.get(i));
    for(Integer leftVal : leftValues) {
      if(valueSequence.contains(leftVal)) return false;
    }
    return true;
  }
}
