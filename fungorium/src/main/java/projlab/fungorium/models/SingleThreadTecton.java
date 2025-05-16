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
     * Létrehoz egy új Tectont, aminek nincsenek szomszédjai és nincs rajta semmi.
     * 
     * @param neighbour Szomszédos tektonok.
     */
    public SingleThreadTecton(List<Tecton> neighbours) {
        super(neighbours);
    }

    /**
     * Ellenőrzi azt is, hogy ha már nő rajta egy fonal, akkor nem növeszthet
     * többet.
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

    @Override
    public String getOutputString() {
        StringBuilder sb = new StringBuilder("SINGLETHREADTECTON ");
        sb.append(getID() + " ");
        sb.append(neighbours.size() + " ");
        sb.append(hasBody() ? mushroomBody.getID() + " " : -1 + " ");
        sb.append(mushroomThreads.size() + " ");
        sb.append(mushroomSpores.size());

        return sb.toString();
    }
}
