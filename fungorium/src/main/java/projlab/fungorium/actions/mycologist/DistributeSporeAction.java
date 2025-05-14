package projlab.fungorium.actions.mycologist;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import projlab.fungorium.controllers.GameController;
import projlab.fungorium.controllers.MycologistController;
import projlab.fungorium.models.MushroomBody;

public class DistributeSporeAction extends AbstractAction {
    
    private GameController controller;
    private MycologistController mycologistcontroller;

    public DistributeSporeAction(GameController controller, MycologistController mycologistcontroller) {
        super();

        this.controller = controller;
        this.mycologistcontroller = mycologistcontroller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            MushroomBody mb = mycologistcontroller.getSelectedBody();
            mb.distributeSpores();
        } catch (Exception ex) {
            controller.showError(ex);
        }
    }
}
