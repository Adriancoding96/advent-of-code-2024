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
        
        Character ch = linesOfChars.get(i).get(j);
        // Check if character is A, else continue
        if(!ch.equals('A')) continue;

        // Makw sure checking for pattern does not cause out of bounds exception
        if((i - 1) < 0 || (i + 1) >= yAxis || (j - 1) < 0 || (j + 1) >= xAxis) continue;
        // check top right and bottom left
        Character topRightChar = linesOfChars.get(i - 1).get(j + 1);
        if(!topRightChar.equals('M') && !topRightChar.equals('S')) continue;

        Character bottomLeftChar = linesOfChars.get(i + 1).get(j - 1);
        if(topRightChar.equals('M')) {
          if(!bottomLeftChar.equals('S')) continue;
        }
        if(topRightChar.equals('S')) {
          if(!bottomLeftChar.equals('M')) continue;
        }

        // Check top left and bottom right
        Character topLeftChar = linesOfChars.get(i - 1).get(j - 1);
        if(!topLeftChar.equals('M') && !topLeftChar.equals('S')) continue;

        Character bottomRightChar = linesOfChars.get(i + 1).get(j + 1);
        if(topLeftChar.equals('M')) {
          if(!bottomRightChar.equals('S')) continue;
        }
        if(topLeftChar.equals('S')) {
          if(!bottomRightChar.equals('M')) continue;
        }

        xmasCounter++;

      }
    }
    System.out.println(xmasCounter);

  }
}
