package projlab.fungorium.tests;

import projlab.fungorium.models.*;
import projlab.fungorium.utilities.Logger;

/**
 * A rovarok egyes teszteit egységbe záró osztály. Kizárólag statikus
 * függvényeket tartalmaz.
 * <p>
 * Tartalmazott tesztek:
 * <ul>
 * <li>{@link #testEatMushroomSpore1()}: EatMushroomSpore success</li>
 * <li>{@link #testEatMushroomSpore2()}: EatMushroomSpore fail (no spore on
 * tecton)</li>
 * <li>TODO: MoveToTecton success</li>
 * <li>TODO: MoveToTecton fail (non-neighboring tecton)</li>
 * <li>TODO: MoveToTecton fail (unconnected tecton)</li>
 * <li>TODO: MoveToTecton fail (while stunned)</li>
 * </ul>
 */
public class InsectTester {
	/** Ne példányosítsuk. */
	private InsectTester() {
	}

	private static Tecton t1;
	private static Tecton t2;
	private static MushroomSpore spore;
	private static Insect insect;

	private static void initClearVariables() {
		t1 = null;
		t2 = null;
		spore = null;
		insect = null;
	}

	private static void initMushroomSporeSuccess() {
		initClearVariables();

		t1 = new Tecton();
		insect = new Insect(t1);
		spore = new MushroomSpore(t1);
	}

	private static void initMushroomSporeFail() {
		initClearVariables();

		t1 = new Tecton();
		insect = new Insect(t1);
		spore = null;
	}

	/**
	 * Az "EatMushroomSpore success" teszt szekvenciadiagramot megvalósító függvény.
	 * <p>
	 * Itt a rovar a vele azonos tektonon lévő egyetlen spórát eszi meg. Az effekt
	 * hatása nem számít, a lényeg, hogy az eatMushroomSpore függvény ne dobjon
	 * kivételt, és ne legyen spóra a tektonon a művelet elvégézése után.
	 */
	public static void testEatMushroomSpore1() {
		initMushroomSporeSuccess();

		try {
			Logger.print("void", "eatMushroomSpore");
			insect.eatMushroomSpore();
		} catch (Exception exc) {
			Logger.printError("EatMushroomSpore threw exception when it wasn't supposed to.");
		}

		if (t1.getSporeCount() != 0) {
			Logger.printError("The only spore was eaten from the tecton, but sporeCount isn't 0.");
		}
	}

	/**
	 * Az "EatMushroomSpore Fail (no spore on tecton)" teszt szekvenciadiagramot
	 * megvalósító függvény.
	 * <p>
	 * Itt a rovar megpróbál spórát enni a saját tektonjáról, azonban nincs.
	 * Ilyenkor az eatMushroomSpore-nak kivételt kell dobnia.
	 */
	public static void testEatMushroomSpore2() {
		initMushroomSporeFail();

		boolean eatMushroomSporeThrew = false;
		try {
			Logger.print("void", "eatMushroomSpore");
			insect.eatMushroomSpore();
		} catch (Exception exc) {
			eatMushroomSporeThrew = true;
		}

		if (!eatMushroomSporeThrew) {
			Logger.printError("EatMushroomSpore didn't throw when there were no spores on the tecton");
		}
	}
}
