package projlab.fungorium.actions.game;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import projlab.fungorium.controllers.GameController;
import projlab.fungorium.models.Game;
import projlab.fungorium.models.GameObject;
import projlab.fungorium.models.Insect;
import projlab.fungorium.models.MushroomBody;
import projlab.fungorium.models.player.Insectologist;
import projlab.fungorium.models.player.Mycologist;
import projlab.fungorium.models.player.PlayerType;
import projlab.fungorium.views.gamecomponents.GameComponentView;

public class PassAction extends AbstractAction {
    private GameController controller;

    public PassAction(GameController controller) {
        super();
        putValue(NAME, "Pass");

        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (controller.getActiveType() == PlayerType.INSECTOLOGIST) {
            int points=0;

            for (var insect : Game.getInstance().getRegistry().getInsects()) {
                if(insect.getInsectologistID()==controller.getInsectologistIdx()){
                    points+=insect.getSporesEaten();

                }
            }

            controller.getCurrentPlayer().setPoints(points);


            controller.setInsectologistIdx(controller.getInsectologistIdx() + 1);
            // ha már a másik csapaton végig ment akkor nem vált
            if (controller.getMycologistIdx() != controller.getMycologistsSize()) {
                controller.setActiveType(PlayerType.MYCOLOGIST);
            }
        } else {

            int points=0;

            for (var mushroomBody : Game.getInstance().getRegistry().getMushroomBodies()) {
                if (mushroomBody.getMushroomID() == controller.getMycologistIdx()) {
                    points++;
                }
            }


            controller.getCurrentPlayer().setPoints(points);

            controller.setMycologistIdx(controller.getMycologistIdx() + 1);

            // ha már a másik csapaton végig ment akkor nem vált
            if (controller.getInsectologistIdx() != controller.getInsectologistsSize()) {
                controller.setActiveType(PlayerType.INSECTOLOGIST);
            }
        }

        if (controller.checkIfLastActive()) {
            controller.nextRound();
            controller.decraseRoundsRemaining();
            if (controller.getRoundsRemaining()==0) {
                Mycologist winnerM=controller.getMycologists().get(0);
                for (Mycologist mycologist : controller.getMycologists()) {
                    if(mycologist.getPoints()>winnerM.getPoints()) {
                        winnerM = mycologist;
                    }
                }
                controller.setWinnerMycologist(winnerM);

                Insectologist winnerI=controller.getInsectologists().get(0);
                for(Insectologist insectologist : controller.getInsectologists()) {
                    if(insectologist.getPoints()>winnerI.getPoints()) {
                        winnerI = insectologist;
                    }
                }
                controller.setWinnerInsectologist(winnerI);

                controller.endgame();
            }
            controller.setInsectologistIdx(0);
            controller.setMycologistIdx(0);

            controller.setActiveType(controller.getActiveType() == PlayerType.INSECTOLOGIST
                    ? PlayerType.MYCOLOGIST
                    : PlayerType.INSECTOLOGIST);
        }

        controller.onNextRound();

        controller.redraw();
    }

}
