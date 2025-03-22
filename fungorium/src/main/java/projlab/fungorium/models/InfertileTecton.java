package projlab.fungorium.models;

final public class InfertileTecton extends Tecton {
    @Override
    final public void setBody(MushroomBody mb) throws Exception {
        throw new Exception("InfertileTecton can't grow a body");
    }
}
