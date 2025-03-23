package projlab.fungorium.models.effects;

import projlab.fungorium.models.Insect;

public class BlockEffect implements Effect {
    //when the insects eat this effect, a counter is started and it cannot cut threads
    public void applyEffect(Insect insect) {
        insect.setCounter();
        insect.setCanCut(false);
    }
}
