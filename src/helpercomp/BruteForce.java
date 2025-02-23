package helpercomp;
import custom.*;
import java.util.*;

public class BruteForce {
    public static OutputPuzzlerPro getOutput(InputFormat form) {
        char[][] beginningMat;
        if (form.input.mode.equals("DEFAULT")) {
            beginningMat = createEmptyMatrix(form.input.height, form.input.width);
        } else if (form.input.mode.equals("CUSTOM")) {
            beginningMat = form.input.customBoard;
        } else {
            System.out.printf("Error: %s is not a valid case%n", form.input.mode);
            return null;
        }

        long startTime = System.currentTimeMillis();
        OutputPuzzlerPro res = processPuzzle(beginningMat, form.input.piecesList);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        res.executionTime = duration;

        return res;
    }

    public static OutputPuzzlerPro processPuzzle(char[][] currentMatrix, List<char[][]> pieces) {
        if (pieces.isEmpty()) {
            if (matrixIsFull(currentMatrix)) {
                OutputPuzzlerPro res = new OutputPuzzlerPro(currentMatrix, 0, 1);
                // res.printSolutionBoard(currentMatrix);
                return res;
            } else {
                OutputPuzzlerPro res = new OutputPuzzlerPro(null, 0, 1);
                // res.printSolutionBoard(currentMatrix);
                return res;
            }
        }

        char[][] currentPiece = pieces.get(0);
        // printMatrix(currentPiece);
        List<char[][]> currentVariations = getPieceVariations(currentPiece);
        long countCases = 0;

        // Try all piece variation with rotate and flip
        for (int i = 0; i < currentVariations.size(); i++) {
            int currentWidth = currentVariations.get(i)[0].length;
            int currentHeight = currentVariations.get(i).length;

            int height = currentMatrix.length - currentHeight + 1;
            int width = currentMatrix[0].length - currentWidth + 1;

            // if doesn't fit because larger than matrix, don't process because it fails here
            if (height <= 0 || width <= 0) {
                countCases += 1;
                continue;
            }

            // Checking if the current piece position variation fits for every possible location in matrix
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < width; k++) {
                    boolean canFit = true;

                    // determining if currentVariation fits
                    for (int x = 0; x < currentHeight; x++) {
                        for (int y = 0; y < currentWidth; y++) {
                            if (currentVariations.get(i)[x][y] != '_' && currentMatrix[j + x][k + y] != '_') {
                                canFit = false;
                                break;
                            }
                        }
                        if (!canFit) break;
                    }

                    // If it fits, place the variation into currentMatrix and continue to the next piece
                    if (canFit) {
                        char[][] newMatrix = placeVariation(currentMatrix, currentVariations.get(i), j, k);
                        OutputPuzzlerPro res = processPuzzle(newMatrix, listWithoutFirstElement(pieces));
                        if (res.filledBoardSolution != null) {
                            res.casesExplored += countCases;
                            return res;
                        } else {
                            countCases += res.casesExplored;
                        }
                    } else { // count case and continue to the next position
                        countCases += 1;
                    }
                }
            }
            // if all positions fail, try another variation
        }
        // if all variation fail, return null matrix and how many cases were explored
        OutputPuzzlerPro res = new OutputPuzzlerPro(null, 0, countCases);
        return res;
    }

    public static char[][] createEmptyMatrix(int height, int width) {
        char[][] matrix = new char[height][width];
        
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                matrix[i][j] = '_';
            }
        }

        return matrix;
    }


    public static boolean matrixIsFull(char[][] m) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                if (m[i][j] == '_') {
                    return false;
                }
            }
        }

        return true;
    }

    public static List<char[][]> getPieceVariations(char[][] piece) {
        List<char[][]> variations = new ArrayList<>();
        variations.add(piece);

        // flip
        char[][] mirroredPieceHorizontal = new char[piece.length][piece[0].length];

        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[0].length; j++) {
                mirroredPieceHorizontal[i][piece[0].length - j - 1] = piece[i][j];
            }
        }
        variations.add(mirroredPieceHorizontal);

        // rotate
        char[][] oldVar = piece;
        for (int i = 0; i < 3; i++) {
            int rows = oldVar.length;
            int cols = oldVar[0].length;
            char[][] var = new char[cols][rows];

            for (int j = 0; j < rows; j++) {
                for (int k = 0; k < cols; k++) {
                    var[k][rows - 1 - j] = oldVar[j][k];
                }
            }

            variations.add(var);
            oldVar = var;
        }

        oldVar = mirroredPieceHorizontal;
        for (int i = 0; i < 3; i++) {
            int rows = oldVar.length;
            int cols = oldVar[0].length;
            char[][] var = new char[cols][rows];

            for (int j = 0; j < rows; j++) {
                for (int k = 0; k < cols; k++) {
                    var[k][rows - 1 - j] = oldVar[j][k];
                }
            }

            variations.add(var);
            oldVar = var;
        }
        
        return variations;
    }

    public static char[][] placeVariation(char[][] currentMatrix, char[][] currentVariation, int startX, int startY) {
        char[][] newMatrix = new char[currentMatrix.length][currentMatrix[0].length];

        for (int i = 0; i < currentMatrix.length; i++) {
            System.arraycopy(currentMatrix[i], 0, newMatrix[i], 0, currentMatrix[i].length);
        }

        for (int i = 0; i < currentVariation.length; i++) {
            for (int j = 0; j < currentVariation[0].length; j++) {
                if (currentVariation[i][j] != '_') {
                    newMatrix[startX + i][startY + j] = currentVariation[i][j];
                }
            }
        }

        return newMatrix;
    }

    public static void printMatrix(char[][] m) {
        if (m == null) {
            System.out.println("Matrix: null");
        } else {
            System.out.println("Matrix:");
            for (int i = 0; i < m.length; i++) {
                for (int j = 0; j < m[i].length; j++) {
                    System.out.print(m[i][j] + " ");
                }
                System.out.println();
            }
        }
    }

    public static List<char[][]> copyListOfMatrix(List<char[][]> originalList) {
        List<char[][]> copiedList = new ArrayList<>();

        for (char[][] originalMatrix : originalList) {
            char[][] copiedMatrix = new char[originalMatrix.length][];
            
            for (int i = 0; i < originalMatrix.length; i++) {
                copiedMatrix[i] = new char[originalMatrix[i].length];
                System.arraycopy(originalMatrix[i], 0, copiedMatrix[i], 0, originalMatrix[i].length);
            }

            copiedList.add(copiedMatrix);
        }

        return copiedList;
    }

    public static List<char[][]> listWithoutFirstElement(List<char[][]> originalList) {
        if (originalList.isEmpty()) {
            return new ArrayList<>(); 
        }

        return new ArrayList<>(originalList.subList(1, originalList.size()));
    }

}