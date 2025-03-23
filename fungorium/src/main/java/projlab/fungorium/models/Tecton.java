package projlab.fungorium.models;

import java.util.List;
import java.util.Random;

import projlab.fungorium.interfaces.TurnAware;

import java.util.ArrayList;

/**
 * A Tecton modellje.
 */
public class Tecton implements TurnAware {
    /**
     * Létrehoz egy új Tectont, aminek nincsenek szomszédjai és nincs rajta semmi.
     */
    public Tecton() {
        this.mushroomBody = null;
        this.mushroomThreads = new ArrayList<>();
        this.mushroomSpores = new ArrayList<>();
        this.insects = new ArrayList<>();
        this.neighbours = new ArrayList<>();
    }

    /**
     * Létrehoz egy új Tectont, aminek nincsenek szomszédjai és nincs rajta semmi.
     * @param neighbour Szomszédos tektonok.
     */
    public Tecton(List<Tecton> neighbours) {
        this.mushroomBody = null;
        this.mushroomThreads = new ArrayList<>();
        this.mushroomSpores = new ArrayList<>();
        this.insects = new ArrayList<>();
        this.neighbours = new ArrayList<>();
    
        for (Tecton t : neighbours) {
            registerNeighbour(t);
        }
    }

    // -------------------------------------
    // Tecton stuff

    /**
     * Tekton szakadás. Létrehoz egy új Tectont, aminek beregisztálja magát és az összes szomszédját szomszédnak. Elszakítja a Tectonon lévő összes fonalat.
     */
    public final void split() {
        List<Tecton> newNeighbours = new ArrayList<>(List.of(this));
        newNeighbours.addAll(neighbours);
        Tecton newTect = new Tecton();
        registerNeighbour(newTect);

        for (MushroomThread mushroomThread : mushroomThreads) {
            mushroomThread.cut();
        }
    }

    /**
     * Ellenőrzi, hogy a megadott Tecton szomszédos-e ezzel a Tectonnal.
     * 
     * @param t Tekton, amit ellenőriz
     * @return True, ha szomszédosak
     */
    public final boolean isNeighbour(Tecton t) {
        return neighbours.contains(t);
    }

    /**
     * Hozzáadja a megadott Tectont ennek a Tectonnak a szomszédjai közé, aztán ezt a Tectont is hozzáadja a megadott Tecton szomszédjai közé.
     * 
     * @param t A másik tekton
     */
    public final void registerNeighbour(Tecton t) {
        neighbours.add(t);

        // Feltétel, hogy ne legyen végtelen rekurzió
        if (t.isNeighbour(this)) {
            return;
        }

        t.registerNeighbour(this);
    }

    /**
     * Eltávolítja a megadott Tectont ennek a Tectonnak a szomszédjai közül, aztán ezt a Tectont is eltávolítja a megadott Tecton szomszédjai közül.
     * 
     * @param t A másik tekton
     */
    public final void unregisterNeighbour(Tecton t) {
        neighbours.remove(t);

        // Feltétel, hogy ne legyen végtelen rekurzió
        if (!t.isNeighbour(this)) {
            return;
        }

        t.unregisterNeighbour(this);
    }



    // -------------------------------------
    // MushroomThread stuff

    /**
     * Ellenőrzi, hogy van-e fonállal összeköttetése a másik Tectonhoz.
     * 
     * @param t A másik Tecton
     * @return True, ha össze vannak kötve.
     */
    public final boolean verifyConnection(Tecton t) {
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

    /**
     * Felveszi a megadott MushroomThreadet a Tecton fonalai közé.
     * 
     * @param mt A megadott MushroomThread
     */
    public void addConnection(MushroomThread mt) throws Exception {
        mushroomThreads.add(mt);
    }

    /**
     * Eltávolítja a megadott MushroomThreadet a Tecton fonalai közül.
     * 
     * @param mt
     */
    public final void removeConnection(MushroomThread mt) {
        mushroomThreads.remove(mt);
    }



    // -------------------------------------
    // Insect stuff

    /**
     * Hozzáadja a megadott Insectet a Tectonon álló rovarokhoz.
     * 
     * @param i A megadott Insect
     */
    public final void registerInsect(Insect i) {
        insects.add(i);
    }

    /**
     * Eltávolítja a megadott Insectet a Tectonon álló rovarokból.
     * 
     * @param i A megadott Insect
     */
    public final void unregisterInsect(Insect i) {
        insects.remove(i);
    }



    // -------------------------------------
    // MushroomSpore stuff

    /**
     * Hozzáadja a megadott MushroomSporet a Tecton spóráihoz.
     * 
     * @param ms A megadott MushroomSpore
     */
    public final void addSpore(MushroomSpore ms) {
        mushroomSpores.add(ms);
    }

    /**
     * Eltávolítja a megadott MushroomSporet a Tecton spóráiból.
     * 
     * @param ms A megadott MushroomSpore
     */
    public final void removeSpore(MushroomSpore ms) {
        mushroomSpores.remove(ms);
    }

    /**
     * Visszaad egy random MushroomSporet a Tecton spóráiból.
     * 
     * @return Egy random MushroomSpore a Tectonról.
     */
    public final MushroomSpore getRandomSpore() {
        Random r = new Random();
        return mushroomSpores.get(r.nextInt(mushroomSpores.size()));
    }

    /**
     * Visszaadja, hogy a Tectonon mennyi spóra van.
     * 
     * @return A spórák száma
     */
    public final int getSporeCount() {
        return mushroomSpores.size();
    }



    // -------------------------------------
    // MushroomBody stuff

    /**
     * Megvizsgálja, hogy az adott MushroomBodytól nő-e MushroomThread a Tectonon.
     * 
     * @param mb Az adott MushroomBody
     * @return True, ha nő rajta fonal a gombatesstől
     */
    public final boolean hasThreadFrom(MushroomBody mb) {
        for (MushroomThread mushroomThread : mushroomThreads) {
            for (MushroomBody mbIt : mushroomThread.getConnectedBodies()) {
                if (mbIt.equals(mb)) {
                    return true;
                }
            }
        }

        return false;
    }

    public MushroomBody getBody() throws Exception {
        if (mushroomBody == null) {
            throw new Exception("Tecton does not hav a MushroomBody");
        }
        return mushroomBody;
    }

    // Ennek igazából nem is kell argumentum, mert csak egy Body lehet egy Tectonon.
    final public void removeBody() {
        mushroomBody = null;
    }

    /**
     * Beállítja a megadott MushroomBodyt a Tectonon növő gombatestnek.
     * 
     * @param mb A megadott MushroomBody
     * @throws Exception Ha a Tectonon már nő egy gombatest, vagy ha a Tectonon nincs legalább 3 MushroomSpore, vagy ha a Tectonon nem nő még MushroomThread mb-től.
     */
    public void setBody(MushroomBody mb) throws Exception {
        if (mushroomBody != null) {
            throw new Exception("Tecton already has a body.");
        }

        if (getSporeCount() < 3) {
            throw new Exception("Tecton needs at least 3 MushroomSpores to grow a body.");
        }

        if (!hasThreadFrom(mb)) {
            throw new Exception("Tecton needs a MushroomThread from the MushroomBody before growing it.");
        }

        mushroomBody = mb;
    }

    /**
     * Ellenőrzi, hogy nő-e MushroomBody a Tectonon.
     * 
     * @return True, ha nő.
     */
    public final boolean hasBody() {
        return mushroomBody != null;
    }

    // TODO: killThread átnevezése killThreads-re, mert mindegyiket megöli, ami rajta van
    /**
     * Nem csinál semmit, mivel ezt csak a ThreadKillingTecton tudja végrehajtani.
     * 
     * @throws Exception Mindig.
     */
    public void killThreads() throws Exception {
        throw new Exception("Non-ThreadKillingTectons can't kill MushroomThreads");
    }



    // -------------------------------------
    // Egyéb

    /**
     * Beállítja a Tectonok szakadási esélyét a megadott értékre.
     * 
     * @param p A szakadási esély, egy 0.0 és 1.0 közti double.
     */
    public static void setSplitChance(double p) {
        splitChance = p;
    }

    public List<Tecton> getNeighbours() {
        return neighbours;
    }

    // -------------------------------------
    // Interface implementációk

    /**
     * Minden kör végén a Tecton splitChance eséllyel kettészakad.
     */
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
    protected static double splitChance = 0.1f;
}
