package projlab.fungorium.actions.mycologist;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import projlab.fungorium.controllers.GameController;
import projlab.fungorium.controllers.MycologistController;
import projlab.fungorium.models.Tecton;

public class GrowBodyAction extends AbstractAction {
    
    private GameController controller;
    private MycologistController mycologist;

    public GrowBodyAction(GameController controller, MycologistController mycologist) {
        super();
        
        this.controller = controller;
        this.mycologist = mycologist;
    } 

    @Override
    public void actionPerformed(ActionEvent e) {
        Tecton t = controller.getSelectedTecton();
        int id = mycologist.getMushroomID();
        try {
            t.growBody(id);
        } catch (Exception ex) {
            controller.showError(ex);
        }
    }
}
