package projlab.fungorium.models;

final public class SingleThreadTecton extends Tecton {
    @Override
    public void addConnection(MushroomThread mt) {
        if (mushroomThreads.size() >= 1) {
            throw new RuntimeException("SingleThreadTecton can only grow a maximum of 1 MushroomThreads.");
        }

        super.addConnection(mt);
    }
}
