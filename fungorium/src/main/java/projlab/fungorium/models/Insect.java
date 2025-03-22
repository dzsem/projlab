package projlab.fungorium.models;

import projlab.fungorium.interfaces.TurnAware;

/**
 * A rovarászok által irányított rovarokat megvalósító osztály.
 * <p>
 * Számon tartja a tektont, amin van, illetve van (a spórák és a
 * körök eltelése által változtatott) állapota.
 */
public class Insect implements TurnAware {
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
	 * Amennyiben nem, csak akkor billen vissza igazba, amikor a {@link #counter}
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
	private int counter;

	/**
	 * A tekton, amin jelenleg a rovar áll. A rovar mindig regisztrálja magát a
	 * jelenlegi tektonon, és mindig pontosan egy tektonon van regisztrálva.
	 * 
	 * @see Tecton#registerInsect(Insect)
	 */
	private Tecton tecton;

	/**
	 * Létrehoz egy rovart, a megadott tektonon.
	 *
	 * @param startingTecton Az a tekton, amin a rovar eredetileg tartózkodik. A
	 *                       tektonra a rovar regisztálásra kerül a konstruktor
	 *                       lefutásakor.
	 */
	public Insect(Tecton startingTecton) {
		tecton = startingTecton;
		canMove = true;
		canCut = true;
		counter = 0;

		tecton.registerInsect(this);
	}

	/**
	 * @todo
	 * 
	 * @param mt
	 */
	public void cutMushroomThread(MushroomThread mt) {
		throw new UnsupportedOperationException("Unimplemented method");
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
		// TODO: typo fix
		boolean isNeighbour = tecton.isNegihbour(t);
		boolean isConnected = tecton.verifyConnection(t);

		if (isNeighbour && isConnected && canMove) {
			tecton.unregisterInsect(this);
			t.registerInsect(this);

			tecton = t;
		} else {
			throw new Exception(
					"moveToTecton failed: isNeighbour=" + isNeighbour +
							" isConnected=" + isConnected
							+ " canMove=" + canMove);
		}
	}

	/**
	 * A rovar megeszi a vele azonos tektonon lévő egyik spórát.
	 *
	 * @throws RuntimeException amennyiben nincs spóra a tektonon, a
	 *                          getRandomSpore() kivétele feljebb halad.
	 */
	public void eatMushroomSpore() throws Exception {
		// TODO: tekton műveletek ~tams
		throw new UnsupportedOperationException("Unimplemented method");

		// MushroomSpore spore = tecton.getRandomSpore();
		// spore.applyEffect(this);
	}

	/**
	 * A rovar értesíti a kontrollert, hogy visszakapja az összes akcióját.
	 */
	public void refreshActions() {
		throw new UnsupportedOperationException("Unimplemented method");
	}

	/**
	 * A rovar értesíti a kontrollert, hogy már nem végezhet több akciót ebben a
	 * körben.
	 */
	public void exhaustActions() {
		throw new UnsupportedOperationException("Unimplemented method");
	}

	/**
	 * A rovar a kör végén frissíti az állapotát.
	 * <p>
	 * Amennyiben a canMove hamis volt, nem kapja vissza az akcióit a következő
	 * körre, de viszont a {@link #canMove} visszabillen igazba. Így a következő
	 * utáni körben újra fog tudni lépni a rovar.
	 * <p>
	 * Amennyiben a vágás le volt tiltva, a {@link #counter} értéke a kör végén
	 * csökken. Amikor eléri a 0-t, a {@link #canCut} újra igazba billen.
	 */
	@Override
	public void onEndOfTheRound() {
		if (canMove) {
			refreshActions();
		} else {
			setCanMove(true);
		}

		if (!canCut && counter == 0) {
			setCanCut(true);
		}

		// TODO ez körönként csökken? ~tams
		if (counter != 0) {
			counter--;
		}
	}

	/**
	 * Beállítja a canMove értékét.
	 * 
	 * @param newCanMove
	 */
	public void setCanMove(boolean newCanMove) {
		canMove = newCanMove;
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
		// TODO ne legyen hardcodeolva? ~tams
		counter = 5;
	}
}
