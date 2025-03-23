package projlab.fungorium.utilities;

import java.util.ArrayList;
import java.util.List;
import projlab.fungorium.tests.InsectTester;
import projlab.fungorium.tests.MushroomTester;
import projlab.fungorium.tests.TectonTester;

public class Skeleton {
    private Skeleton() {
    }

    private static boolean shouldStop = true;

    private static List<Command> commands = new ArrayList<>();
    private static List<String> commandDescriptions = new ArrayList<>();

    private static void addCommand(Command command, String desc) {
        commands.add(command);
        commandDescriptions.add(desc);
    }

    public static void init() {
        // Commandok feltoltese
        addCommand(Skeleton::stop, "stop"); // 0

        addCommand(InsectTester::testEatMushroomSpore1, "testEatMushroomSpore1");
        addCommand(InsectTester::testEatMushroomSpore2, "testEatMushroomSpore2");
        addCommand(InsectTester::testMoveToTecton1, "testMoveToTecton1");
        addCommand(InsectTester::testMoveToTecton2, "testMoveToTecton2");
        addCommand(InsectTester::testMoveToTecton3, "testMoveToTecton3");
        addCommand(InsectTester::testMoveToTecton4, "testMoveToTecton4");

        addCommand(MushroomTester::test_advancedMushroomDstibuteSpores, "test_advancedMushroomDstibuteSpores");
        addCommand(MushroomTester::test_connectingTectinFailCut, "test_connectingTectinFailCut");
        addCommand(MushroomTester::test_connectingTectonFailSprout, "test_connectingTectonFailSprout");
        addCommand(MushroomTester::test_connectingTectonSuccess, "test_connectingTectonSuccess");
        addCommand(MushroomTester::test_lastSpore, "test_lastSpore");
        addCommand(MushroomTester::test_threadDie, "test_threadDie");
        addCommand(MushroomTester::test_threadDissolve, "test_threadDissolve");
        addCommand(MushroomTester::test_threadEvolve, "test_threadEvolve");
        addCommand(MushroomTester::test_threadGrowth, "test_threadGrowth");
        addCommand(MushroomTester::test_normalBodyDistibuteSpores, "test_normalBodyDistibuteSpores");
        addCommand(MushroomTester::test_cutConncectionOneBody, "test_cutConncectionOneBody");
        addCommand(MushroomTester::test_cutConnectionTwoBodies, "test_cutConnectionTwoBodies");

        addCommand(TectonTester::InfertileTectonGrowBodyFail, "InfertileTectonGrowBodyFail");
        addCommand(TectonTester::SingleThreadTectonGrowThreadFail, "SingleThreadTectonGrowThreadFail");
        addCommand(TectonTester::SingleThreadTectonGrowThreadSuccess, "SingleThreadTectonGrowThreadSuccess");
        addCommand(TectonTester::TectonGrowBodyBodyFail, "TectonGrowBodyBodyFail");
        addCommand(TectonTester::TectonGrowBodySporeFail, "TectonGrowBodySporeFail");
        addCommand(TectonTester::TectonGrowBodySuccess, "TectonGrowBodySuccess");
        addCommand(TectonTester::TectonGrowBodyThreadFail, "TectonGrowBodyThreadFail");

        addCommand(TectonTester::tectonKillThread, "tectonKillThread");
        addCommand(TectonTester::tectonSplit, "tectonSplit");
        addCommand(TectonTester::threadKillingTectonKillThread, "threadKillingTectonKillThread");
    }

    public static void run() {
        shouldStop = false;

        while (true) {
            System.out.print("> ");
            List<String> inputTokens = InputHandler.getTokens();

            String input = inputTokens.get(0);

            if (input.toLowerCase().startsWith("help")) {
                printHelp();
                continue;
            }

            int commandId = Integer.parseInt(input);

            if (commandId >= commands.size() || commandId < 0) {
                Logger.printError(
                        "Invalid command. Enter a number between 1 and " +
                                (commands.size() - 1) + ", or 0 to exit.");
                Logger.printError("Type \"help\" to get help.");

                continue;
            }

            Command command = commands.get(commandId);

            if (command != null) {
                command.execute();
            }

            if (shouldStop) {
                break;
            }
        }
    }

    private static void printHelp() {
        System.out.println("Enter a number between 1 and " + (commands.size() - 1) + ", or 0 to exit.");
        System.out.println("Tests:");

        for (int i = 0; i < commands.size(); i++) {
            System.out.println(" " + (i + 1) + ")\t" + commandDescriptions.get(i));
        }
    }

    private static void stop() {
        shouldStop = true;
    }
}
