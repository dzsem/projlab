package projlab.fungorium.actions.mycologist;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import projlab.fungorium.controllers.GameController;
import projlab.fungorium.controllers.MycologistController;
import projlab.fungorium.models.Tecton;

public class GrowBodyAction extends AbstractAction {
    
    private GameController controller;
    private MycologistController mycologistcontroller;

    public GrowBodyAction(GameController controller, MycologistController mycologistcontroller) {
        this.controller = controller;
        this.mycologistcontroller = mycologistcontroller;
    } 

    @Override
    public void actionPerformed(ActionEvent e) {
        Tecton t = controller.getSelectedTecton();
        int id = mycologistcontroller.getMushroomID();
        try {
            t.growBody(id);
        } catch (Exception ex) {
            controller.showError(ex);
        }
    }
}
