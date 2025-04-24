package projlab.fungorium.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import projlab.fungorium.interfaces.WritableGameObject;

public class IOHandler {
    private static Scanner scanner = null;

    private IOHandler() {}

    public static List<String> getTokens() {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }

        String userInput = scanner.nextLine();
        return new ArrayList<>(
            Arrays.asList(userInput.split(" "))
        );
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

        // Get the outptutstring
        String output = wgo.getOutputString();

        // Append the outputstring to the file
        OutputStream os = null;
		try {
			os = new FileOutputStream(file, true);
            os.write(output.getBytes(), 0, output.length());
		} catch (IOException e) {
			Logger.printError(e.getMessage());
		} finally {
			try {
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				Logger.printError(e.getMessage());
			}
		}
    }
}
