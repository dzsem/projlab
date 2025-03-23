package projlab.fungorium.models;

import java.util.List;

/**
 * Olyan Tecton modellje, ami nem tud MushroomBodyt növeszteni.
 */
public final class InfertileTecton extends Tecton {
    /**
     * Konstuktor.
     */
    public InfertileTecton() {
        super();
    }

    /**
     * Létrehoz egy új Tectont, aminek nincsenek szomszédjai és nincs rajta semmi.
     * @param neighbour Szomszédos tektonok.
     */
    public InfertileTecton(List<Tecton> neighbours) {
        super(neighbours);
    }
    
    /**
     * Nem tud magának MushroomBodyt állítani.
     */
    @Override
    public final void setBody(MushroomBody mb) { 
        mushroomBody = null;
    }

    @Override 
    public final void growBody(int mushroomID) throws Exception{
        throw new Exception("InfertileTecton can't grow a body");
    }
}
