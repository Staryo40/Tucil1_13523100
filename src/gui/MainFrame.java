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
import java.util.concurrent.atomic.AtomicReference;

public class MainFrame extends JFrame {

    private JButton chooseFileButton;
    private JButton uploadFileButton;
    private JButton solveButton;
    private File selectedFile = null;

    public MainFrame() {
        String currentDirectory = System.getProperty("user.dir");
        File currentDirFile = new File(currentDirectory);
        String tempString = currentDirFile + File.separator + "temp";

        initialize();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                deleteTempFolder(new File(tempString));
            }
        });
    }

    public void initialize() {
        setTitle("Peak Brute Force IQ Puzzler Pro Solver (Def not brain rot fr fr)");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        getContentPane().setBackground(new Color(240, 240, 240));

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));  
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 
        mainPanel.setBackground(new Color(230, 230, 230));

        // Left panel (dynamic)
        JPanel leftPanel = new JPanel(new BorderLayout(20, 20));
        leftPanel.setBackground(new Color(230, 230, 230));

        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS)); 
        leftPanel.setBackground(Color.WHITE);

        JPanel topLeftPanel = new JPanel();
        topLeftPanel.setLayout(new BoxLayout(topLeftPanel, BoxLayout.Y_AXIS));  
        topLeftPanel.setBackground(Color.WHITE); 

        JLabel boardLabel = new JLabel("Empty Board");
        boardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  
        topLeftPanel.add(boardLabel);  

        JPanel emptyBox = new JPanel();
        emptyBox.setAlignmentX(Component.CENTER_ALIGNMENT);  
        emptyBox.setBackground(Color.WHITE);  

        topLeftPanel.add(emptyBox); 

        JPanel bottomLeftPanel = new JPanel();
        bottomLeftPanel.setLayout(new BoxLayout(bottomLeftPanel, BoxLayout.Y_AXIS));  
        bottomLeftPanel.setBackground(Color.WHITE);  

        JLabel piecesLabel = new JLabel("Pieces");
        piecesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  
        bottomLeftPanel.add(piecesLabel); 

        JPanel[] imagePanels = new JPanel[26];  
        JLabel[] imageLabels = new JLabel[26]; 

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(0, 7, 10, 10));  // 0 rows (dynamic), 6 columns, 10px gap

        for (int i = 0; i < 26; i++) {
            imagePanels[i] = new JPanel();
            imagePanels[i].setBackground(Color.WHITE);  
            imagePanels[i].setPreferredSize(new Dimension(150, 150)); 
            
            imageLabels[i] = new JLabel("Piece " + (i + 1));
            imageLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
            imageLabels[i].setVerticalAlignment(SwingConstants.CENTER);
            
            imagePanels[i].add(imageLabels[i]);
            
            gridPanel.add(imagePanels[i]);
        }

        bottomLeftPanel.add(gridPanel);

        leftPanel.add(topLeftPanel);   
        leftPanel.add(bottomLeftPanel); 

        // marginPanel to match mainPanel padding
        JPanel marginPanelLeft = new JPanel();
        marginPanelLeft.setLayout(new BorderLayout());
        marginPanelLeft.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0)); 
        marginPanelLeft.add(leftPanel, BorderLayout.CENTER); 

        mainPanel.add(marginPanelLeft, BorderLayout.CENTER);

        // Right panel (buttons)
        JPanel rightPanel = new JPanel();  
        rightPanel.setPreferredSize(new Dimension(300, 150));  
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));  
        rightPanel.setBackground(new Color(230, 230, 230));

        JPanel fixedPanel = new JPanel();
        fixedPanel.setLayout(new BoxLayout(fixedPanel, BoxLayout.Y_AXIS)); 
        fixedPanel.setBackground(Color.WHITE);

        Border paddingBorder = BorderFactory.createEmptyBorder(20, 20, 20, 20);  
        fixedPanel.setBorder(paddingBorder); 

        // File buttons
        chooseFileButton = new JButton("Choose File");
        uploadFileButton = new JButton("Upload File");

        chooseFileButton.setPreferredSize(new Dimension(200, 40));  
        uploadFileButton.setPreferredSize(new Dimension(200, 40));  
        chooseFileButton.setMaximumSize(new Dimension(200, 40));
        uploadFileButton.setMaximumSize(new Dimension(200, 40));

        chooseFileButton.setBackground(Color.BLACK);  
        uploadFileButton.setBackground(Color.BLACK);  
        chooseFileButton.setFont(new Font("Arial", Font.PLAIN, 15));  
        uploadFileButton.setFont(new Font("Arial", Font.PLAIN, 15));  
        chooseFileButton.setForeground(Color.WHITE);  
        uploadFileButton.setForeground(Color.WHITE);  
        chooseFileButton.setFocusPainted(false);   
        uploadFileButton.setFocusPainted(false);   

        chooseFileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        uploadFileButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Filename text
        JLabel fileName = new JLabel("No file selected", SwingConstants.CENTER);
        fileName.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Choose file button functionality
        chooseFileButton.addActionListener((ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser();
            String currentDirectory = System.getProperty("user.dir");
            File currentDirFile = new File(currentDirectory);

            File initialDirectory = new File(currentDirectory);

            if (initialDirectory.exists() && initialDirectory.isDirectory()) {
                fileChooser.setCurrentDirectory(initialDirectory);
            }

            FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("Text Files (.txt)", "txt");
            fileChooser.setFileFilter(txtFilter);

            int result = fileChooser.showOpenDialog(MainFrame.this);
            
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                fileName.setText("Selected file: " + selectedFile.getName());
            } else {
                System.out.println("No file chosen.");
            }
        });

        AtomicReference<InputFormat> formRef = new AtomicReference<>(null);

        // Upload file button functionality
        uploadFileButton.addActionListener((ActionEvent e) -> {
            if (selectedFile != null) {
                System.out.println("Uploading file: " + selectedFile.getAbsolutePath());
                InputFormat form = Input.readTxt(selectedFile.getAbsolutePath());
                formRef.set(form);  
                InputPuzzlerPro input = form.input;

                if (input == null) {
                    JOptionPane.showMessageDialog(this, form.errorMessage,
                        "Gooner input detected!", JOptionPane.ERROR_MESSAGE);
                } else {
                    String currentDirectory = System.getProperty("user.dir");
                    File currentDirFile = new File(currentDirectory);
                    String tempString = currentDirFile +  File.separator + "temp";
                    File tempFolder = new File(tempString);

                    if (tempFolder.exists()) {
                        for (File file : tempFolder.listFiles()) {
                            if (file.isDirectory()) {
                                deleteDirectory(file);  
                            } else {
                                file.delete(); 
                            }
                        }
                        tempFolder.delete();
                    }

                    InputImageGenerator.saveInput(input);

                    if (!tempFolder.exists()) {
                        JOptionPane.showMessageDialog(this, "Input process failed, input image not created",
                        "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        File emptyBoardFile = new File(tempFolder, "emptyBoard.png");
                        if (emptyBoardFile.exists()) {
                            ImageIcon emptyBoardIcon = new ImageIcon(emptyBoardFile.getAbsolutePath());
                            JLabel emptyBoardLabel = new JLabel(emptyBoardIcon);

                            SwingUtilities.invokeLater(() -> {
                                emptyBox.removeAll();
                                emptyBox.add(emptyBoardLabel, BorderLayout.CENTER);
                                emptyBox.revalidate();
                                emptyBox.repaint();
                            });
                        }

                        for (int i = 0; i < input.piecesList.size(); i++) {
                            String pieceFileName = "piece" + (i + 1) + ".png";  
                            File pieceFile = new File(tempFolder, pieceFileName);

                            if (pieceFile.exists()) {
                                ImageIcon pieceIcon = new ImageIcon(pieceFile.getAbsolutePath());
                                JLabel pieceLabel = new JLabel(pieceIcon);
                                pieceLabel.setHorizontalAlignment(SwingConstants.CENTER);
                                pieceLabel.setVerticalAlignment(SwingConstants.CENTER);

                                int panelIndex = i;  
                                SwingUtilities.invokeLater(() -> {
                                    JPanel panelToUpdate = imagePanels[panelIndex];
                                    panelToUpdate.removeAll();
                                    panelToUpdate.setLayout(new BorderLayout());
                                    panelToUpdate.add(pieceLabel, BorderLayout.CENTER);
                                    panelToUpdate.revalidate();
                                    panelToUpdate.repaint();
                                });
                            }
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "No file selected to upload.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        fixedPanel.add(chooseFileButton);  
        fixedPanel.add(Box.createVerticalStrut(10));
        fixedPanel.add(uploadFileButton);  
        fixedPanel.add(Box.createVerticalStrut(10));
        fixedPanel.add(fileName);

        rightPanel.add(fixedPanel);  

        mainPanel.add(rightPanel, BorderLayout.EAST);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 20));

        // Bottom Section
        JPanel bottomPanel = new JPanel(new BorderLayout());  
        bottomPanel.setBackground(new Color(230, 230, 230));

        solveButton = new JButton("Solve");
        solveButton.setPreferredSize(new Dimension(500, 50));  
        solveButton.setBackground(new Color(0, 122, 255));  
        solveButton.setForeground(Color.WHITE);
        solveButton.setFocusPainted(false);   
        solveButton.setFont(new Font("Arial", Font.BOLD, 18));  

        // Solve button functionality
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedFile != null && formRef.get() != null) {
                    InputFormat form = formRef.get();
                    if (form.input == null) {
                        JOptionPane.showMessageDialog(MainFrame.this, form.errorMessage,
                        "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        new Solution(form);
                    }

                } else {
                    JOptionPane.showMessageDialog(MainFrame.this, "No file selected, can't solve",
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        bottomPanel.add(solveButton);

        JPanel marginPanelBottom = new JPanel();
        marginPanelBottom.setLayout(new BorderLayout());
        marginPanelBottom.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0)); 

        marginPanelBottom.add(bottomPanel, BorderLayout.CENTER);  

        mainPanel.add(marginPanelBottom, BorderLayout.SOUTH);

        add(mainPanel);
        setMinimumSize(new Dimension(1200, 800));
        pack();

        // Display the frame
        setVisible(true);
    }

    private void deleteDirectory(File directory) {
        if (directory.isDirectory()) {
            String[] files = directory.list();
            if (files != null) {
                for (String file : files) {
                    File currentFile = new File(directory, file);
                    if (currentFile.isDirectory()) {
                        deleteDirectory(currentFile);  
                    } else {
                        currentFile.delete();  
                    }
                }
            }
        }
        directory.delete();  
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
