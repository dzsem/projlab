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
     * Konstruktor.
     * 
     * @param neighbours Szomszédos Tectonok.
     * @param thread Tectonon nönő fonalak.
     * @param spores Tectonon lévő spórák.
     * @param insects Tectonon álló rovarok.
     */
    public InfertileTecton(List<Tecton> neighbours, MushroomThread thread, List<MushroomSpore> spores, List<Insect> insects) {
        super(neighbours, List.of(thread), spores, insects, null);
    }
    
    /**
     * Nem tud magának MushroomBodyt állítani.
     * 
     * @throws Exception Mindig.
     */
    @Override
    public final void setBody(MushroomBody mb) throws Exception {
        throw new Exception("InfertileTecton can't grow a body");
    }
}
