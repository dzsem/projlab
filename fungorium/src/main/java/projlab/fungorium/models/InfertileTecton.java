package projlab.fungorium.models;

/**
 * Olyan Tecton modellje, ami nem tud MushroomBodyt növeszteni.
 */
public final class InfertileTecton extends Tecton {
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
