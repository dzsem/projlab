package projlab.fungorium.models.effects;

import projlab.fungorium.models.Insect;

public class SpeedEffect implements Effect {
    //restart the insects turn
    public void applyEffect(Insect insect) {
        insect.refreshActions();
    }
}
