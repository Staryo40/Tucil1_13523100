package helpercomp;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import custom.*;


public class OutputImage {

    private static final Color[] COLORS = {
        new Color(45, 70, 84),     // charcoal
        new Color(139, 69, 19),    // saddlebrown
        new Color(0, 100, 0),      // darkgreen
        new Color(128, 128, 0),    // olive
        new Color(72, 61, 139),    // darkslateblue
        new Color(60, 179, 113),   // mediumseagreen
        new Color(0, 128, 128),    // teal
        new Color(0, 0, 128),      // navy
        new Color(154, 205, 50),   // yellowgreen
        new Color(139, 0, 139),    // darkmagenta
        new Color(176, 48, 96),    // maroon3
        new Color(255, 0, 0),      // red
        new Color(255, 165, 0),    // orange
        new Color(255, 218, 34),    // school bus yellow
        new Color(127, 255, 0),    // chartreuse
        new Color(138, 43, 226),   // blueviolet
        new Color(0, 255, 127),    // springgreen
        new Color(220, 20, 60),    // crimson
        new Color(0, 255, 255),    // aqua
        new Color(0, 191, 255),    // deepskyblue
        new Color(0, 0, 255),      // blue
        new Color(255, 127, 80),   // coral
        new Color(255, 0, 255),    // fuchsia
        new Color(30, 144, 255),   // dodgerblue
        new Color(96, 58, 64),  // Eggplant
        new Color(221, 160, 221),   // plum

    };

    public static void solutionImage(char[][] solutionBoard, String filename, String mode) {
        int cellSize = 80; // Size of each cell 
        int padding = 5;  // Padding between cells
        int edgePadding = 40; // Padding at edge of image 
        int fontSize = 40; // Font size for letters inside of the cells

        int rows = solutionBoard.length;
        int cols = solutionBoard[0].length;

        // Calculate the width and height of the image based on the matrix size
        int imageWidth = (cols * cellSize) + ((cols - 1) * padding) + 2 * edgePadding;
        int imageHeight = (rows * cellSize) + ((rows - 1) * padding) + 2 * edgePadding;

        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        if (mode.equals("DARK")) {
            g2d.setColor(Color.BLACK);
        } else {
            g2d.setColor(Color.WHITE);
        }
        
        g2d.fillRect(0, 0, imageWidth, imageHeight);
        
        // Set font and stroke for circle cell
        g2d.setFont(new Font("Arial", Font.PLAIN, fontSize));
        g2d.setStroke(new BasicStroke(2));

        // Calculate the starting positions to center the matrix
        int startX = edgePadding;
        int startY = edgePadding;

        // + (imageWidth - (cols * (cellSize + padding) + edgePadding)) / 2;
        // + (imageHeight - (rows * (cellSize + padding) + edgePadding)) / 2;

        // Iterate through the matrix and draw circles for capital letters
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char currentChar = solutionBoard[i][j];
                int centerX = startX + j * (cellSize + padding) + cellSize / 2;
                int centerY = startY + i * (cellSize + padding) + cellSize / 2;

                // Draw a circle only for capital letters
                if (Character.isUpperCase(currentChar)) {
                    int charIndex = currentChar - 'A';
                    g2d.setColor(COLORS[charIndex]);

                    g2d.fillOval(centerX - cellSize / 2, centerY - cellSize / 2, cellSize, cellSize); // Draw circle
                    g2d.setColor(Color.WHITE); // Draw letter in white inside the circle

                    FontMetrics fontMetrics = g2d.getFontMetrics();
                    int charWidth = fontMetrics.charWidth(currentChar);
                    int charX = centerX - (charWidth / 2); 
                    int baselineY = centerY + (fontMetrics.getAscent() - fontMetrics.getDescent()) / 2;

                    g2d.drawString(String.valueOf(currentChar), charX, baselineY); // Draw the letter

                    // Draw Links
                    // Up (i-1, j)
                    if (i > 0 && solutionBoard[i-1][j] == currentChar) {
                        drawLinkBetweenCells(g2d, centerX, centerY - cellSize/2, centerX, centerY - (cellSize/2 + padding), COLORS[charIndex]);
                    }
                    // Down (i+1, j)
                    if (i < rows - 1 && solutionBoard[i+1][j] == currentChar) {
                        drawLinkBetweenCells(g2d, centerX, centerY + cellSize/2, centerX, centerY + (cellSize/2 + padding), COLORS[charIndex]);
                    }
                    // Left (i, j-1)
                    if (j > 0 && solutionBoard[i][j-1] == currentChar) {
                        drawLinkBetweenCells(g2d, centerX - cellSize/2, centerY, centerX - (cellSize/2 + padding), centerY, COLORS[charIndex]);
                    }
                    // Right (i, j+1)
                    if (j < cols - 1 && solutionBoard[i][j+1] == currentChar) {
                        drawLinkBetweenCells(g2d, centerX + cellSize/2, centerY, centerX + (cellSize/2 + padding), centerY, COLORS[charIndex]);
                    }
                } else {
                    if (mode.equals("DARK")) {
                        g2d.setColor(Color.BLACK);
                    } else {
                        g2d.setColor(Color.WHITE);
                    }
                    g2d.fillRect(centerX - cellSize / 2, centerY - cellSize / 2, cellSize, cellSize); // Clear the area
                }
                
            }
        }

        g2d.dispose();

        try {
            ImageIO.write(image, "PNG", new File(filename));
            System.out.println("Image saved as " + filename);
        } catch (IOException e) {
            System.out.println("Error saving the image: " + e.getMessage());
        }

    }

    public static void drawLinkBetweenCells(Graphics2D g2d, int x1, int y1, int x2, int y2, Color lineColor) {
        g2d.setColor(lineColor); 
        g2d.setStroke(new BasicStroke(20)); 
        g2d.drawLine(x1, y1, x2, y2);
    }

    // Helper method to display the image in a window
    private static void displayImage(BufferedImage image) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(image.getWidth(), image.getHeight());
        frame.add(new JLabel(new ImageIcon(image)));
        frame.setVisible(true);
    }

    public static void solutionDisplayImage(char[][] solutionBoard, String filename, String mode) {
        int cellSize = 40; // Size of each cell 
        int padding = 5;  // Padding between cells
        int edgePadding = 20; // Padding at edge of image 
        int fontSize = 20; // Font size for letters inside of the cells

        int rows = solutionBoard.length;
        int cols = solutionBoard[0].length;

        // Calculate the width and height of the image based on the matrix size
        int imageWidth = (cols * cellSize) + ((cols - 1) * padding) + 2 * edgePadding;
        int imageHeight = (rows * cellSize) + ((rows - 1) * padding) + 2 * edgePadding;

        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        if (mode.equals("DARK")) {
            g2d.setColor(Color.BLACK);
        } else {
            g2d.setColor(Color.WHITE);
        }
        
        g2d.fillRect(0, 0, imageWidth, imageHeight);
        
        // Set font and stroke for circle cell
        g2d.setFont(new Font("Arial", Font.PLAIN, fontSize));
        g2d.setStroke(new BasicStroke(2));

        // Calculate the starting positions to center the matrix
        int startX = edgePadding;
        int startY = edgePadding;

        // + (imageWidth - (cols * (cellSize + padding) + edgePadding)) / 2;
        // + (imageHeight - (rows * (cellSize + padding) + edgePadding)) / 2;

        // Iterate through the matrix and draw circles for capital letters
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char currentChar = solutionBoard[i][j];
                int centerX = startX + j * (cellSize + padding) + cellSize / 2;
                int centerY = startY + i * (cellSize + padding) + cellSize / 2;

                // Draw a circle only for capital letters
                if (Character.isUpperCase(currentChar)) {
                    int charIndex = currentChar - 'A';
                    g2d.setColor(COLORS[charIndex]);

                    g2d.fillOval(centerX - cellSize / 2, centerY - cellSize / 2, cellSize, cellSize); // Draw circle
                    g2d.setColor(Color.WHITE); // Draw letter in white inside the circle

                    FontMetrics fontMetrics = g2d.getFontMetrics();
                    int charWidth = fontMetrics.charWidth(currentChar);
                    int charX = centerX - (charWidth / 2); 
                    int baselineY = centerY + (fontMetrics.getAscent() - fontMetrics.getDescent()) / 2;

                    g2d.drawString(String.valueOf(currentChar), charX, baselineY); // Draw the letter

                    // Draw Links
                    // Up (i-1, j)
                    if (i > 0 && solutionBoard[i-1][j] == currentChar) {
                        drawLinkBetweenCells(g2d, centerX, centerY - cellSize/2, centerX, centerY - (cellSize/2 + padding), COLORS[charIndex]);
                    }
                    // Down (i+1, j)
                    if (i < rows - 1 && solutionBoard[i+1][j] == currentChar) {
                        drawLinkBetweenCells(g2d, centerX, centerY + cellSize/2, centerX, centerY + (cellSize/2 + padding), COLORS[charIndex]);
                    }
                    // Left (i, j-1)
                    if (j > 0 && solutionBoard[i][j-1] == currentChar) {
                        drawLinkBetweenCells(g2d, centerX - cellSize/2, centerY, centerX - (cellSize/2 + padding), centerY, COLORS[charIndex]);
                    }
                    // Right (i, j+1)
                    if (j < cols - 1 && solutionBoard[i][j+1] == currentChar) {
                        drawLinkBetweenCells(g2d, centerX + cellSize/2, centerY, centerX + (cellSize/2 + padding), centerY, COLORS[charIndex]);
                    }
                } else {
                    if (mode.equals("DARK")) {
                        g2d.setColor(Color.BLACK);
                    } else {
                        g2d.setColor(Color.WHITE);
                    }
                    g2d.fillRect(centerX - cellSize / 2, centerY - cellSize / 2, cellSize, cellSize); // Clear the area
                }
                
            }
        }

        g2d.dispose();

        try {
            ImageIO.write(image, "PNG", new File(filename));
            System.out.println("Image saved as " + filename);
        } catch (IOException e) {
            System.out.println("Error saving the image: " + e.getMessage());
        }

    }
}
