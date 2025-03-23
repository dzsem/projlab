package projlab.fungorium.models.effects;

import projlab.fungorium.models.Insect;

public class SlowEffect implements Effect {
    /**
     * ends the insects turn
     * @param insect is the Insect which eats the spore
     */
    //
    public void applyEffect(Insect insect) {
        insect.exhaustActions();
    }
}
