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

    public void printSolutionBoard(char[][] boardSolution) {
        if (boardSolution == null) {
            System.out.println("Solution: No solution");
        } else {
            System.out.println("Solution:");
            for (int i = 0; i < boardSolution.length; i++) {
                for (int j = 0; j < boardSolution[i].length; j++) {
                    System.out.print(boardSolution[i][j] + " ");
                }
                System.out.println();
            }
        }
    }

    public void printDetails() {
        printSolutionBoard(filledBoardSolution);
        System.out.println("");
        System.out.printf("Execution Time: %d ms%n", executionTime);
        System.out.println("Number of cases checked: " + casesExplored);
    }
}