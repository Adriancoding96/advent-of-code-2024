import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
  
  public static void main(String[] args) {
 
    // Read content of file to a String
    String fileName = "../data.txt";
    String corruptedData = "";
    try {
      Path filePath = Paths.get(fileName);
      corruptedData = Files.readString(filePath);
    } catch(IOException e) {
      e.printStackTrace();
    }

    /*
     * Extracts sub strings matching mul(,) | do() | don't() using regex pattern
     * and java.utils pattern matching methods.
     */
    String regex = "mul\\(\\d+,\\d+\\)|don't\\(\\)|do\\(\\)";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(corruptedData);
    
    ArrayList<String> subStrings = new ArrayList<>();
    while(matcher.find()) subStrings.add(matcher.group());

    for(String subStr : subStrings) {
      System.out.println(subStr);
    }


    boolean multiply = true;

    /*
     * Iterates over all sub strings, check if substring is
     * do or dont, if dont set multiply to false, and jumps
     * to next iteration until a do string is found.
     *
     * if multiply is true amd mul() string is found it trims away mul( and ),
     * parses the numbers left over to integers, adds the
     * product of the multiplication of the integers in to list
     */
    ArrayList<Integer> productsOfMultiplication = new ArrayList<>();
    for(String subStr : subStrings) {
      if(subStr.equals("don't()")) multiply = false;
      if(subStr.equals("do()")) multiply = true;
      if(!multiply) continue;

      if(subStr.contains("mul")) {
          subStr = subStr.replace("mul(", "");
          subStr = subStr.replace(")", "");
          
          String[] strFactors = subStr.split(",");
          Integer multiplicand = Integer.parseInt(strFactors[0]);
          Integer multiplier = Integer.parseInt(strFactors[1]);
          productsOfMultiplication.add(multiplicand * multiplier);
      }
    }
    
    /*
     * Adds products of multiplication together
     */
    
    Integer result = 0;
    for(Integer product : productsOfMultiplication) {
      result += product;
    }

    System.out.println(result);
  }
}
