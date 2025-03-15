package projlab.fungorium.utilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tester {
    private Tester() {}

    private static Map<String, Command> commands = new HashMap<>();

    public static void init() {
        // Commandok feltoltese
    }

    public static void run() {
        while (true) {
            List<String> inputTokens = InputHandler.getTokens();
        }
    }
}
