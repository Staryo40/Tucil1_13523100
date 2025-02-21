# Tugas Kecil 1 Strategi Algoritma 2025     
## IQ Puzzler Pro with Brute Force Algorithm
IQ Puzzler Pro is a board game that is produced by Smart Games. The goal of this game is to fill the board with the available pieces with not pieces remaining.  
There are two main components to this game:  
1. Board: This is where the pieces are fit into
2. Pieces: These pieces have a variety of shapes, the player needs to find the combination of position, rotation, mirror of the pieces to find the solution  
In this program, the solution of this puzzle is solved with the Brute Force Algorithm, or by trying every combination possible until a solution is found, or every combination is tried and no solution is found.
<p align="center">
<img src="https://drive.google.com/uc?id=1bERJlzYuZ2FV9wE5jER64L1RGqww0fmI" alt="Example input interface" width="500"/>
<img src="https://drive.google.com/uc?id=1NxiX1kVChxGe27yWY1fGYa6h878NSb5S" alt="Example output interface" width="500"/>
</p>
## Program Requirements
1. Java Runtime Environment (JRE) 
2. Java Development Kit (JDK) (to compile the program)
## Compiling the Program
Navigate to the directory that contains bin, doc, src, and test  
Then run the line below to compile all .java files to .class that are placed in bin folder
```bash
javac -d bin $(find src -name "*.java")
```
After running line above, run the line below to create .jar
```bash
jar cfm bin/IQPuzzlerProBruteForce.jar bin/META-INF/MANIFEST.MF -C bin .
```
## Running the Program
After compiling the program, run the line below to run the program
```bash
java -jar bin/IQPuzzlerProBruteForce.jar
```
## About The Creator
Nama: Aryo Wisanggeni  
NIM: 13523100  
Kelas: K02


