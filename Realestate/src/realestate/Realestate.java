package realestate;

import realestate.Parser.Parser;

public class Realestate {

    public static void main(String[] args) {
        Parser myParser = new Parser();
        
        // Match the filename from your uploaded screenshot
        String fileName = "database.csv"; 
        
        myParser.printDatabase(fileName);
    }
}