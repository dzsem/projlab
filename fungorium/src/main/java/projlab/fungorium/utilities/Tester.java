package projlab.fungorium.utilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import projlab.fungorium.models.MushroomThread;

public class Tester {

    private Tester() {}
    private static boolean shouldStop = true;

    private static Map<String, Command> commands = new HashMap<>();

    public static void init() {
        // Commandok feltoltese
        commands.put("stop", Tester::stop);
        commands.put("grow_thread", Tester::testMushroomThreadGrowth);
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

    private static void testMushroomThreadGrowth(List<String> args) {
        MushroomThread thread = new MushroomThread();
        thread.grow();
        Logger.print("void", "MushroomThread.grow");

    }

    private static void stop(List<String> args) {
        shouldStop = true;
    }
}
