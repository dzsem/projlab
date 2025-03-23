package projlab.fungorium.models;

/**
 * Az egy fonalat növesztő Tecton modellje.
 */
public final class SingleThreadTecton extends Tecton {
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
