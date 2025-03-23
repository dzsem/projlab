package projlab.fungorium.models.effects;

import projlab.fungorium.interfaces.PrintableState;
import projlab.fungorium.models.Insect;

public class BlockEffect implements Effect, PrintableState {
    /**
     * mikor a rovar megeszi az effectet, elindul egy számláló és letiltja a fonál vágást
     * @param insect a rovar ami megeszi ezt az effectet
     */
    public void applyEffect(Insect insect) {
        insect.setCounter();
        insect.setCanCut(false);
    }


}
