package projlab.fungorium.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.swing.WindowConstants;

import projlab.fungorium.models.player.Insectologist;
import projlab.fungorium.models.player.Mycologist;
import projlab.fungorium.models.player.Player;
import projlab.fungorium.models.player.PlayerType;
import projlab.fungorium.views.menu.PlayerTableModel;
import projlab.fungorium.windowing.game.MainWindow;
import projlab.fungorium.windowing.menu.MenuPanel;
import projlab.fungorium.windowing.menu.MenuWindow;

public class MenuController {

    private PlayerTableModel ptm;
    private List<Player> players;

    private MenuPanel menuPanel;

    private MenuWindow window;

    public MenuController(MenuWindow window) {
        players = new ArrayList<>();
        ptm = new PlayerTableModel(players);

        this.window = window;
    }

    public void setPanel(MenuPanel menuPanel) {
        this.menuPanel = menuPanel;
    }

    public PlayerTableModel getPlayerTableModel() {
        return ptm;
    }

    public void startGame() {
        if (players.size() < 2) {
            return;
        }

        new MainWindow(new GameController());
        window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        window.dispose();
    }

    public void removePlayer() {
        int selectedRow = menuPanel.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        players.remove(selectedRow);
        ptm.removeRow(selectedRow);
    }

    public void addNewPlayer() {
        if (menuPanel.getPlayerName().isBlank()) {
            return;
        }

        switch (menuPanel.getPlayerType()) {
            case INSECTOLOGIST:
                addNewInsectologist(new Insectologist(menuPanel.getPlayerName()));
                break;
            
            case MYCOLOGIST:
                addNewMycologist(new Mycologist(menuPanel.getPlayerName()));
                break;

            default:
                break;
        }

        menuPanel.clearTextField();
    }

    private void addNewMycologist(Mycologist mycologist) {
        players.add(mycologist);
        ptm.addNewRow(PlayerType.MYCOLOGIST);
    }

    private void addNewInsectologist(Insectologist insectologist) {
        players.add(insectologist);
        ptm.addNewRow(PlayerType.INSECTOLOGIST);
    }
}
