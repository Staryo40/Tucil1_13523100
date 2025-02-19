import helpercomp.*;
import custom.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        InputPuzzlerPro input = Input.readTxt("../test/test1custom.txt");

        // INPUT TEST
        // input.printDetails();

        // PIECE VARIATION TEST
        // List<char[][]> var = BruteForce.getPieceVariations(input.piecesList.get(6));
        // input.printPieces(var);
        
        // MAIN PROCESS TEST
        OutputPuzzlerPro output = BruteForce.getOutput(input);
        output.printDetails();
    }
}