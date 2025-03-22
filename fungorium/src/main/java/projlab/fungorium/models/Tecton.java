package projlab.fungorium.models;

import java.util.List;
import java.util.Random;

import projlab.fungorium.interfaces.TurnAware;

import java.util.ArrayList;

public class Tecton implements TurnAware {
    public Tecton() {
        this.mushroomBody = null;
        this.mushroomThreads = new ArrayList<>();
        this.mushroomSpores = new ArrayList<>();
        this.insects = new ArrayList<>();
        this.neighbours = new ArrayList<>();
    }

    public Tecton(List<Tecton> neighbours) {
        this.mushroomBody = null;
        this.mushroomThreads = new ArrayList<>();
        this.mushroomSpores = new ArrayList<>();
        this.insects = new ArrayList<>();
        this.neighbours = new ArrayList<Tecton>(neighbours);
    }

    // -------------------------------------
    // Tecton stuff

    final public void split() {
        List<Tecton> newNeighbours = new ArrayList<>(List.of(this));
        newNeighbours.addAll(neighbours);
        Tecton newTect = new Tecton(newNeighbours);
        neighbours.add(newTect);
    }

    final public boolean isNeighbour(Tecton t) {
        return neighbours.contains(t);
    }

    // Ez kell egyáltalán hogy publikus legyen?
    // public List<Tecton> getNeighbours() {
    //     return neighbours;
    // }



    // -------------------------------------
    // MushroomThread stuff

    final public boolean verifyConnection(Tecton t) {
        if (!isNeighbour(t)) {
            return false;
        }

        for (MushroomThread mt : mushroomThreads) {
            if (mt.isConnectingTecton(t)) {
                return true;
            }
        }

        return false;
    }

    public void addConnection(MushroomThread mt) {
        // TODO: check if mt comes from a neighbour tecton
        

        mushroomThreads.add(mt);
    }

    final public void removeConnection(MushroomThread mt) {
        mushroomThreads.remove(mt);
    }



    // -------------------------------------
    // Insect stuff

    final public void registerInsect(Insect i) {
        insects.add(i);
    }

    final public void unregisterInsect(Insect i) {
        insects.remove(i);
    }



    // -------------------------------------
    // MushroomSpore stuff

    final public void addSpore(MushroomSpore ms) {
        mushroomSpores.add(ms);
    }

    final public void removeSpore(MushroomSpore ms) {
        mushroomSpores.remove(ms);
    }

    final public MushroomSpore getRandomSpore() {
        Random r = new Random();
        return mushroomSpores.get(r.nextInt(mushroomSpores.size()));
    }

    final public int getSporeCount() {
        return mushroomSpores.size();
    }



    // -------------------------------------
    // MushroomBody stuff

    // TODO
    final public boolean hasThreadFrom(MushroomBody mb) throws UnsupportedOperationException {
        // for (MushroomThread mt : mushroomThreads) {
        //     if ()
        // }

        throw new UnsupportedOperationException("Unimplemented method 'hasThreadFrom'");
    }

    // Ennek igazából nem is kell argumentum, mert csak egy Body lehet egy Tectonon.
    final public void removeBody() {
        mushroomBody = null;
    }

    public void setBody(MushroomBody mb) throws RuntimeException {
        if (mushroomBody != null) {
            throw new RuntimeException("Tecton already has a body.");
        }

        if (getSporeCount() < 3) {
            throw new RuntimeException("Tecton needs at least 3 MushroomSpores to grow a body.");
        }

        if (!hasThreadFrom(mb)) {
            throw new RuntimeException("Tecton needs a MushroomThread from the MushroomBody before growing it.");
        }

        mushroomBody = mb;
    }

    final public boolean hasBody() {
        return mushroomBody != null;
    }

    // TODO: killThread átnevezése killThreads-re, mert mindegyiket megöli, ami rajta van
    public void killThreads() throws Exception {
        throw new UnsupportedOperationException("Non-ThreadKillingTectons can't kill MushroomThreads");
    }



    // -------------------------------------
    // Egyéb

    public void setSplitChance(double p) {
        splitChance = p;
    }



    // -------------------------------------
    // Interface implementációk

    @Override
    public void onEndOfTheRound() {
        Random r = new Random();

        if (r.nextDouble() < splitChance) {
            this.split();
        }
    }

    protected MushroomBody mushroomBody;
    protected List<MushroomThread> mushroomThreads;
    private List<MushroomSpore> mushroomSpores;
    private List<Insect> insects;
    private List<Tecton> neighbours;
    private static double splitChance = 0.3f;
}
