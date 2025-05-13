package projlab.fungorium.actions.mycologist;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import projlab.fungorium.controllers.GameController;
import projlab.fungorium.models.MushroomBody;

public class DistributeSporeAction extends AbstractAction {
    
    private GameController controller;

    public DistributeSporeAction(GameController controller) {
        super();

        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MushroomBody mb = controller.getSelectedBody();
        try {
            mb.distributeSpores();
        } catch (Exception ex) {
            controller.showError(ex);
        }
    }
}
