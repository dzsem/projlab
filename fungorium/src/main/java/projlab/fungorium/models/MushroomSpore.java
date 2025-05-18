package projlab.fungorium.models;

import projlab.fungorium.interfaces.PrintableState;
import projlab.fungorium.interfaces.WritableGameObject;
import projlab.fungorium.models.effects.*;

import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

public class MushroomSpore extends GameObject implements PrintableState {
    private Tecton tecton;
    private EffectTypes effectType;
    private Random rand = new Random();
    private static final Map<EffectTypes, Supplier<Effect>> effectMap = Map.of(
            EffectTypes.BLOCK, BlockEffect::new,
            EffectTypes.SLOW, SlowEffect::new,
            EffectTypes.SPEED, SpeedEffect::new,
            EffectTypes.STUN, StunEffect::new,
            EffectTypes.NO, NoEffect::new,
            EffectTypes.DUPLICATE, DuplicateEffect::new);

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
        effectType = EffectTypes.RANDOM;

        Game.getInstance().getRegistry().registerMushroomSpore(this);
    }

    public Tecton getTecton() {
        return tecton;
    }

    /**
     * beállítja a spóra által generált effektet a paraméterként megadott effektre
     * 
     * @param effectType kívánt effekt
     */
    public void setEffectGeneration(EffectTypes effectType) {
        this.effectType = effectType;
    }

    /**
     * vissza ad egy random effectet
     * 
     * @return egy random effectet, ami lehet blockkoló, lassító, gyorsító, bénító
     *         vagy semmilyen
     */
    protected Effect generateEffect() {
        if (effectType == EffectTypes.RANDOM) {
            // ne lehessen NoEffect-et generálni véletlenszerűen
            return switch (rand.nextInt(4)) {
                case 1 -> new BlockEffect();
                case 2 -> new SlowEffect();
                case 3 -> new SpeedEffect();
                case 4 -> new StunEffect();
                default -> new NoEffect();
            };
        }
        return effectMap.get(effectType).get();
    }

    /**
     * a rovar megeszi a spórát, ami generál egy random effectet és aktiválja a
     * hatását
     * 
     * @param insect a rovar ami megeszi ezt az effectet
     */
    public Effect applyEffect(Insect insect) throws Exception {
        Effect effect = generateEffect();
        effect.applyEffect(insect);
        delete();

        return effect;
    }

    @Override
    protected void delete() {
        Game.getInstance().getRegistry().unregisterMushroomSpore(this);
        super.delete();
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