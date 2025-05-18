package projlab.fungorium.actions.mycologist;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import projlab.fungorium.controllers.GameController;
import projlab.fungorium.models.MushroomThread;

public class EatInsectAction extends AbstractAction {
    
    private GameController controller;

    public EatInsectAction(GameController controller) {
        super();

        putValue(NAME, "Eat Insect");

        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MushroomThread mt = controller.getSelectedThread();
        try {

            if (mt.getMushroomID() != controller.getMycologistId()) {
                throw new Exception("Can't controll someone elses thread");
            }
            
            mt.eat();

            controller.redraw();
        } catch (Exception ex) {
            controller.showError(ex);
        }
    }
}
