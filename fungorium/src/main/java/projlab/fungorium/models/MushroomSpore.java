package projlab.fungorium.models;

import projlab.fungorium.models.effects.*;

import java.util.Random;

class MushroomSpore {

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
    public void applyEffect(Insect insect) {
        generateEffect().applyEffect(insect);
    }
}