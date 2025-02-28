import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
  
  public static void main(String[] args) {

    ArrayList<ArrayList<Integer>> reports = new ArrayList<>();
    try (Stream<String> lines = Files.lines(Paths.get("../data.txt"))) {
      reports = lines
        .map(line -> Arrays.stream(line.split(" "))
          .map(Integer::valueOf)
          .collect(Collectors.toCollection(ArrayList::new)))
        .collect(Collectors.toCollection(ArrayList::new));
    } catch(IOException e) {
      System.err.println("Error reading file");
    }

    Integer safeReports = 0;

    //Enum to set increasing / decreasing state of report
    enum State {
      NILL,
      INCREASING,
      DECREASING;
    }

    /*
     * Loop through all reports and check following conditions.
     * if current value and next value is the same, check if:
     *   if probleam dampener has been triggered, report is unsafe,
     *   else remove current value and continue
     * 
     * if current value is lesser then next value check following:
     *   if state of report sequence is decreasing, check if:
     *     if problem dampener has been triggered, report is unsafe,
     *     else remove current value and continue
     *   if the diffrence in value is greater then three, report is unsafe.
     *
     * if current value is greater then next value check following:
     *   if state of report sequence is increasing, check following:
     *     if problem dampener has been triggered, report is unsafe,
     *     else remove current value and continue.
     *   if difernece in value is greater then three, report is unsafe.
     * 
     * if all checks passed, report is considered safe
     */
    for(int i = 0; i < reports.size(); i++) {
      boolean isSafe = true;
      State state = State.NILL;
      ArrayList<Integer> values = reports.get(i);
      boolean problemDampenerTriggered = false;

      for(int j = 0; j < values.size() - 1; j++){
        Integer currValue = values.get(j);
        Integer nextValue = values.get(j + 1);

        if(currValue.equals(nextValue)) {
          if(!problemDampenerTriggered) {
            problemDampenerTriggered = true;
            values.remove(j);
            continue;
          } else {
            isSafe = false;
            break;
          }
        }

        if(currValue < nextValue) {
          if(state == State.DECREASING) {
            if(!problemDampenerTriggered) {
              problemDampenerTriggered = true;
              values.remove(j);
              continue;
            } else {
              isSafe = false;
              break;
            }
          }

          if(nextValue - currValue > 3) {
            isSafe = false;
            break;
          }
          
          state = State.INCREASING;
        }

        if(currValue > nextValue) {
          if(state == State.INCREASING) {
            if(!problemDampenerTriggered) {
              problemDampenerTriggered = true;
              values.remove(j);
              continue;
            } else {
              isSafe = false;
              break;
            }
          }

          if(currValue - nextValue > 3) {
            isSafe = false;
            break;
          }

          state = State.DECREASING;
        }

      }
      if(isSafe) safeReports++;
    }
  
    System.out.println(safeReports);

  }
}
