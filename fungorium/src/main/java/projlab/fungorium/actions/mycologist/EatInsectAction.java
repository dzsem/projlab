package projlab.fungorium.actions.mycologist;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import projlab.fungorium.controllers.GameController;
import projlab.fungorium.models.MushroomThread;

public class EatInsectAction extends AbstractAction {
    
    private GameController controller;

    public EatInsectAction(GameController controller) {
        super();

        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MushroomThread mt = controller.getSelectedThread();
        try {
            mt.eat();
        } catch (Exception ex) {
            controller.showError(ex);
        }
    }
}
