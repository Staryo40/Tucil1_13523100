package custom;
import java.util.List;

public class InputPuzzlerPro {
    public int height;
    public int width;
    public int numPieces;
    public String mode;
    public char[][] customBoard;
    public List<char[][]> piecesList;

    public InputPuzzlerPro(int height, int width, int numPieces, String mode, 
                           char[][] customBoard, List<char[][]> piecesList) {
        this.height = height;
        this.width = width;
        this.numPieces = numPieces;
        this.mode = mode;
        this.customBoard = customBoard;
        this.piecesList = piecesList;
    }

    public void printBoard(char[][] board) {
        if (board == null) {
            System.out.println("Custom Board: null");
        } else {
            System.out.println("Custom Board:");
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    System.out.print(board[i][j] + " ");
                }
                System.out.println();
            }
        }
    }

    public void printPieces(List<char[][]> piecesList) {
        if (piecesList.isEmpty()) {
            System.out.println("Pieces List: empty");
        } else {
            System.out.println("Pieces List:");
            for (int index = 0; index < piecesList.size(); index++) {
                System.out.println("Piece " + (index + 1) + ":");
                if (piecesList.get(index) == null) {
                    System.out.println("null");
                } else {
                    for (int i = 0; i < piecesList.get(index).length; i++) {
                        for (int j = 0; j < piecesList.get(index)[i].length; j++) {
                            System.out.print(piecesList.get(index)[i][j] + " ");
                        }
                        System.out.println();
                    }
                }
            }
        }
    }

    public void printDetails() {
        System.out.println("Height: " + height);
        System.out.println("Width: " + width);
        System.out.println("Number of Pieces: " + numPieces);
        System.out.println("Mode: " + mode);
        printBoard(customBoard);
        System.out.println("");
        printPieces(piecesList);
    }
}