package projlab.fungorium;

import java.util.List;

import projlab.fungorium.controllers.GameController;
import projlab.fungorium.models.player.Insectologist;
import projlab.fungorium.models.player.Mycologist;
import projlab.fungorium.windowing.game.MainWindow;
import projlab.fungorium.windowing.menu.MenuWindow;

public class Main {
    private static final boolean isDev = true;

    public static void main(String[] args) {
        if (!isDev) {
            var menuWindow = new MenuWindow();
        } else {
            // FOR TESTING ONLY
            GameController gController = new GameController(
                List.of(
                    new Insectologist("Insectologist 1"),
                    new Insectologist("Insectologist 2")),
                List.of(
                    new Mycologist("Mycologist 1"),
                    new Mycologist("Mycologist 2")));
    
            new MainWindow(gController);
        }



    }
}