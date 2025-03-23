package projlab.fungorium.utilities;

import projlab.fungorium.interfaces.PrintableState;

public class Logger {
    private Logger() {}

    public static void print(String returnValue, String method, String... parameters) {
        System.out.print(method + "(");

        if (parameters != null) {
            for (int i = 0; i < parameters.length; i++) {
                System.out.print(parameters[i]);
                if (i != parameters.length - 1) {
                    System.out.print(", ");
                }
            }
        }
        
        System.out.println( ") = " + returnValue);
    }

    public static void printState(PrintableState ps) {
        System.out.println(ps.getStateString());
    }
}
