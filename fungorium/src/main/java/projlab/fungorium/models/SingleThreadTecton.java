package projlab.fungorium.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Az egy fonalat növesztő Tecton modellje.
 */
public final class SingleThreadTecton extends Tecton {
    /**
     * Konstuktor.
     */
    public SingleThreadTecton() {
        super();
    }
    
    /**
     * Konstruktor.
     * 
     * @param neighbours Szomszédos Tectonok.
     * @param thread Tectonon nönő fonalak.
     * @param spores Tectonon lévő spórák.
     * @param insects Tectonon álló rovarok.
     * @param body Tectonon növő gombatest.
     */
    public SingleThreadTecton(List<Tecton> neighbours, MushroomThread thread, List<MushroomSpore> spores, List<Insect> insects, MushroomBody body) {
        super(neighbours, List.of(thread), spores, insects, body);
    }
    
    /**
     * Ellenőrzi azt is, hogy ha már nő rajta egy fonal, akkor nem növeszthet többet.
     * 
     * @throws Exception Ha már nőtt rajta egy fonal.
     */
    @Override
    public final void addConnection(MushroomThread mt) throws Exception {
        if (!mushroomThreads.isEmpty()) {
            throw new Exception("SingleThreadTecton can only grow a maximum of 1 MushroomThreads.");
        }

        super.addConnection(mt);
    }
}
