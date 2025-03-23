package projlab.fungorium.utilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import projlab.fungorium.models.MushroomThread;

public class Skeleton {

    private Skeleton() {}
    private static boolean shouldStop = true;

    private static Map<String, Command> commands = new HashMap<>();

    public static void init() {
        // Commandok feltoltese
        commands.put("stop", Skeleton::stop);
    }

    public static void run() {
        shouldStop = false;

        while (true) {
            List<String> inputTokens = InputHandler.getTokens();
            List<String> args = null;
            if (inputTokens.size() > 1) {
                args = inputTokens.subList(1, inputTokens.size() - 1); 
            }

            Command command = commands.get(inputTokens.get(0));

            if (command != null) {
                command.execute(args);
            }

            if (shouldStop) {
                break;
            }
        }
    }

    private static void stop(List<String> args) {
        shouldStop = true;
    }
}
