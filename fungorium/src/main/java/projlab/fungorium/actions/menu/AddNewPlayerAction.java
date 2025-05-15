package projlab.fungorium.actions.menu;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import projlab.fungorium.controllers.MenuController;

public class AddNewPlayerAction extends AbstractAction {

    private MenuController controller;

    public AddNewPlayerAction(MenuController menuController) {
        putValue(NAME, "Add New Player");
        controller = menuController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        controller.addNewPlayer();
    }
}
