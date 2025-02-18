package helpercomp;
import custom.*;
import java.io.*;
import java.util.*;

public class BruteForce {
    public static OutputPuzzlerPro getOutput(InputPuzzlerPro input) {
        
    }

    public OutputPuzzlerPro processPuzzle(char[][] currentMatrix, List<char[][]> pieces) {
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

            for (int j = 0; j < currentMatrix.length - currentHeight + 1; j++) {
                for (int k = 0; k < currentMatrix[0].length - currentWidth + 1; k++) {

                }
            }
        }
    }

    public boolean matrixIsFull(char[][] m) {

    }

    public List<char[][]> getPieceVariations(char[][] piece) {
        
    }
}