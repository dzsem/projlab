package projlab.TestProcessor;

public class Main {
    private static void PrintHelp() {
        System.err.println("dzsem TestProcessor 1.0");
        System.err.println("Usage:");
        System.err.println("\tjava -jar TestProcessor.jar <assertfile> <outputfile>");
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            PrintHelp();
            return;
        }

        Tester.Assert(args[0], args[1]);
    }
}