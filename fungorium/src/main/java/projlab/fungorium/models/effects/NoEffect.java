package projlab.fungorium.models.effects;

import projlab.fungorium.interfaces.PrintableState;
import projlab.fungorium.models.Insect;


public class NoEffect implements Effect, PrintableState {
    /**
     * nem csinál semmit
     * @param insect a rovar ami megeszi ezt az effectet
     */
    public void applyEffect(Insect insect) {}

    @Override
    public String getStateString() {
        return "This is a nothing";
    }

    @Override
    public String getDescription() {
        return "Effect: This effect does nothing";
    }

}
