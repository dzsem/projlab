package projlab.fungorium.models.effects;

import projlab.fungorium.interfaces.PrintableState;
import projlab.fungorium.models.Insect;

public class SpeedEffect implements Effect, PrintableState {
    /**
     * újraezdi a rovar körét
     * @param insect a rovar ami megeszi ezt az effectet
     */
    public void applyEffect(Insect insect) {
        insect.refreshActions();
    }
    @Override
    public String getStateString() {
        return "This is a speed spore";
    }
}
