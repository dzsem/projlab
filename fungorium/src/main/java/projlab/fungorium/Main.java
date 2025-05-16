package projlab.fungorium;

import java.util.ArrayList;
import java.util.List;

import projlab.fungorium.controllers.GameController;
import projlab.fungorium.models.player.Insectologist;
import projlab.fungorium.models.player.Mycologist;
import projlab.fungorium.windowing.game.MainWindow;
import projlab.fungorium.windowing.menu.MenuWindow;

public class Main {
    public static void main(String[] args) {
        // var menuWindow = new MenuWindow();

        var gameController = GameController.getInstance();
        var insects = new ArrayList<Insectologist>();
        insects.add(new Insectologist("Insectologist 1"));
        insects.add(new Insectologist("Insectologist 2"));

        var myce = new ArrayList<Mycologist>();
        myce.add(new Mycologist("Mycologist 1"));
        myce.add(new Mycologist("Mycologist 2"));
        gameController.setPlayers(insects, myce);
        var mainWindow = new MainWindow(gameController);
    }
}