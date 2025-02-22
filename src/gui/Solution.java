package gui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import helpercomp.*;
import custom.*;
import java.nio.file.Files;
import javax.print.attribute.standard.OutputDeviceAssigned;

public class Solution extends JFrame {
    public Solution(InputFormat form ) {
        String currentDirectory = System.getProperty("user.dir");
        File currentDirFile = new File(currentDirectory);
        String tempString = currentDirFile + File.separator + "temp";

        initialize(form);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                deleteTempFolder(new File(tempString));
            }
        });
    }

    public void initialize(InputFormat form) {
        OutputPuzzlerPro output = BruteForce.getOutput(form);
        output.printDetails();

        // Temporary folder for saving the solution
        String currentDirectory = System.getProperty("user.dir");
        File currentDirFile = new File(currentDirectory);
        String tempString = currentDirFile + File.separator + "temp";
        File tempFolder = new File(tempString);

        if (!tempFolder.exists()) {
            if (tempFolder.mkdirs()) {
                System.out.println("Temporary folder created: " + tempFolder.getAbsolutePath());
            } else {
                System.out.println("Failed to create temporary folder.");
            }
        } else {
            System.out.println("Temporary folder already exists.");
        }

        // Define the file path for the solution image
        String filename = tempFolder.getAbsolutePath() + File.separator + "solution.png";

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(Color.WHITE);

        JLabel boardLabel = new JLabel("Solution Board");
        boardLabel.setFont(new Font("Arial", Font.BOLD, 24));  
        boardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  
        boardLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));  
        topPanel.add(boardLabel);

        // Check if the solution board is available
        if (output.filledBoardSolution != null) {
            OutputImage.solutionDisplayImage(output.filledBoardSolution, filename, "LIGHT");

            ImageIcon boardImageIcon = new ImageIcon(filename);
            JLabel boardImageLabel = new JLabel(boardImageIcon);

            boardImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            topPanel.add(boardImageLabel);
        } else {
            JPanel noSolutionPanel = new JPanel();
            noSolutionPanel.setPreferredSize(new Dimension(400, 400));
            noSolutionPanel.setBackground(Color.WHITE);
            JLabel noSolutionLabel = new JLabel("No Valid Solution");
            noSolutionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  
            noSolutionPanel.add(noSolutionLabel);
            noSolutionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            topPanel.add(noSolutionPanel);
        }

        // Panel to house infoPanel
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(240, 240, 240));
        
        // Execution time and number of cases explored stored in infoPanel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 

        infoPanel.setPreferredSize(new Dimension(600, 100));
        infoPanel.setMaximumSize(new Dimension(600, 100));

        // Create the execution time label
        JLabel executionTimeLabel = new JLabel("Execution Time = " + output.executionTime + " ms");
        executionTimeLabel.setFont(new Font("Arial", Font.BOLD, 20)); 
        executionTimeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);  
        infoPanel.add(executionTimeLabel);

        infoPanel.add(Box.createVerticalStrut(10));

        // Create the cases explored label
        JLabel casesExploredLabel = new JLabel("Number of Cases Explored = " + output.casesExplored);
        casesExploredLabel.setFont(new Font("Arial", Font.BOLD, 20)); 
        casesExploredLabel.setAlignmentX(Component.LEFT_ALIGNMENT); 
        infoPanel.add(casesExploredLabel);

        centerPanel.add(infoPanel);

        // Bottom Panel with buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 2, 10, 10));  // 1 row, 2 columns, 10px horizontal and vertical gap
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Button for saving the solution as text and image
        JButton saveTextButton = new JButton("Save Solution Text");
        JButton saveImageButton = new JButton("Save Solution Image");

        saveTextButton.setMaximumSize(new Dimension(300, 100));
        saveImageButton.setMaximumSize(new Dimension(300, 100));

        saveTextButton.setBackground(Color.BLACK);  
        saveImageButton.setBackground(Color.BLACK);  
        saveTextButton.setFont(new Font("Arial", Font.PLAIN, 15));  
        saveImageButton.setFont(new Font("Arial", Font.PLAIN, 15));  
        saveTextButton.setForeground(Color.WHITE);  
        saveImageButton.setForeground(Color.WHITE);  
        saveTextButton.setFocusPainted(false);   
        saveImageButton.setFocusPainted(false);   

        // Save solution text button functionality
        saveTextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(currentDirFile);
                fileChooser.setDialogTitle("Choose a location to save the solution text");
                fileChooser.setSelectedFile(new File("solution.txt"));

                int returnValue = fileChooser.showSaveDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    // Ensure the file has the ".txt" extension
                    if (!selectedFile.getName().endsWith(".txt")) {
                        selectedFile = new File(selectedFile.getAbsolutePath() + ".txt");
                    }
                    String fname = selectedFile.getAbsolutePath();
                    OutputTxt.output(form.input, output, fname);  // Save the text file
                }
            }
        });
        bottomPanel.add(saveTextButton);

        // Save solution as image button functionality
        saveImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (output.filledBoardSolution != null) {
                    JFileChooser fileChooser = new JFileChooser(currentDirFile);
                    fileChooser.setDialogTitle("Choose a location to save the solution image");
                    fileChooser.setSelectedFile(new File("solution.png"));

                    int returnValue = fileChooser.showSaveDialog(null);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        // Ensure the file has the ".png" extension
                        if (!selectedFile.getName().endsWith(".png")) {
                            selectedFile = new File(selectedFile.getAbsolutePath() + ".png");
                        }
                        String fname = selectedFile.getAbsolutePath();
                        OutputImage.solutionImage(output.filledBoardSolution, fname, "LIGHT");  // Save the image
                    }
                } else {
                    JOptionPane.showMessageDialog(Solution.this, "No solution image to save",
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        bottomPanel.add(saveImageButton);

        // mainPanel
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(new Color(240, 240, 240));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add all the components to the main panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        setTitle("Peak Brute Force IQ Puzzler Pro Solver Solution (Def not brain rot fr fr)");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(240, 240, 240));
        add(mainPanel);
        setMinimumSize(new Dimension(1200, 800));
        pack();
        setVisible(true);
    }

    private void deleteTempFolder(File folder) {
        if (folder.exists()) {
            try {
                Files.walk(folder.toPath())
                        .map(java.nio.file.Path::toFile)
                        .sorted((f1, f2) -> f2.compareTo(f1)) 
                        .forEach(File::delete);
                System.out.println("Temporary folder deleted successfully!");
            } catch (IOException ex) {
                System.err.println("Failed to delete temp folder: " + ex.getMessage());
            }
        }
    }

}