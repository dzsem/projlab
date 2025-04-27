package projlab.fungorium.models;

import projlab.fungorium.interfaces.PrintableState;
import projlab.fungorium.interfaces.WritableGameObject;
import projlab.fungorium.models.effects.*;

import java.util.Random;

public class MushroomSpore extends GameObject implements PrintableState {
    private Tecton tecton;

    /**
     * mikor egy spóra létrejön, akkor hozzáadja magát a tecton listájához, amin
     * rajta van
     * 
     * @param tecton a Tetcon, amin a spóra rajta van
     */
    public MushroomSpore(Tecton tecton) {
        super();
        this.tecton = tecton;
        tecton.addSpore(this);
    }

    /**
     * vissza add egy random effectet
     * 
     * @return egy random effectet, ami lehet blockkoló, lassító, gyorsító, bénító
     *         vagy semmilyen
     */
    protected Effect generateEffect() {
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
     * a rovar megeszi a spórát, ami generál egy random effectet és aktiválja a
     * hatását
     * 
     * @param insect a rovar ami megeszi ezt az effectet
     */
    public void applyEffect(Insect insect) {
        generateEffect().applyEffect(insect);
    }

    @Override
    public String getStateString() {
        return "This is a Mushroom Spore, which  can be eaten or used";
    }

    @Override
    public String getOutputString() {
        StringBuilder sb = new StringBuilder("MUSHROOMSPORE ");
        sb.append(getID() + " ");
        sb.append(tecton.getID());

        return sb.toString();
    }
}