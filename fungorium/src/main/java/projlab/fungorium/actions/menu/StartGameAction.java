package projlab.fungorium.actions.menu;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import projlab.fungorium.controllers.MenuController;

public class StartGameAction extends AbstractAction {

    private MenuController controller;

    public StartGameAction(MenuController controller) {
        putValue(NAME, "Start Game");

        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        controller.startGame();
    }

}
