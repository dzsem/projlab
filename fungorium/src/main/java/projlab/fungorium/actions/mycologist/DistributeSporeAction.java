package projlab.fungorium.actions.mycologist;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import projlab.fungorium.controllers.GameController;
import projlab.fungorium.controllers.MycologistController;
import projlab.fungorium.models.MushroomBody;

public class DistributeSporeAction extends AbstractAction {
    
    private GameController controller;
    private MycologistController mycologist;

    public DistributeSporeAction(GameController controller, MycologistController mycologist) {
        super();

        putValue(NAME, "Distribute Spore");

        this.controller = controller;
        this.mycologist = mycologist;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            MushroomBody mb = mycologist.getSelectedBody();
            mb.distributeSpores();
        } catch (Exception ex) {
            controller.showError(ex);
        }
    }
}
