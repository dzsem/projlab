package projlab.fungorium.models.effects;

import projlab.fungorium.models.Insect;

/**
 * all the effects must have an applyEffect
 */
public interface Effect {
    void applyEffect(Insect insect);
}
