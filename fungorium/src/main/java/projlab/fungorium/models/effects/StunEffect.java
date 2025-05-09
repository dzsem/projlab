package projlab.fungorium.models.effects;

import projlab.fungorium.interfaces.PrintableState;
import projlab.fungorium.models.Insect;

public class StunEffect implements Effect, PrintableState {
    /**
     * befejezi az rovar körét és letiltja a következőt is
     * @param insect a rovar ami megeszi ezt az effectet
     */
    public void applyEffect(Insect insect) {
        insect.exhaustActions();
        insect.setCanMove(false);
    }

    @Override
    public String getStateString() {
        return "This is a stun spore";
    }
}
