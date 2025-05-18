package projlab.fungorium.models;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import projlab.fungorium.interfaces.PrintableState;
import projlab.fungorium.utilities.Logger;

public class MushroomThread extends TurnAware implements PrintableState {
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

    private final int mushroomID;

    private int turnsToDie;
    private int turnsToGrow;

    private List<MushroomThread> connectedThreads;
    private Tecton tecton;

    private GrowState growState;
    private CutState cutState;

    /**
     * Beállítja az attribútumait az alapértelmezett értékekre és hozzáadja magát a
     * tekton listájához.
     * 
     * @param tecton
     */
    public MushroomThread(Tecton tecton, int mushroomID) {
        this.mushroomID = mushroomID;
        this.tecton = tecton;
        this.cutState = CutState.UNCUT;
        this.growState = GrowState.SPROUT;
        this.turnsToDie = DEFAULT_TURNS_TO_DIE;
        this.turnsToGrow = DEFAULT_TURNS_TO_GROW;

        connectedThreads = new ArrayList<>();

        try {
            tecton.addConnection(this);
        } catch (Exception e) {
            Logger.printError(e.getMessage());
        }

        Game.getInstance().getRegistry().registerMushroomThread(this);
    }

    /**
     * Törli magát a connectedThreads listájának elemeiből és a tektonjáról
     */
    public void kill() {
        for (MushroomThread connectedThread : connectedThreads) {
            connectedThread.removeConnection(this);
        }
        tecton.removeConnection(this);
        this.tecton = null;
        delete();
    }

    /**
     * BFS szerűen összegűjti és visszaadja azoknak a MushromoBody-k listáját,
     * amivel kapcsolatban van
     * 
     * @return Azoknak a MushroomBody-k listája, amikkel kapcsolatban van a thread
     */
    public List<MushroomBody> getConnectedBodies() {
        List<MushroomBody> result = new ArrayList<>();
        
        try {
            if (tecton.hasBody() && tecton.getBody().getMushroomID() == mushroomID) { // Ha a tektonján van gomba test,
                                                                                      // akkor
                // azt felveszi a visszatérítendő listába
                result.add(tecton.getBody());

                // Ha a gombatest mellett van, akkor automatikus uncut. Softlock elkerülése
                cutState = CutState.UNCUT;
            }
        } catch (Exception e) {
            Logger.printError(e.getMessage());
        }

        if (cutState == CutState.CUT) { // Ha el van vágva, akkor nem lehet összeköttetve gomba testtel, visszatér az
                                        // üres listával
            return result;
        }

        List<MushroomThread> queue = new ArrayList<>();
        List<MushroomThread> visited = new ArrayList<>();

        for (MushroomThread connectedThread : connectedThreads) { // Felveszi a sorba azokat a fonalakat, amik nincsenek
                                                                  // átvágva és benne vannak a connectedThreads listban
            if (connectedThread.cutState == CutState.UNCUT) {
                queue.add(connectedThread);
            }
        }

        visited.add(this); // Felveszi magát a visited listába, hogy a későbbiekben, ne vizsgálja újra
                           // magát

        while (!queue.isEmpty()) {
            MushroomThread thread = queue.remove(0);

            if (thread.tecton.hasBody()) { // Ha a vizsgált fonálnak a tektonján van gomba test, akkor azt felveszi a
                try {
                    if (thread.tecton.getBody().getMushroomID() == mushroomID) {
                        // visszatérítendő listába
                        result.add(thread.tecton.getBody());
                    }
                } catch (Exception e) {
                    Logger.printError(e.getMessage());
                }
            } else { // Ha nincs rajta gomba test, akkor a sorba rakja a vizsgált fonálhoz kapcsolodó
                     // fonalak közül azokaz, amik nincsenek elvágva és még nem vizsgálták őket. Majd
                     // felveszi a vizsgált fonalat a már megvizsgáltak közé
                for (MushroomThread connectedThread : thread.connectedThreads) {
                    if (!visited.contains(connectedThread) && connectedThread.cutState == CutState.UNCUT) {
                        queue.add(connectedThread);
                    }
                }

                visited.add(thread);
            }
        }

        return result;
    }

    /**
     * BFS szerűen megvizsgálja, hogy van-e összeköttetés gomba testtel.
     * 
     * @return true, ha van összeköttetés; false, ha nincs
     */
    public boolean isConnectedToBody() {
        return !getConnectedBodies().isEmpty();
    }

    /**
     * Összegűjti és visszaadja azoknak a Tektonoknak listáját, amivel közvetlen
     * kapcsolatban van
     * 
     * @return Azoknak a Tektonoknak listáját, amivel közvetlen kapcsolatban van
     */
    public List<Tecton> getConnectedTectons() {
        List<Tecton> result = new ArrayList<>();

        if (cutState == CutState.CUT) { // Ha el van vágva, akkor üres listával tér vissza
            return result;
        }

        for (MushroomThread connectedThread : connectedThreads) {
            if (connectedThread.cutState == CutState.UNCUT) { // Csak azokra a tectonokra van szükség, amiknek a fonala
                                                              // az nincs elvágva
                result.add(connectedThread.tecton);
            }
        }

        return result;
    }

    public List<MushroomThread> getConnectedThreads() {
        return connectedThreads;
    }

    /**
     * Csökkenti a turnsToDie értékét eggyel
     */
    public void decreaseTurnsToDie() {
        turnsToDie--;
    }

    /**
     * Visszaállítja a turns to die értékét az alapértelmezettre
     */
    public void resetTurnsToDie() {
        turnsToDie = DEFAULT_TURNS_TO_DIE;
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
        turnsToGrow--;
    }

    /**
     * Beállítja a fonál cutState változóját.
     * Tesztekhez szükséges
     * 
     * @param cutState cutState új értéke
     */
    public void setCutState(CutState cutState) {
        this.cutState = cutState;
    }

    /**
     * Létrehoz egy új MushroomThread-et a to tecton felé, majd ezt felveszi a
     * listájába
     * 
     * @param to az a tecton, ahol az új fonál létrejön
     * @throws Exception ha nem tod
     */
    public MushroomThread createConnection(Tecton to) throws Exception {
        if (!to.isNeighbour(tecton)) {
            throw new Exception("Tecton was not neighour");
        }

        if (growState != GrowState.GROWN) {
            throw new Exception("Only grown threads can create new connections");
        }

        try {
            var mt = to.getThread(mushroomID);
            mt.cutState = CutState.UNCUT;

            return mt;
        } catch (NoSuchElementException e) {
            MushroomThread newThread = new MushroomThread(to, mushroomID);
            newThread.addConnection(this);
            addConnection(newThread);

            return newThread;
        }
    }

    /**
     * Felveszi a paraméterként kapott thread-et a connectedThreads listájába
     * 
     * @param thread az a fonál, amit a connectedThreads listába vesz fel
     */
    public void addConnection(MushroomThread thread) {
        connectedThreads.add(thread);
    }

    /**
     * Kiveszi a paraméterként kapott thread-et a connectedThreads listájából
     * 
     * @param thread az a fonál, amit a connectedThreads listából vesz ki
     */
    public void removeConnection(MushroomThread thread) {
        connectedThreads.remove(thread);
    }

    /**
     * Megvizsgálja, hogy a paraméterkén kapott tektonnal össze van-e kötve
     * közvetenül.
     * (Fontos, hogy a fonál, amivel össze van kötve meg legyen nőve [GROWN] és ne
     * legyen elvágva [UNCUT] )
     * 
     * @param t a vizsgálandó tekton
     * @return igaz, ha össze van kötve; hamis, ha nincs
     */
    public boolean isConnectingTecton(Tecton t) {
        for (MushroomThread connectedThread : connectedThreads) {
            // Átugorja azokat a fonalakat, amik le vannak vágva vagy még nincsenek megnőve
            if (connectedThread.cutState != CutState.UNCUT || connectedThread.growState != GrowState.GROWN) {
                continue;
            }

            if (connectedThread.tecton == t) {
                return true;
            }
        }

        return false;
    }

    /**
     * Visszaadja a gombához tartozó mushroomID-t
     * 
     * @return gombához tartozó mushroomID
     */
    public int getMushroomID() {
        return mushroomID;
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
            decreaseTurnsToDie();
            dieIfUnconnected();
        } else {
            if (growState == GrowState.SPROUT) {
                grow();
                if (turnsToGrow == 0) {
                    growState = GrowState.GROWN;
                }
            }
        }
    }

    public void eat() throws Exception {
        Insect i = tecton.getStunnedInsect();
        tecton.unregisterInsect(i);
        i.die();

        if (!tecton.hasBody()) {
            tecton.growBody(mushroomID);
        }
    }

    @Override 
    protected void delete() {
        Game.getInstance().getRegistry().unregisterMushroomThread(this);
        super.delete();
    }

    /*
     * Visszaadja a Tectont, amin a gombafonál van.
     */
    public Tecton getTecton() {
        return tecton;
    }

    public CutState getCutState() {
        return cutState;
    }

    public GrowState getGrowState() {
        return growState;
    }

    public int getTurnsToGrow() {
        return turnsToGrow;
    }

    /**
     * Beállítja a fonál turnsToDie változóját.
     * Tesztekhez szükséges
     * 
     * @param turnsToDie turnsToDie új értéke
     */
    public void setTurnsToDie(int turnsToDie) {
        this.turnsToDie = turnsToDie;
    }

    /**
     * Beállítja a fonál turnsToDie változóját.
     * Tesztekhez szükséges
     * 
     * @param turnsToGrow turnsToGrow új értéke
     */
    public void setTurnsToGrow(int turnsToGrow) {
        this.turnsToGrow = turnsToGrow;
    }

    /**
     * Beállítja a fonál growState változóját.
     * Tesztekhez szükséges
     * 
     * @param growState growState új értéke
     */
    public void setGrowState(GrowState growState) {
        this.growState = growState;
    }

    /**
     * Létrehozza a MushroomThread-hez tartozó state stringet
     */
    @Override
    public String getStateString() {
        StringBuilder stateString = new StringBuilder();

        stateString.append("Mushroom ID: ").append(mushroomID).append("\n");
        stateString.append("Turns to die: ").append(turnsToDie).append("\n");
        stateString.append("Turns to grow: ").append(turnsToGrow).append("\n");
        stateString.append("Number of connected threads: ").append(connectedThreads.size()).append("\n");
        stateString.append("Grow state ").append(growState.toString()).append("\n");
        stateString.append("Cut state ").append(cutState.toString()).append("\n");

        return stateString.toString();
    }

    @Override
    public String getOutputString() {
        StringBuilder sb = new StringBuilder("MUSHROOMTHREAD ");
        sb.append(getID() + " ");
        sb.append(mushroomID + " ");
        sb.append(tecton.getID() + " ");
        sb.append(connectedThreads.size() + " ");
        sb.append(cutState.toString() + " ");
        sb.append(growState.toString());

        return sb.toString();
    }

}
