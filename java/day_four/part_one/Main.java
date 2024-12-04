import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
  
  public static void main(String[] args) throws IOException {

    // Read in file as a array of strings
    ArrayList<String> lines = new ArrayList<>();
    BufferedReader reader = new BufferedReader(new FileReader("../data.txt"));
    String line = reader.readLine();
    while(line != null) {
      lines.add(line);
      line = reader.readLine();
    }

    reader.close();
    
    // Convert each string in to a char array
    ArrayList<ArrayList<Character>> linesOfChars = new ArrayList<>();
    for(String str : lines) {
      ArrayList<Character> chars = new ArrayList<>();
      for(char c : str.toCharArray()) {
        chars.add(c);
      }
      linesOfChars.add(chars);
    }

    // Asuming every array is the same size
    int xAxis = linesOfChars.get(0).size();
    int yAxis = linesOfChars.size();
    int xmasCounter = 0;

    // Loop through char arrays
    for(int i = 0; i < yAxis; i++) {
      // Loop through each char of array
      for(int j = 0; j < xAxis; j++) {
        
        // Check if current char is x, if not continue
        if(!linesOfChars.get(i).get(j).equals('X')) continue;

        // Check if there are three characters forward of current character
        if((j + 3) < xAxis) {
          char[] chars = new char[]{
            linesOfChars.get(i).get(j),
            linesOfChars.get(i).get(j + 1),
            linesOfChars.get(i).get(j + 2),
            linesOfChars.get(i).get(j + 3),
  
          };
          if(equalsXmas(chars)) xmasCounter++;
        }

        // Check if there are three characters backwards of current character
        if((j - 3) >= 0) {
          char[] chars = new char[]{
            linesOfChars.get(i).get(j),
            linesOfChars.get(i).get(j - 1),
            linesOfChars.get(i).get(j - 2),
            linesOfChars.get(i).get(j - 3),
          };
          if(equalsXmas(chars)) xmasCounter++;
        }

        // Check if there are three characters above current character
        if((i - 3) >= 0) {
          char[] chars = new char[]{
            linesOfChars.get(i).get(j),
            linesOfChars.get(i - 1).get(j),
            linesOfChars.get(i - 2).get(j),
            linesOfChars.get(i - 3).get(j),
          };
          if(equalsXmas(chars)) xmasCounter++;
        }

        // Check if there are three characters downwards of current character
        if((i + 3) < yAxis) {
          char[] chars = new char[]{
            linesOfChars.get(i).get(j),
            linesOfChars.get(i + 1).get(j),
            linesOfChars.get(i + 2).get(j),
            linesOfChars.get(i + 3).get(j),
          };
          if(equalsXmas(chars)) xmasCounter++; 
        }

        // Check diagonal direction to top left
        if((i - 3) >= 0 && (j - 3) >= 0) {
          char[] chars = new char[]{
            linesOfChars.get(i).get(j),
            linesOfChars.get(i - 1).get(j - 1),
            linesOfChars.get(i - 2).get(j - 2),
            linesOfChars.get(i - 3).get(j - 3),
          };
          if(equalsXmas(chars)) xmasCounter++; 
        }
        
        // Check diagonal directon to top right
        if((i - 3) >= 0 && (j + 3) < xAxis) {
          char[] chars = new char[]{
            linesOfChars.get(i).get(j),
            linesOfChars.get(i - 1).get(j + 1),
            linesOfChars.get(i - 2).get(j + 2),
            linesOfChars.get(i - 3).get(j + 3),
          };
          if(equalsXmas(chars)) xmasCounter++; 
        }

        // Check diagonal direction to bottom left
        if((i + 3) < yAxis && (j - 3) >= 0) {
          char[] chars = new char[]{
            linesOfChars.get(i).get(j),
            linesOfChars.get(i + 1).get(j - 1),
            linesOfChars.get(i + 2).get(j - 2),
            linesOfChars.get(i + 3).get(j - 3),
          };
          if(equalsXmas(chars)) xmasCounter++; 
        }

        // Check diagonal direction to bottom right
        if((i + 3) < yAxis && (j + 3) < xAxis) {
          char[] chars = new char[]{
            linesOfChars.get(i).get(j),
            linesOfChars.get(i + 1).get(j + 1),
            linesOfChars.get(i + 2).get(j + 2),
            linesOfChars.get(i + 3).get(j + 3),
          };
          if(equalsXmas(chars)) xmasCounter++; 
        }
      }
    }

    System.out.println(xmasCounter);
  }

  private static boolean equalsXmas(char[] chars) {
    String word = new String(chars);
    if(!word.equals("XMAS")) return false;
    return true;
  }
}
