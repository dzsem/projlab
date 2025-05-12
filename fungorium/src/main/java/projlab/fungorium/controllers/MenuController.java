package projlab.fungorium.controllers;

import java.util.ArrayList;
import java.util.List;

import projlab.fungorium.models.player.Insectologist;
import projlab.fungorium.models.player.Mycologist;
import projlab.fungorium.models.player.Player;
import projlab.fungorium.models.player.PlayerType;
import projlab.fungorium.views.menu.PlayerTableModel;
import projlab.fungorium.windowing.menu.MainMenuPanel;

public class MenuController {

    private PlayerTableModel ptm;
    private List<Player> players;

    private MainMenuPanel menuPanel;

    public MenuController() {
        players = new ArrayList<>();
        ptm = new PlayerTableModel(players);
    }

    public void setPanel(MainMenuPanel menuPanel) {
        this.menuPanel = menuPanel;
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

    public void removePlayer() {
        int selectedRow = menuPanel.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        players.remove(selectedRow);
        ptm.removeRow(selectedRow);
    }

    private void addNewMycologist(Mycologist mycologist) {
        players.add(mycologist);
        ptm.addNewRow(PlayerType.MYCOLOGIST);
    }

    private void addNewInsectologist(Insectologist insectologist) {
        players.add(insectologist);
        ptm.addNewRow(PlayerType.INSECTOLOGIST);
    }

    public PlayerTableModel getPlayerTableModel() {
        return ptm;
    }

}
