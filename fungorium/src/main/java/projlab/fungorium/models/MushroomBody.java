package projlab.fungorium.models;

import java.util.ArrayList;
import java.util.List;

import projlab.fungorium.interfaces.TurnAware;
import projlab.fungorium.utilities.Logger;

public class MushroomBody implements TurnAware {
    public enum Advancement {
        NORMAL,
        ADVANCED
    }

    private static final int MAX_SPORES = 5;
    private static final int ADVANCED_AGE = 5;

    private int remainingSpores;
    private int age;

    private Tecton tecton;

    private Advancement advancement;

    /**
     * Beállítja az attributumait az alapértelmezett értékekre és felhelyezi magát a paraméterként kapott tektonra
     * @param tecton A tekton, amire a gombatest fog kerülni
     */
    public MushroomBody(Tecton tecton) {    
        this.tecton = tecton;
        this.advancement = Advancement.NORMAL;
        this.age = 0;
        this.remainingSpores = MAX_SPORES;

        try {
            tecton.setBody(this);
        } catch (Exception e) {
            Logger.printError(e.getMessage());
        }
    }

    /**
     * Spórákat helyez el a körülötte lévő tektonokra.
     * Ha normal állapotban van, akkor csak az 1 és 0 távolságra lévőkre,
     * Ha advanced állapotban van, akkor a 2, 1 és 0 távolságra lévőkre
     */
    public void distributeSpores() {
        List<Tecton> tectonsToSpore = new ArrayList<>(); // azoknak a tektonoknak a listája, amiket be kell spórázni
        List<Tecton> neighbours = tecton.getNeighbours();

        tectonsToSpore.add(tecton); // 0 távolságra a saját tekton van
        tectonsToSpore.addAll(neighbours); // 1 távolságra a szomszédos tektonok
    
        if (advancement == Advancement.ADVANCED) { // Összegyűjti azokat a szomszédokkal szomszédos tektonakat, amik még nincsenek benne a spórázandó tektonok listájában (így minden csak egyszer szerepel benne)
            for (Tecton neighbour : neighbours) { 
                for (Tecton neighbourOfNeighbour : neighbour.getNeighbours()) {
                    if (!tectonsToSpore.contains(neighbourOfNeighbour)) {
                        tectonsToSpore.add(neighbourOfNeighbour);
                    }
                }
            }
        }

        for (Tecton tectonToSpore : tectonsToSpore) { // Tektonok spórázása
            new MushroomBody(tectonToSpore);
        }

        remainingSpores--; // Spóra mennyiség cskkentés

        if (remainingSpores == 0) { // Vizsgálja, hogy elfogytak-e a spórák és ha igen, akkor törli magát a tektonról
            tecton.removeBody(); 
        }
    }

    /**
     * Körök végén kell meghívni.
     * Növeli a gomba test korát, ha a test még nem érte el az advanced szintet
     */
    public void onEndOfTheRound() {
        if (advancement == Advancement.NORMAL) {
            age++;
    
            if (age >= ADVANCED_AGE) {
                advancement = Advancement.ADVANCED;
            }
        }
    } 

    /**
     * Beállítja a gomba test advancement szintjét.
     * Teszteléshez használt.
     * @param advancement az új advancement szint
     */
    public void setAdvancement(Advancement advancement) {
        this.advancement = advancement;
    }

    /**
     * Beállítja a gomba test maradék spóráit szintjét.
     * Teszteléshez használt.
     * @param remainingSpores a maradék spórák új értéke
     */
    public void setSporesRemaining(int remainingSpores) {
        this.remainingSpores = remainingSpores;
    }

}
