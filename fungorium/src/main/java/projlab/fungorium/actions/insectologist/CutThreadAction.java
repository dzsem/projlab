package projlab.fungorium.actions.insectologist;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import projlab.fungorium.controllers.GameController;
import projlab.fungorium.controllers.InsectologistController;

public class CutThreadAction extends AbstractAction {

    private GameController controller;
    private InsectologistController insectologist;

    @Override
    public void actionPerformed(ActionEvent e) {
        Insect i = insectologist.getSelectedInsect();
    }

}
