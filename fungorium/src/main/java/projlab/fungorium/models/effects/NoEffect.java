package projlab.fungorium.models.effects;

import projlab.fungorium.models.Insect;

public class NoEffect implements Effect {
    /**
     * nem csinál semmit
     * @param insect a rovar ami megeszi ezt az effectet
     */
    public void applyEffect(Insect insect) {}
}
