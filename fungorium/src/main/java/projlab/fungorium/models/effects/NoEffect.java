package projlab.fungorium.models.effects;

import projlab.fungorium.models.Insect;

public class NoEffect implements Effect {
    /**
     * it does nothing
     * @param insect is the Insect which eats the spore
     */
    public void applyEffect(Insect insect) {}
}
