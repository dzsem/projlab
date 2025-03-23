package projlab.fungorium.models.effects;

import projlab.fungorium.models.Insect;

public class SlowEffect implements Effect {
    /**
     * befejezi a rovar körét
     * @param insect a rovar ami megeszi ezt az effectet
     */
    //
    public void applyEffect(Insect insect) {
        insect.exhaustActions();
    }
}
