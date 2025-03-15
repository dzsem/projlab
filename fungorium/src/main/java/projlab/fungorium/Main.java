package projlab.fungorium;

import projlab.fungorium.utilities.Logger;



public class Main {
    public static void main(String[] args) {
        Logger.print(String.valueOf(add(1, 3)), "Main.add", String.valueOf(1), String.valueOf(3));

    }

    private static int add(int a, int b) {
        return a + b;
    }
}