package projlab.fungorium.models.effects;

import projlab.fungorium.models.Insect;

public class BlockEffect implements Effect {

    public void applyEffect(Insect insect) {
        insect.setCounter();
        insect.setCanCut(false);
    }
}
