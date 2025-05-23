package projlab.fungorium.models;

import projlab.fungorium.controllers.GameController;
import projlab.fungorium.interfaces.PrintableState;
import projlab.fungorium.models.MushroomThread.CutState;
import projlab.fungorium.models.effects.Effect;

/**
 * A rovarászok által irányított rovarokat megvalósító osztály.
 * <p>
 * Számon tartja a tektont, amin van, illetve van (a spórák és a
 * körök eltelése által változtatott) állapota.
 */
public class Insect extends TurnAware implements PrintableState {
    /** A rovarász játékos azonosítója */
    private int insectologistID;

    /**
     * Számon tartja, hogy a rovar tud-e jelenleg mozogni.
     * <p>
     * Amennyiben nem, akkor a kör végén nem kapja vissza az akcióit,
     * viszont visszabillen igazba; tehát a következő utáni körben újra léphet.
     * 
     * @see #onEndOfTheRound()
     */
    private boolean canMove;

    /**
     * Számon tartja, hogy a rovar tud-e fonalat vágni.
     * <p>
     * Amennyiben nem, csak akkor billen vissza igazba, amikor a
     * {@link #canCutCounter}
     * eléri a 0-t.
     *
     * @see #onEndOfTheRound()
     */
    private boolean canCut;

    /**
     * Számláló, fonálvágás-blokkolásnál használt.
     *
     * Amég a rovar nem tud vágni, ez a számláló csökken, egészen addig, amég el nem
     * éri a 0-t, ekkor a {@link #canCut} állapot a kör végén igazba billen.
     * 
     * @see #onEndOfTheRound()
     */
    private int canCutCounter;

    /**
     * Számláló a canMove visszaállítására a {@link #canCutCounter} mintája alapján.
     */
    private int canMoveCounter;

    /**
     * A tekton, amin jelenleg a rovar áll. A rovar mindig regisztrálja magát a
     * jelenlegi tektonon, és mindig pontosan egy tektonon van regisztrálva.
     * 
     * @see Tecton#registerInsect(Insect)
     */
    private Tecton tecton;

    private static final int CANCUT_COUNTER_DEFAULT_VALUE = 3;
    private static final int CANMOVE_COUNTER_DEFAULT_VALUE = 2;

    private int sporesEaten;
    /**
     * Létrehoz egy rovart, a megadott tektonon.
     *
     * @param startingTecton Az a tekton, amin a rovar eredetileg tartózkodik. A
     *                       tektonra a rovar regisztálásra kerül a konstruktor
     *                       lefutásakor.
     */
    public Insect(int playerID, Tecton startingTecton) {
        super();

        insectologistID = playerID;
        tecton = startingTecton;
        canMove = true;
        canCut = true;
        canCutCounter = 0;
        canMoveCounter = 0;
        sporesEaten = 0;

        tecton.registerInsect(this);

        Game.getInstance().getRegistry().registerInsect(this);
    }

    public void die() {
        tecton.unregisterInsect(this);
        delete();
    }

    @Override
    protected void delete() {
        Game.getInstance().getRegistry().unregisterInsect(this);
        super.delete();
    }

    /**
     * Elvágja a bemenetként adott gombafonalat, amennyiben
     * 
     * @param mt Az elvágandó gombafonál
     * @throws Exception Ha az elvágandó fonál nincs a rovar tektonján, vagy ha a
     *                   rovar nem tud vágni
     */
    public void cutMushroomThread(MushroomThread mt) throws Exception {
        if (canCut) {
            if (tecton == mt.getTecton()) {
                mt.setCutState(CutState.CUT);
                return;
            }
            throw new Exception("Insect can't cut thread that is not on it's tecton");
        }

        throw new Exception("Insect can't cut on this round");
    }

    /**
     * Átlépteti a rovart a megadott céltektonra, amennyiben nincs
     * letiltva a mozgás, a céltekton szomszédos a jelenlegi tektonnal, és van a két
     * tekton között kifejlett, nem elvágott gombafonál..
     * 
     * @param t A céltekton
     * @throws Exception ha az átlépési követelmények nem teljesülnek.
     */
    public void moveToTecton(Tecton t) throws Exception {
        boolean isNeighbour = tecton.isNeighbour(t);
        boolean isConnected = tecton.verifyConnection(t);

        if (!isNeighbour) {
            throw new Exception("Selected tecton is not neighbour");
        }

        if (!isConnected) {
            throw new Exception("Selected tecton is not connected with threads");
        }

        if (!canMove) {
            throw new Exception("Selected insect can't move this round");
        }

        tecton.unregisterInsect(this);
        t.registerInsect(this);

        tecton = t;

        /*
         * if (isNeighbour && isConnected && canMove) {
         * tecton.unregisterInsect(this);
         * t.registerInsect(this);
         * 
         * tecton = t;
         * } else {
         * throw new Exception(
         * "moveToTecton failed: isNeighbour=" + isNeighbour +
         * " isConnected=" + isConnected
         * + " canMove=" + canMove);
         * }
         */
    }

    /**
     * A rovar megeszi a vele azonos tektonon lévő egyik spórát.
     *
     * @throws Exception amennyiben nincs spóra a tektonon, a
     *                   getRandomSpore() kivétele feljebb halad.
     */
    public Effect eatMushroomSpore() throws Exception {
        MushroomSpore spore = tecton.getRandomSpore();
        tecton.removeSpore(spore);
        sporesEaten++;
        return spore.applyEffect(this);
    }


    public int getSporesEaten() {return sporesEaten;}
    /**
     * A rovar értesíti a kontrollert, hogy visszakapja az összes akcióját.
     */
    public void refreshActions() throws Exception {
        Game.getInstance().onInsectRefreshActions(this);
    }

    /**
     * A rovar értesíti a kontrollert, hogy már nem végezhet több akciót ebben a
     * körben.
     */
    public void exhaustActions() throws Exception {
        Game.getInstance().onInsectExhaustActions(this);
    }

    /**
     * A rovar a kör végén frissíti az állapotát.
     * <p>
     * Amennyiben a canMove hamis volt, nem kapja vissza az akcióit a következő
     * körre, de viszont a {@link #canMove} visszabillen igazba. Így a következő
     * utáni körben újra fog tudni lépni a rovar.
     * <p>
     * Amennyiben a vágás le volt tiltva, a {@link #canCutCounter} értéke a kör
     * végén
     * csökken. Amikor eléri a 0-t, a {@link #canCut} újra igazba billen.
     */
    @Override
    public void onEndOfTheRound() {
        if (canMoveCounter != 0) {
            canMoveCounter--;
        }

        if (canMoveCounter == 0 && !canMove) {
            setCanMove(true);
        }

        if (!canCut && canCutCounter == 0) {
            setCanCut(true);
        }

        if (canCutCounter != 0) {
            canCutCounter--;
        }
    }

    /**
     * Visszatér egy rovar állapotát leíró stringgel.
     * Debug célból használandó.
     * 
     * @return A tesztelőnek megjelenítendő debug string.
     * @see projlab.fungorium.interfaces.PrintableState
     */
    @Override
    public String getStateString() {
        StringBuilder resultBuilder = new StringBuilder();

        resultBuilder.append("Insect(tecton=")
                .append(tecton.toString());

        if (!canMove) {
            resultBuilder.append(", !canMove");
        }

        if (!canCut) {
            resultBuilder.append(", !canCut");
        }

        if (isCounterSet()) {
            resultBuilder.append(", counter=").append(canCutCounter);
        }

        resultBuilder.append(")");

        return resultBuilder.toString();
    }

    /**
     * Beállítja a canMove értékét.
     * 
     * @param newCanMove
     */
    public void setCanMove(boolean newCanMove) {
        if (canMove == newCanMove)
            return;

        canMove = newCanMove;
        canMoveCounter = !canMove ? CANMOVE_COUNTER_DEFAULT_VALUE : 0;
    }

    /**
     * Beállítja a canCut értékét.
     * 
     * @param newCanCut
     */
    public void setCanCut(boolean newCanCut) {
        canCut = newCanCut;
    }

    /**
     * Beállítja a {@link #canCut}-hoz tartozó számlálót egy fix értékre.
     * Ennyi körbe telik majd, amég a rovar újra fonalat vághat.
     */
    public void setCounter() {
        canCutCounter = CANCUT_COUNTER_DEFAULT_VALUE;
    }

    public int getInsectologistID() {
        return insectologistID;
    }

    public Tecton getTecton() {
        return tecton;
    }

    public boolean getCanMove() {
        return canMove;
    }

    public boolean getCanCut() {
        return canCut;
    }

    /**
     * Visszatér arra, hogy a counter változó nem 0 értékre van-e állítva, azaz
     * éppen számol-e vissza a rovar.
     * 
     * @return
     */
    public boolean isCounterSet() {
        return canCutCounter != 0;
    }

    @Override
    public String getOutputString() {
        StringBuilder sb = new StringBuilder("INSECT ");

        sb.append(getID() + " ");
        sb.append(insectologistID + " ");
        sb.append(tecton.getID() + " ");
        sb.append((canCut ? 1 : 0) + " ");
        sb.append((canMove ? 1 : 0) + " ");
        sb.append(canCutCounter);

        return sb.toString();
    }
}
