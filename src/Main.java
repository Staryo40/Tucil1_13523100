import helpercomp.*;
import custom.*;
import gui.*;
import java.util.*;
import jdk.jshell.spi.SPIResolutionException;

public class Main {
    public static void main(String[] args) {
        // InputFormat form = Input.readTxt("../test/test1default.txt");
        // InputPuzzlerPro input = form.input;

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
        // OutputPuzzlerPro output = BruteForce.getOutput(input);
        // String filename = "../test/solutionBoard1.png";
        // output.printDetails();
        // OutputImage.solutionImage(output.filledBoardSolution, filename, "DARK");

        // GUI
        new MainFrame();  
        // InputImageGenerator.saveInput(input);
    }
}