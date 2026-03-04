package realestate.Parser;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.util.Arrays;

public class Parser {

    public void printDatabase(String filePath) {
        // The 'try-with-resources' ensures the file closes automatically
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] nextLine;
            
            System.out.println(">>> LOADING DATABASE: " + filePath);
            System.out.println("--------------------------------------------------------------------------------");

            // readNext() pulls one row at a time as a String array
            while ((nextLine = reader.readNext()) != null) {
                // Use printf to create clean, aligned columns in the terminal
                // %-12s means "string, left-aligned, 12 characters wide"
                System.out.printf("%-12s | %-10s | %-10s | %-15s | %-10s%n", 
                                  nextLine[0], nextLine[1], nextLine[2], nextLine[4], nextLine[5]);
            }
            
            System.out.println("--------------------------------------------------------------------------------");

        } catch (Exception e) {
            System.err.println("Error: Could not read the file. Check if " + filePath + " is in the project root.");
        }
    }
}