package projlab.fungorium.models.effects;

import projlab.fungorium.interfaces.PrintableState;
import projlab.fungorium.models.Insect;

public class SlowEffect implements Effect, PrintableState {
    /**
     * befejezi a rovar körét
     * @param insect a rovar ami megeszi ezt az effectet
     */
    //
    public void applyEffect(Insect insect) {
        insect.exhaustActions();
    }

    @Override
    public String getStateString() {
        return "This is a slow spore";
    }

    @Override
    public String getDescription() {
        return "";
    }
}
