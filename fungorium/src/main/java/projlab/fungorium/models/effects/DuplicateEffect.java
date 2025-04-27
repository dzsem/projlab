package projlab.fungorium.models.effects;

import projlab.fungorium.interfaces.PrintableState;
import projlab.fungorium.models.Insect;

public class DuplicateEffect implements Effect, PrintableState {
    @Override
    public void applyEffect(Insect insect) {
        Insect newInsect=new Insect(insect.getTecton());
        //TODO kellene egy
    }

    @Override
    public String getStateString() {
        return "This is a block spore";
    }
}
