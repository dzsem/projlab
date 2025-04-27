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

    @Override
    public String getOutputString() {
        StringBuilder sb = new StringBuilder("INFERTILETECTON ");
        sb.append(getID() + " ");
        sb.append(neighbours.size() + " ");
        sb.append(hasBody() ? mushroomBody.getID() + " " : -1 + " ");
        sb.append(mushroomThreads.size() + " ");
        sb.append(mushroomSpores.size());

        return sb.toString();
    }
}
