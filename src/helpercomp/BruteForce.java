package helpercomp;
import custom.*;
import java.io.*;
import java.util.*;

public class BruteForce {
    public static OutputPuzzlerPro getOutput(InputPuzzlerPro input) {
        char[][] beginningMat;
        if (input.mode.equals("DEFAULT")) {
            beginningMat = createEmptyMatrix(input.height, input.width);
        } else if (input.mode.equals("CUSTOM")) {
            beginningMat = input.customBoard;
        } else {
            System.out.printf("Error: %s is not a valid case%n", input.mode);
            return null;
        }

        long startTime = System.currentTimeMillis();
        OutputPuzzlerPro res = processPuzzle(beginningMat, input.piecesList);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        res.executionTime = duration;

        return res;
    }

    public static OutputPuzzlerPro processPuzzle(char[][] currentMatrix, List<char[][]> pieces) {
        if (pieces.isEmpty()) {
            if (matrixIsFull(currentMatrix)) {
                OutputPuzzlerPro res = new OutputPuzzlerPro(currentMatrix, 0, 1);
                return res;
            } else {
                OutputPuzzlerPro res = new OutputPuzzlerPro(null, 0, 1);
                return res;
            }
        }

        char[][] currentPiece = pieces.remove(0);
        List<char[][]> currentVariations = getPieceVariations(currentPiece);

        for (int i = 0; i < currentVariations.size(); i++) {
            int currentWidth = currentVariations.get(i)[0].length;
            int currentHeight = currentVariations.get(i).length;

            int height = currentMatrix.length - currentHeight + 1;
            int width = currentMatrix[0].length - currentWidth + 1;

            if (height <= 0 || width <= 0) {
                continue;
            }

            for (int j = 0; j < height; j++) {
                for (int k = 0; k < width; k++) {
                    boolean canFit = true;

                    // determining if currentVariation fits
                    for (int x = 0; x < currentHeight; x++) {
                        for (int y = 0; y < currentWidth; y++) {
                            if (currentVariations.get(i)[x][y] != '_' && currentMatrix[i + x][j + y] != '_') {
                                canFit = false;
                                break;
                            }
                        }
                        if (!canFit) break;
                    }

                    // If it fits, place the variation into currentMatrix and continue to the next piece
                    if (canFit) {
                        char[][] newMatrix = placeVariation(currentMatrix, currentVariations.get(i), i, j);
                        OutputPuzzlerPro res = processPuzzle(newMatrix, pieces);
                        if (res.filledBoardSolution != null) {
                            return res;
                        } else {
                            // count up the cases
                        }
                    }
                    // else try fitting it in another spot
                }
            }
            // if all positions fail, try another variation
        }
        // if all variation fail, return null matrix and how many cases were explored
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
        List<char[][]> variations;

        // rotate
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

}