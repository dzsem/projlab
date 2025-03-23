package projlab.fungorium.models;

import projlab.fungorium.models.effects.*;

import java.util.Random;

class MushroomSpore {
    /**
     * mikor egy spóra létrejön, akkor hozzáadja magát a tecton listájához, amin rajta van
     * @param tecton a Tetcon, amin a spóra rajta van
     */
    public MushroomSpore(Tecton tecton) {
        tecton.addSpore(this);
    }
    /**
     * vissza add egy random effectet
     * @return egy random effectet, ami lehet blockkoló, lassító, gyorsító, bénító vagy semmilyen
     */
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

    /**
     * a rovar megeszi a spórát, ami generál egy random effectet és aktiválja a hatását
     * @param insect a rovar ami megeszi ezt az effectet
     */
    public void applyEffect(Insect insect) {
        generateEffect().applyEffect(insect);
    }
}