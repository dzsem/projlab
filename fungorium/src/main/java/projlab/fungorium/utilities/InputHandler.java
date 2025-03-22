package projlab.fungorium.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputHandler {
    private static Scanner scanner = null;

    private InputHandler() {}

    public static List<String> getTokens() {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }

        String userInput = scanner.nextLine();
        return new ArrayList<>(
            Arrays.asList(userInput.split(" "))
        );
    }
}
