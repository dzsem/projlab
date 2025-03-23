package projlab.fungorium.models.effects;

import projlab.fungorium.models.Insect;

public class StunEffect implements Effect {
    //ends the insects turn and disable it for the next turn
    public void applyEffect(Insect insect) {
        insect.exhaustActions();
        insect.setCanMove(false);
    }
}
