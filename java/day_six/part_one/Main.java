import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
 
  static class Coordinates {
      int x;
      int y;

      Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
      }
    }

  static ArrayList<Coordinates> visitedTiles = new ArrayList<>();
  
  public static void main(String[] args) throws IOException {
    
    ArrayList<String> lines = new ArrayList<>();
    BufferedReader reader = new BufferedReader(new FileReader("../data.txt"));
    String line = reader.readLine();
    while(line != null) {
      lines.add(line);
      line = reader.readLine();
    }
    reader.close();

    char[][] map = new char[lines.size()][lines.get(0).length()]; // Assuming all lines are the same size
    for(int i = 0, n = lines.size(); i < n; i++) {
      for(int j = 0, k = lines.get(i).length(); j < k; j++) {
        map[i][j] = lines.get(i).charAt(j);
      }
    }
   

    int x = 0;
    int y = 0;
    for(int i = 0, n = map.length; i < n; i++) {
      for(int j = 0, k = map[i].length; j < k; j++) {
        if(map[i][j] == '^') {
          y = i;
          x = j;
          visitedTiles.add(new Coordinates(x, y));
        }
      }
    }
    
    enum Direction {
      UP,
      DOWN,
      RIGHT,
      LEFT;
    }

    

    Direction direction = Direction.UP;
    int tilesVisited = 1;

    while(true) {
      // Check if on border of map and what direction guard is facing
      if(direction == Direction.UP && y == 0) break;
      if(direction == Direction.DOWN && y == map.length - 1) break;
      if(direction == Direction.RIGHT && x == map[y].length - 1) break;
      if(direction == Direction.LEFT && x == 0) break;

      // Check if path is free upwards, else change direction to RIGHT
      if(direction == Direction.UP) {
        if(map[y - 1][x] == '#') {
          direction = Direction.RIGHT;
          continue;
        }
        y--;
        if(!checkIfTileVisited(x, y)) {
          tilesVisited++;
          visitedTiles.add(new Coordinates(x, y));
        }
        continue;
        
      }

      // Check if path is free to the right, else change direction to DOWN
      if(direction == Direction.RIGHT) {
        if(map[y][x + 1] == '#') {
          direction = Direction.DOWN;
          continue;
        }
        x++;
        if(!checkIfTileVisited(x, y)) {
          tilesVisited++;
          visitedTiles.add(new Coordinates(x, y));
        }
        continue;
      }

      // Check if path is free to the left, else change direction to LEFT
      if(direction == Direction.DOWN) {
        if(map[y + 1][x] == '#') {
          direction = Direction.LEFT;
          continue;
        }
        y++;
        if(!checkIfTileVisited(x, y)) {
          tilesVisited++;
          visitedTiles.add(new Coordinates(x, y));
        }
        continue;
      } 
      
      // Check if path is free to the left, else change direction to UP 
      if(direction == Direction.LEFT) {
        if(map[y][x - 1] == '#') {
          direction = Direction.UP;
          continue;
        }
        x--;
        if(!checkIfTileVisited(x, y)){
          tilesVisited++;
          visitedTiles.add(new Coordinates(x, y));
        }
        continue;
      }
    }
  
    System.out.println(tilesVisited);

  }

  private static boolean checkIfTileVisited(int x, int y) {
    if(visitedTiles.size() == 1) System.out.println("Visited tiles in always the size of 1");
    if(visitedTiles.isEmpty()) return false;
    for(Coordinates coords : visitedTiles) {
      if(coords.x == x && coords.y == y) return true;
    }
    return false;
  }
}
