package projlab.fungorium.models.effects;

import projlab.fungorium.interfaces.PrintableState;
import projlab.fungorium.models.Insect;

public class DuplicateEffect implements Effect, PrintableState {

    /**
     * mikor a rovar megeszi a spórát, létrejön belőle egy új rovar, ugyanazzal a Insectologisttal
     * @param insect
     */
    @Override
    public void applyEffect(Insect insect) {
        new Insect(insect.getInsectologistID(), insect.getTecton());
    }

    @Override
    public String getStateString() {
        return "This is a block spore";
    }

    @Override
    public String getDescription() {
        return "Effect: A new insect is created";
    }
}
