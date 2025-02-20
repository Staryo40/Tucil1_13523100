package helpercomp;
import custom.InputPuzzlerPro;
import java.io.*;
import java.util.*;

public class Input {
    public static InputPuzzlerPro readTxt(String filename) {
        try {
            File file = new File(filename);
            Scanner reader = new Scanner(file);

            // Reading N M P
            int height = -1;
            int width = -1;
            int numPieces = -1;
            if (reader.hasNextLine()) {
                String line = reader.nextLine().strip();  
                String[] parts = line.split("\\s+");  // Split the line into parts by spaces

                if (parts.length == 3) {
                    height = Integer.parseInt(parts[0]);
                    width = Integer.parseInt(parts[1]);
                    numPieces = Integer.parseInt(parts[2]);

                    if (height <= 0 || width <= 0) {
                        System.out.println("Error: matrix dimension zero or negative");
                        return null;
                    }

                    if (numPieces <= 0 || numPieces > 26) {
                        System.out.println("Error: number of pieces is not in range 1 to 26");
                        return null;
                    }
                    
                } else {
                    System.out.println("Error: first line does not contain three integers");
                    return null;
                }
            }

            // Reading mode
            String mode = "";
            if (reader.hasNextLine()) {
                String line = reader.nextLine().strip();  

                switch (line) {
                    case "DEFAULT":
                        mode = "DEFAULT";
                        break;
                    case "CUSTOM":
                        mode = "CUSTOM";
                        break;
                    default:
                        System.out.printf("Error: %s is not a valid case%n", line);
                        return null;
                }
            }

            // Reading matrix if mode == "CUSTOM"
            char[][] customBoard = null;
            String lineAfterMatrix = "";

            if (mode.equals("CUSTOM")) {
                boolean inMatrix = false;
                ArrayList<String> matrixLines = new ArrayList<>();
                
                while (reader.hasNextLine()) {
                    String line = reader.nextLine();  
                    
                    if (line.contains("X") && line.strip().length() == width) {
                        inMatrix = true;
                    }
                     
                    if (inMatrix) {
                        matrixLines.add(line.strip());
                        inMatrix = false;
                    } else {
                        if (matrixLines.isEmpty()) {
                            System.out.println("Error: no custom matrix provided OR matrix provided does not match given width");
                            return null;
                        }
                        lineAfterMatrix = line;
                        break;
                    }
                }

                customBoard = new char[matrixLines.size()][];  
                for (int i = 0; i < matrixLines.size(); i++) {
                    String row = matrixLines.get(i);  
                    customBoard[i] = row.toCharArray();  
                }

                if (customBoard.length != height) {
                    System.out.println("Error: Board height does not match given height");
                    return null;
                }
            }

            // Reading pieces 
            String line;
            ArrayList<String> piecesCandidate = new ArrayList<>();

            if (mode.equals("CUSTOM")) {
                line = lineAfterMatrix;
            } else {
                line = reader.nextLine();  
            }

            while (true) {
                if (isPiecePartValid(line)){
                    piecesCandidate.add(line);
                } else {
                    System.out.println("Error: invalid piece detected -> has different characters in one row OR not a capital alphabet");
                    System.out.printf("Error piece: %s%n", line);
                    return null;
                }

                if (reader.hasNextLine()) {
                    line = reader.nextLine();  
                } else {
                    break;
                }
            }

            List<char[][]> pieces = new ArrayList<>();
            ArrayList<String> currentPiece = new ArrayList<>();

            for (int i = 0; i < piecesCandidate.size(); i++) {
                String piecePart = piecesCandidate.get(i);
                
                if (currentPiece.isEmpty() || getFirstNonSpaceChar(piecePart) == getFirstNonSpaceChar(currentPiece.get(0))) { // have not handled if starts with space
                    currentPiece.add(piecePart);
                } else {
                    // If we encounter a different character, create a matrix from the current group
                    pieces.add(createMatrixFromPiece(currentPiece));
                    currentPiece.clear(); 
                    currentPiece.add(piecePart);
                    
                }

                // add last piece
                if (i == piecesCandidate.size()-1) {
                    pieces.add(createMatrixFromPiece(currentPiece));
                }
            }

            if (pieces.size() != numPieces) {
                System.out.printf("Error: pieces detected: %d, expected number of pieces: %d%n", pieces.size(), numPieces);
                return null;
            }

            if (!isAllPiecesValid(pieces)) {
                System.out.printf("Error: Invalid piece detected, cells of the piece are not connected%n");
                return null;
            }

            InputPuzzlerPro res = new InputPuzzlerPro(height, width, numPieces, mode, customBoard, pieces);
            if (mode.equals("CUSTOM")) {
                res.customBoard = res.matrixTurnXtoUnd(res.customBoard);
            }
            return res;
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found - " + e.getMessage());
            return null; 
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format - " + e.getMessage());
            return null; 
        }
    }

    public static char[][] createMatrixFromPiece(List<String> piece) {
        int maxLength = piece.stream().mapToInt(String::length).max().orElse(0);
        char[][] matrix = new char[piece.size()][maxLength];

        for (int i = 0; i < piece.size(); i++) {
            String piecePart = piece.get(i);
            for (int j = 0; j < piecePart.length(); j++) {
                matrix[i][j] = (piecePart.charAt(j) == ' ') ? '_' : piecePart.charAt(j);
            }
            for (int j = piecePart.length(); j < maxLength; j++) {
                matrix[i][j] = '_';
            }
        }
        return matrix;
    }

    public static boolean isPiecePartValid(String piecePart) {
        if (piecePart.isEmpty()) {
            return false;  
        }

        char alph = getFirstNonSpaceChar(piecePart);

        if (!Character.isUpperCase(alph)) {
            return false;  // If the first character is not a capital letter, return false
        }
        
        for (int i = 1; i < piecePart.length(); i++) {
            if (piecePart.charAt(i) != alph && piecePart.charAt(i) != ' ') {
                return false;  
            }
        }

        return true;
    }

    public static char getFirstNonSpaceChar(String line) {
        for (int i = 0; i < line.length(); i++) {
            char currentChar = line.charAt(i);
            
            if (currentChar != ' ') {
                return currentChar;
            }
        }
        
        return '\0'; 
    }

    public static boolean isAllPiecesValid(List<char[][]> pieces) {
        for (char[][] currentPiece : pieces) {
            if (currentPiece.length != 1 && currentPiece[0].length != 0) {
                for (int j = 0; j < currentPiece.length; j++) {
                    for (int k = 0; k < currentPiece[0].length; k++) {
                        char currentChar = currentPiece[j][k];
                        if (Character.isUpperCase(currentChar)) {
                            boolean valid = false;
                            // check up
                            if (j-1 >= 0 && currentChar == currentPiece[j-1][k]) {
                                valid = true;
                                continue;
                            }
                            // check down
                            if (j+1 < currentPiece.length && currentChar == currentPiece[j+1][k]) {
                                valid = true;
                                continue;
                            }
                            // check right
                            if (k+1 < currentPiece[0].length && currentChar == currentPiece[j][k+1]) {
                                valid = true;
                                continue;
                            }
                            // check left
                            if (k-1 >= 0 && currentChar == currentPiece[j][k-1]) {
                                valid = true;
                                continue;
                            }

                            if (!valid) {
                                // System.out.printf("%s%n", currentChar);
                                return false;
                            }
                        }
                    }
                }
            }
        }
        // System.out.println("true ran");
        return true;
    }
}