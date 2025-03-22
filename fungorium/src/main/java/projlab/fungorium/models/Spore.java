package projlab.fungorium.models;

import projlab.fungorium.models.effects.Effect;
import projlab.fungorium.models.effects.NoEffect;

class Spore {
    protected Effect generateEffect(){return new NoEffect();}
    public void applyEffect(Insect insect) {
        generateEffect().applyEffect(insect);
    }
}