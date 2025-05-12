package projlab.fungorium.actions.menu;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import projlab.fungorium.controllers.MenuController;

public class RemovePlayerAction extends AbstractAction {

    private MenuController controller;

    public RemovePlayerAction(MenuController controller) {
        putValue(NAME, "Delete");
        
        this.controller = controller;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        controller.removePlayer();
    }
    
}
