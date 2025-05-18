package projlab.fungorium.actions.insectologist;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import projlab.fungorium.controllers.GameController;
import projlab.fungorium.controllers.InsectologistController;
import projlab.fungorium.models.Insect;


public class EatSporeAction extends AbstractAction {
    private GameController controller;
    private InsectologistController insectologist;

    public EatSporeAction(GameController controller, InsectologistController insectologist) {
        super();

        putValue(NAME, "Eat Spore");

        this.controller = controller;
        this.insectologist = insectologist;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Insect i = insectologist.getSelectedInsect();

            if (i.getInsectologistID() != controller.getInsectologistId()) {
                throw new Exception("Can't controll someone elses insect");
            }
            
            i.eatMushroomSpore();

            controller.redraw();
        } catch (Exception ex) {
            controller.showError(ex);
        }
   }

}
