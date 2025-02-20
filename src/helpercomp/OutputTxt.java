package helpercomp;
import java.io.*;
import custom.*;

public class OutputTxt {
    public static void output (InputPuzzlerPro input, OutputPuzzlerPro output, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            // Write input board to file
            char[][] inputBoard = input.createEmptyMatrix();;
            if (input.mode.equals("CUSTOM")) {
                if (input.customBoard == null){
                    inputBoard = null;
                } else {
                    inputBoard = input.matrixTurnXtoUnd(input.customBoard);
                }
            }

            if (inputBoard == null) {
                writer.write("Input Board: null");
                writer.newLine();
            } else {
                writer.write("Input Board:");
                writer.newLine();
                for (int i = 0; i < inputBoard.length; i++) {
                    for (int j = 0; j < inputBoard[i].length; j++) {
                        if (inputBoard[i][j] != '_') {
                            writer.write("  ");
                        } else {
                            writer.write(inputBoard[i][j] + " ");
                        }
                    }
                    writer.newLine();
                }
            }
            writer.newLine();

            // Write input pieces
            if (input.piecesList.isEmpty()) {
                writer.write("Pieces List: empty");
                writer.newLine();
            } else {
                writer.write("Pieces List:");
                writer.newLine();
                for (int index = 0; index < input.piecesList.size(); index++) {
                    writer.write("Piece " + (index + 1) + ":");
                    writer.newLine();
                    if (input.piecesList.get(index) == null) {
                        writer.write("null");
                    } else {
                        for (int i = 0; i < input.piecesList.get(index).length; i++) {
                            for (int j = 0; j < input.piecesList.get(index)[i].length; j++) {
                                writer.write(input.piecesList.get(index)[i][j] + " ");
                            }
                            writer.newLine();
                        }
                    }
                }
            }
            writer.newLine();

            // Write solution board to file
            if (output.filledBoardSolution == null) {
                writer.write("Solution: No solution");
                writer.newLine();
            } else {
                writer.write("Solution:\n");
                for (int i = 0; i < output.filledBoardSolution.length; i++) {
                    for (int j = 0; j < output.filledBoardSolution[i].length; j++) {
                        char currentChar = output.filledBoardSolution[i][j];

                        if (!Character.isUpperCase(currentChar)) {
                            writer.write("  ");
                        } else {
                            writer.write(currentChar + " ");
                        }
                    }
                    writer.newLine();
                }
            }
            
            writer.newLine();

            // Write execution time and number of cases at the end
            writer.write("Execution Time: " + output.executionTime + " ms\n");
            writer.write("Number of cases checked: " + output.casesExplored + "\n");

            System.out.println("Solution saved to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error occurred while saving the solution.");
        }
    }
}