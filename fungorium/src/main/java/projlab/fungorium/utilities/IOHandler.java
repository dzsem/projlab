package projlab.fungorium.utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import projlab.fungorium.interfaces.WritableGameObject;

public class IOHandler {
    private static Scanner scanner = null;

    private IOHandler() {
    }

    public static List<String> getTokens() {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }

        String userInput = scanner.nextLine();
        return new ArrayList<>(
                Arrays.asList(userInput.split(" ")));
    }

    public static void save(File file, WritableGameObject wgo) {
        // Create file if needed
        try {
            file.createNewFile();
        } catch (IOException e) {
            Logger.printError(e.getMessage());
        }

        // Check if 'file' is a file
        if (!file.isFile()) {
            Logger.printError(file.getName() + " is not a file");
        }

        // Get the outputstring
        String output = wgo.getOutputString();

        // Append the outputstring to the file
        FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter pw = null;

        try {
            fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);

            pw.println(output);
        } catch (IOException e) {
            Logger.printError(e.getMessage());
        } finally {
            try {
                if (pw != null) {
                    pw.close();
                }

                if (bw != null) {
                    bw.close();
                }

                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e) {
                Logger.printError(e.getMessage());
            }
        }
    }
}
