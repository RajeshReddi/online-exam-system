package util;

import java.io.*;
import java.util.*;

public class FileUtil {

    //  Read lines from file safely
    public static List<String> readLines(String path) {
        List<String> lines = new ArrayList<>();
        File file = new File(path);

        // Create file if not exists (avoid crash)
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs(); // create data/ folder
                file.createNewFile();
                return lines; // empty list
            }

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line.trim());
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("⚠ Error reading file: " + path);
            e.printStackTrace();
        }

        return lines;
    }

    //  Write all lines to file (overwrite)
    public static void writeLines(String path, List<String> lines) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("⚠ Error writing file: " + path);
            e.printStackTrace();
        }
    }
}


