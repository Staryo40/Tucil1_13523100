import helpercomp.*;
import custom.*;
import java.util.*;
import jdk.jshell.spi.SPIResolutionException;

public class Main {
    public static void main(String[] args) {
        InputPuzzlerPro input = Input.readTxt("../test/test1custom.txt");

        // INPUT TEST
        // input.printDetails();

        // PIECE VARIATION TEST
        // List<char[][]> var = BruteForce.getPieceVariations(input.piecesList.get(6));
        // input.printPieces(var);
        
        // MAIN PROCESS TEST
        // OutputPuzzlerPro output = BruteForce.getOutput(input);
        // output.printDetails();

        // TEXT OUTPUT TEST
        // System.out.println("Current working directory: " + System.getProperty("user.dir"));
        // OutputPuzzlerPro output = BruteForce.getOutput(input);
        // String filename = "../test/solution.txt";
        // OutputTxt.output(input, output, filename);

        // IMAGE OUTPUT TEST
        OutputPuzzlerPro output = BruteForce.getOutput(input);
        String filename = "../test/solutionBoard.png";
        output.printDetails();
        OutputImage.solutionImage(output.filledBoardSolution, filename, "LIGHT");
    }
}