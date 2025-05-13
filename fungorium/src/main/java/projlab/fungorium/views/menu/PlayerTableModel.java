package projlab.fungorium.views.menu;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import projlab.fungorium.models.player.Insectologist;
import projlab.fungorium.models.player.Mycologist;
import projlab.fungorium.models.player.Player;
import projlab.fungorium.models.player.PlayerType;

public class PlayerTableModel extends AbstractTableModel {
    private List<Player> players; // Reference to controllers players
    private List<PlayerType> playerTypes;

    public PlayerTableModel(List<Player> players) {
        this.players = players;
        playerTypes = new ArrayList<>();
    }

    public void addNewRow(PlayerType playerType) {
        playerTypes.add(playerType);
        fireTableDataChanged();
    }

    public void removeRow(int rowIndex) {
        playerTypes.remove(rowIndex);
        fireTableDataChanged();
    }

    public List<Insectologist> getInsectologists() {
        List<Insectologist> result = new ArrayList<>();

        for (int idx = 0; idx < players.size(); idx++)
            if (playerTypes.get(idx) == PlayerType.INSECTOLOGIST)
                result.add((Insectologist) players.get(idx));

        return result;
    }

    public List<Mycologist> getMycologists() {
        List<Mycologist> result = new ArrayList<>();

        for (int idx = 0; idx < players.size(); idx++)
            if (playerTypes.get(idx) == PlayerType.MYCOLOGIST)
                result.add((Mycologist) players.get(idx));

        return result;
    }

    @Override
    public String getColumnName(int column) {
        if (column == 0) {
            return "Player Name";
        }

        return "Type";
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public int getRowCount() {
        return players.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return players.get(rowIndex).getName();
        }

        if (columnIndex == 1) {
            return playerTypes.get(rowIndex).name();
        }

        return null;
    }
}
