package projlab.fungorium.models.effects;

import projlab.fungorium.models.Insect;

/**
 * minden effectnek kell lennie egy applyEffect függvénye
 */
public interface Effect {
    void applyEffect(Insect insect);

    String getDescription();
}
