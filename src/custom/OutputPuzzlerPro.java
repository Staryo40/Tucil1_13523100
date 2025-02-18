package custom;
import java.util.*;


public class OutputPuzzlerPro {
    public char[][] filledBoardSolution;
    public long executionTime;
    public int casesExplored;

    public OutputPuzzlerPro(char[][] filledBoardSolution, long executionTime, int casesExplored) {
        this.filledBoardSolution = filledBoardSolution;
        this.executionTime = executionTime;
        this.casesExplored = casesExplored;
    }
}