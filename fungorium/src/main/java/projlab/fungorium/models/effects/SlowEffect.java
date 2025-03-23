package projlab.fungorium.models.effects;

import projlab.fungorium.models.Insect;

public class SlowEffect implements Effect {
    //ends the insects turn
    public void applyEffect(Insect insect) {
        insect.exhaustActions();
    }
}
