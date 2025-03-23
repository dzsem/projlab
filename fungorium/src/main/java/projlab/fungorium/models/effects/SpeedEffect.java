package projlab.fungorium.models.effects;

import projlab.fungorium.models.Insect;

public class SpeedEffect implements Effect {
    /**
     * újraezdi a rovar körét
     * @param insect a rovar ami megeszi ezt az effectet
     */
    public void applyEffect(Insect insect) {
        insect.refreshActions();
    }
}
