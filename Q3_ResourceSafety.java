import java.io.*;

public class Q3_ResourceSafety {

    // Version 1: manual close using finally
    static void manualReport(String[] files, String output)
            throws IOException {
        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(output));
            for (String file : files) {
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new FileReader(file));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        writer.write(line);
                        writer.newLine();
                    }
                } finally {
                    if (reader != null)
                        reader.close();
                }
            }
        } finally {
            if (writer != null)
                writer.close();
        }
    }

    // Version 2: try-with-resources
    static void safeReport(String[] files, String output)
            throws IOException {
        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(output))) {
            for (String file : files) {
                try (BufferedReader reader =
                             new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        writer.write(line);
                        writer.newLine();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        String[] files = {"store1.txt", "store2.txt"};

        try {
            // Run either method:
            manualReport(files, "manual_report.txt");
            safeReport(files, "safe_report.txt");
            System.out.println("Both reports created successfully");
        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        }
    }
}

/*
Create store1.txt and store2.txt in the same folder.
Put a few sales lines in each file, then run the program.
*/