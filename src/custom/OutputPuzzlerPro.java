package custom;
import java.util.*;


public class OutputPuzzlerPro {
    public char[][] filledBoardSolution;
    public int executionTime;
    public int casesExplored;

    public OutputPuzzlerPro(char[][] filledBoardSolution, int executionTime, int casesExplored) {
        this.filledBoardSolution = filledBoardSolution;
        this.executionTime = executionTime;
        this.casesExplored = casesExplored;
    }
}