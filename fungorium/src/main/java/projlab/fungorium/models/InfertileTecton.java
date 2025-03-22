package projlab.fungorium.models;

final public class InfertileTecton extends Tecton {
    @Override
    public void setBody(MushroomBody mb) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("InfertileTecton can't grow a body");
    }
}
