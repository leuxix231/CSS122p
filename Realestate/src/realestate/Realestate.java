package realestate;

import realestate.Parser.Parser;
import realestate.Presenter.Property;
import realestate.Presenter.PropertyData;
import Report.ReportGenerator;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Realestate {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Parser parser           = new Parser();
            PropertyData presenter  = new PropertyData();
            ReportGenerator report  = new ReportGenerator();

            List<String[]> allData = parser.loadRawData("database.csv");
            if (allData.isEmpty()) {
                System.out.println("Error: database.csv is missing or empty.");
                return;
            }

            // ── Convert CSV rows into Property objects ───────────────────────────
            List<Property> properties = new ArrayList<>();
            for (String[] row : allData) {
                try {
                    // Skip header row
                    if (row[0].equalsIgnoreCase("Unit Code")) continue;

                    // Clean price: remove commas e.g. "1,300,00" → "130000"
                    String cleanPrice = row[4].replace(",", "");

                    Property p = new Property.Builder()
                            .setUnitCode(row[0])
                            .setBlock(row[1])                        // String block (A/B/C/D/E)
                            .setLot(Integer.parseInt(row[2].trim()))
                            .setArea(Integer.parseInt(row[3].trim()))
                            .setPrice(Double.parseDouble(cleanPrice))
                            .setStatus(row[5].trim())
                            .build();
                    properties.add(p);
                } catch (Exception e) {
                    System.out.println("Skipping invalid row: " + String.join(",", row));
                }
            }

            // ── Main menu loop ───────────────────────────────────────────────────
            while (true) {
                System.out.println("\n--- REAL ESTATE PROPERTY SYSTEM ---");
                System.out.println("1. Display all properties");
                System.out.println("2. Filter by block");
                System.out.println("3. Filter by size");
                System.out.println("4. Filter by status");
                System.out.println("5. Generate full property report");
                System.out.println("6. Generate report per block");
                System.out.println("7. Exit");
                System.out.print("Choose an option: ");
                String choice = scanner.nextLine();

                List<Property> result = new ArrayList<>();

                switch (choice) {
                    case "1":
                        presenter.displayLots(properties);
                        break;

                    case "2":
                        System.out.print("Enter block (A/B/C/D/E): ");
                        String block = scanner.nextLine().trim();
                        result = presenter.filterByBlock(properties, block);
                        presenter.displayLots(result);
                        break;

                    case "3":
                        System.out.print("Enter lot size (sqm): ");
                        int size = Integer.parseInt(scanner.nextLine().trim());
                        result = presenter.filterBySize(properties, size);
                        presenter.displayLots(result);
                        break;

                    case "4":
                        System.out.print("Enter status (Available/Sold/Reserved): ");
                        String status = scanner.nextLine().trim();
                        result = presenter.filterByStatus(properties, status);
                        presenter.displayLots(result);
                        break;

                    case "5":
                        report.generateFullReport(properties);
                        break;

                    case "6":
                        report.generateBlockReport(properties);
                        break;

                    case "7":
                        System.out.println("Exiting system...");
                        return;

                    default:
                        System.out.println("Invalid choice, try again.");
                }
            }
        }
    }
}