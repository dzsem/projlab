package projlab.fungorium.models;

/**
 * Az egy fonalat növesztő Tecton modellje.
 */
public final class SingleThreadTecton extends Tecton {
    /**
     * Ellenőrzi azt is, hogy ha már nő rajta egy fonal, akkor nem növeszthet többet.
     * 
     * @throws RuntimeException Ha már nőtt rajta egy fonal.
     */
    @Override
    public final void addConnection(MushroomThread mt) throws RuntimeException {
        if (!mushroomThreads.isEmpty()) {
            throw new RuntimeException("SingleThreadTecton can only grow a maximum of 1 MushroomThreads.");
        }

        super.addConnection(mt);
    }
}
