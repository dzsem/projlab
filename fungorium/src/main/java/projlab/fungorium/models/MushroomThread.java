package projlab.fungorium.models;

import java.util.ArrayList;
import java.util.List;

import projlab.fungorium.interfaces.TurnAware;
import projlab.fungorium.utilities.Logger;

public class MushroomThread implements TurnAware {
    public enum GrowState {
        SPROUT,
        GROWN
    }

    public enum CutState {
        UNCUT,
        CUT
    }

    private static final int DEFAULT_TURNS_TO_DIE = 3;
    private static final int DEFAULT_TURNS_TO_GROW = 3;

    private int turnsToDie;
    private int turnsToGrow;

    private List<MushroomThread> connectedThreads;
    private Tecton tecton;

    private GrowState growState;
    private CutState cutState;

    public MushroomThread(Tecton from, Tecton to) {
    } 

    /**
     * Törli magát a connectedThreads listájának elemeiből és a tektonjáról
     */
    public void kill() {
        for (MushroomThread connectedThread : connectedThreads) {
            connectedThread.removeConnection(this);
        }
        tecton.removeConnection(this);
    }

    /**
     * BFS szerűen összegűjti és visszaadja azoknak a MushromoBody-k listáját, amivel kapcsolatban van
     * @return Azoknak a MushroomBody-k listája, amikkel kapcsolatban van a thread
     */
    public List<MushroomBody> getConnectedBodies() {
        List<MushroomBody> result = new ArrayList<>();

        if (cutState == CutState.CUT) { // Ha el van vágva, akkor nem lehet összeköttetve gomba testtel, visszatér az üres listával
            return result;
        }

        if (tecton.hasBody()) { // Ha a tektonján van gomba test, akkor azt felveszi a visszatérítendő listába
            try {
                result.add(tecton.getBody());
            } catch (Exception e) {
                Logger.printError(e.getMessage());
            }
        }

        List<MushroomThread> queue = new ArrayList<>();
        List<MushroomThread> visited = new ArrayList<>();

        for (MushroomThread connectedThread : connectedThreads) { // Felveszi a sorba azokat a fonalakat, amik nincsenek átvágva és benne vannak a connectedThreads listban
            if (connectedThread.cutState == CutState.UNCUT) {
                queue.addLast(connectedThread);
            }
        }

        visited.add(this); // Felveszi magát a visited listába, hogy a későbbiekben, ne vizsgálja újra magát

        while (!queue.isEmpty()) {
            MushroomThread thread = queue.removeFirst();

            if (thread.tecton.hasBody()) { // Ha a vizsgált fonálnak a tektonján van gomba test, akkor azt felveszi a visszatérítendő listába
                try {
                    result.add(tecton.getBody());
                } catch (Exception e) {
                    Logger.printError(e.getMessage());
                }
            }
            else { // Ha nincs rajta gomba test, akkor a sorba rakja a vizsgált fonálhoz kapcsolodó fonalak közül azokaz, amik nincsenek elvágva és még nem vizsgálták őket. Majd felveszi a vizsgált fonalat a már megvizsgáltak közé
                for (MushroomThread connectedThread : thread.connectedThreads) {
                    if (!visited.contains(connectedThread) && connectedThread.cutState == CutState.UNCUT) {
                        queue.addLast(connectedThread);
                    }
                }

                visited.add(thread);
            }
        }

        return result; 
    }

    /**
     * BFS szerűen megvizsgálja, hogy van-e összeköttetés gomba testtel.
     * @return true, ha van összeköttetés; false, ha nincs
     */
    public boolean isConnectedToBody() {
        if (cutState == CutState.CUT) { // Ha el van vágva, akkor nem lehet összeköttetve gomba testtel
            return false;
        }

        if (tecton.hasBody()) { // Ha a tektonján van gomba test, akkor biztosan van össze van kötve gomba testtel
            return true;
        }

        List<MushroomThread> queue = new ArrayList<>();
        List<MushroomThread> visited = new ArrayList<>();

        for (MushroomThread connectedThread : connectedThreads) { // Felvsezi a sorba azokat a fonalakat, amik nincsenek átvágva és benne vannak a connectedThreads listban
            if (connectedThread.cutState == CutState.UNCUT) {
                queue.addLast(connectedThread);
            }
        }

        visited.add(this); // Felveszi magát a visited listába, hogy a későbbiekben, ne vizsgálja újra magát

        while (!queue.isEmpty()) {
            MushroomThread thread = queue.removeFirst();

            if (thread.tecton.hasBody()) { // Ha a vizsgált fonálnak a tektonján van gomba test, akkor visszatérhet igazzal
                return true;
            }
            else { // Ha nincs rajta gomba test, akkor a sorba rakja a vizsgált fonálhoz kapcsolodó fonalak közül azokaz, amik nincsenek elvágva és még nem vizsgálták őket. Majd felveszi a vizsgált fonalat a már megvizsgáltak közé
                for (MushroomThread connectedThread : thread.connectedThreads) {
                    if (!visited.contains(connectedThread) && connectedThread.cutState == CutState.UNCUT) {
                        queue.addLast(connectedThread);
                    }
                }

                visited.add(thread);
            }
        }

        return false; // Ha a sor kiürült és nem talált gomba testet, akkor nincs összeköttetésen egy testtel sem.
    }

    /**
     * Összegűjti és visszaadja azoknak a Tektonoknak listáját, amivel közvetlen kapcsolatban van
     * @return Azoknak a Tektonoknak listáját, amivel közvetlen kapcsolatban van
     */
    public List<Tecton> getConnectedTectons() {
        throw new RuntimeException("Not Implemented");
    }

    /**
     * Csökkenti a turnsToDie értékét eggyel
     */
    public void decreaseTurnsToDie() {

    }
    
    /**
     * Visszaállítja a turns to die értékét az alapértelmezettre
     */
    public void resetTurnsToDie() {

    }

    
    /**
     * Meghívja a kill függvényt, ha a turnsToDie értéke 0
     */
    public void dieIfUnconnected() {
        if (turnsToDie == 0) {
            kill();
        }
    }

    /**
     * Csökkenti a turnsToGorw értékét eggyel
     */
    public void grow() {
    }

    /**
     * A fonál CutState értékét cut-ra állítja
     */
    public void cut() {

    }

    /**
     * Létrehoz egy új MushroomThread-et a to tecton felé, majd ezt felveszi a listájába
     * @param to az a tecton, ahol az új fonál létrejön
     */
    public void createConnection(Tecton to) {
        if (to.isNeighbour(tecton)) {
            MushroomThread newThread = new MushroomThread(tecton, to);
            newThread.addConnection(this);
            addConnection(newThread);
        }
    }

    /**
     * Felveszi a paraméterként kapott thread-et a connectedThreads listájába
     * @param thread az a fonál, amit a connectedThreads listába vesz fel 
     */
    public void addConnection(MushroomThread thread) {
        connectedThreads.add(thread);
    }

    /**
     * Kiveszi a paraméterként kapott thread-et a connectedThreads listájából
     * @param thread az a fonál, amit a connectedThreads listából vesz ki
     */
    public void removeConnection(MushroomThread thread) {
        connectedThreads.remove(thread);
    }

    /**
     * Megvizsgálja, hogy a paraméterkén kapott tektonnal össze van-e kötve közvetenül. 
     * (Fontos, hogy a fonál, amivel össze van kötve meg legyen nőve [GROWN] és ne legyen elvágva [UNCUT] )
     * @param t a vizsgálandó tekton
     * @return igaz, ha össze van kötve; hamis, ha nincs
     */
    public boolean isConnectingTecton(Tecton t) {
        for (MushroomThread connectedThread : connectedThreads) {
            if (connectedThread.cutState != CutState.UNCUT || connectedThread.growState != GrowState.GROWN) { // Átugorja azokat a fonalakat, amik le vannak vágva vagy még nincsenek megnőve
                continue;
            }

            if (connectedThread.tecton == t) {
                return true;
            }
        }

        return false;
    }


    /**
     * Vizsgálja, hogy össze van-e kötve, gomba testtel és ez alapján: 
     * - Csökkenti a turnsToDie attributumának értékét eggyel
     * - Megöli a fonalat, ha turnsToDie értéke 0
     * - Csökkenti a fonál turnsToGrow értékét eggyel
     * - Beállítja a fonál growState állapotát GROWN-ra, ha a trunsToGrow értéke 0 
     */
    @Override
    public void onEndOfTheRound() {
        if (!isConnectedToBody()) {
            turnsToDie--;
            dieIfUnconnected();
        } else {
            if (growState == GrowState.SPROUT)
            {
                turnsToGrow--;
                if (turnsToGrow == 0) {
                    growState = GrowState.GROWN;
                }
            }
        }
    }
}

