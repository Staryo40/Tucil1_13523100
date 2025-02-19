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

    public static int getCharIndex(char[] chars, char c) {
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == c) {
                return i; 
            }
        }
        return -1;
    }

    public static final String[] ANSI_COLORS = {"\u001b[38;2;0;0;255m", "\u001b[38;2;0;255;0m", "\u001b[38;2;255;128;0m", "\u001b[38;2;166;66;200m", "\u001b[38;2;101;55;0m",
                                                "\u001b[38;2;128;128;128m", "\u001b[38;2;255;0;0m", "\u001b[38;2;128;0;0m", "\u001b[38;2;143;0;255m", "\u001b[38;2;205;127;50m",
                                                "\u001b[38;2;255;255;204m", "\u001b[38;2;254;220;86m", "\u001b[38;2;0;0;128m", "\u001b[38;2;248;131;121m", "\u001b[38;2;224;176;255m",
                                                "\u001b[38;2;255;204;153m", "\u001b[38;2;255;215;0m", "\u001b[38;2;0;255;255m", "\u001b[38;2;0;127;255m", "\u001b[38;2;0;128;128m",
                                                "\u001b[38;2;112;130;56m", "\u001b[38;2;245;245;220m","\u001b[38;2;54;69;79m", "\u001b[38;2;0;0;255m", "\u001b[38;2;36;122;253m",
                                                "\u001b[38;2;255;209;220m"};

    public void printSolutionBoard(char[][] boardSolution) {
        if (boardSolution == null) {
            System.out.println("Solution: No solution");
        } else {
            char[] chars = new char[26];
            int charCount = 0;
            System.out.println("Solution:");
            for (int i = 0; i < boardSolution.length; i++) {
                for (int j = 0; j < boardSolution[i].length; j++) {
                    char currentChar = boardSolution[i][j];

                    if (!Character.isUpperCase(currentChar)) {
                        System.out.print("  ");
                        continue;
                    }

                    if (getCharIndex(chars, currentChar) == -1) {
                        // Add the character to the chars array
                        chars[charCount] = currentChar;
                        charCount++;
                    }

                    int charIndex = getCharIndex(chars, currentChar);

                    if (charIndex != -1 && charIndex < ANSI_COLORS.length) {
                        System.out.print(ANSI_COLORS[charIndex] + currentChar + " ");
                    } else {
                        // If no color exists for the charIndex, use the default color
                        System.out.print("\033[0m" + currentChar + " ");
                    }
                }
                System.out.println();
            }
            System.out.print("\033[0m");
        }
    }

    public void printDetails() {
        printSolutionBoard(filledBoardSolution);
        System.out.println("");
        System.out.printf("Execution Time: %d ms%n", executionTime);
        System.out.println("Number of cases checked: " + casesExplored);
    }
}