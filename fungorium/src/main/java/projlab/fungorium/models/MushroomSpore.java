package projlab.fungorium.models;

import projlab.fungorium.models.effects.*;

import java.util.Random;

class MushroomSpore {
    //when you make a spore it adds itself to the tecton it is on
    public MushroomSpore(Tecton tecton) {
        tecton.addSpore(this);
    }
    //returns a random effect, which will activate when an insect eats the spore
    protected Effect generateEffect(){
        Random rand = new Random();
        return switch (rand.nextInt(5)) {
            case 1 -> new BlockEffect();
            case 2 -> new SlowEffect();
            case 3 -> new SpeedEffect();
            case 4 -> new StunEffect();
            default -> new NoEffect();
        };
    }

    // an insect eats the spore, which generates a random effect and activate it
    public void applyEffect(Insect insect) {
        generateEffect().applyEffect(insect);
    }
}