package projlab.fungorium.models;

import java.util.List;

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

    public void split() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Unimplemented method 'split'");
    }

    public boolean isNegihbour(Tecton t) {
        return neighbours.contains(t);
    }

    // Ez kell egyáltalán hogy publikus legyen?
    // public List<Tecton> getNeighbours() {
    //     return neighbours;
    // }



    // -------------------------------------
    // MushroomThread stuff

    public boolean verifyConnection(Tecton t) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Unimplemented method 'verifyConnection'");
    }

    public void addConnection(MushroomThread mt) {
        mushroomThreads.add(mt);
    }

    public void removeConnection(MushroomThread mt) {
        mushroomThreads.remove(mt);
    }



    // -------------------------------------
    // Insect stuff

    public void registerInsect(Insect i) {
        insects.add(i);
    }

    public void unregisterInsect(Insect i) {
        insects.remove(i);
    }



    // -------------------------------------
    // MushroomSpore stuff

    public void addSpore(MushroomSpore ms) {
        mushroomSpores.add(ms);
    }

    // getRandomSpore() és removeSpore() összeolvasztva, mert tulajdonképpen mindegy melyik spórát veszed ki, az effektet a megevés fogja generálni
    public MushroomSpore removeSpore() {
        return mushroomSpores.removeLast();
    }

    public int getSporeCount() {
        return mushroomSpores.size();
    }



    // -------------------------------------
    // MushroomBody stuff

    public boolean hasThreadFrom(MushroomBody mb) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Unimplemented method 'hasThreadFrom'");
    }

    // Ennek igazából nem is kell argumentum, mert csak egy Body lehet egy Tectonon.
    public void removeBody() {
        mushroomBody = null;
    }

    public void setBody(MushroomBody mb) throws Exception {
        if (mushroomBody != null) {
            throw new Exception("Tecton already has a body.");
        }

        mushroomBody = mb;
    }

    public boolean hasBody() {
        return mushroomBody != null;
    }

    public void killThread(MushroomThread mt) throws Exception {
        throw new Exception("Non-ThreadKillingTectons can't kill MushroomThreads");
    }



    // -------------------------------------
    // Interface implementációk

    @Override
    public void onEndOfTheRound() {
        throw new UnsupportedOperationException("Unimplemented method 'onEndOfTheRound'");
    }

    private MushroomBody mushroomBody;
    private List<MushroomThread> mushroomThreads;
    private List<MushroomSpore> mushroomSpores;
    private List<Insect> insects;
    private List<Tecton> neighbours;
}
