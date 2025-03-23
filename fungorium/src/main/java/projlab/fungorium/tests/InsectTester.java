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
	private static Tecton t3;
	private static Tecton t4;
	private static MushroomThread mt1;
	private static MushroomThread mt2;
	private static MushroomThread mt3;
	private static MushroomSpore spore;
	private static Insect insect;

	private static void initClearVariables() {
		t1 = null;
		t2 = null;
		t3 = null;
		t4 = null;
		mt1 = null;
		mt2 = null;
		mt3 = null;
		spore = null;
		insect = null;
	}

	/**
	 * Az EatMushroomSpore sikeres tesztesetének inicializációja.
	 * (ld.: Kommunikációs diagram)
	 */
	private static void initMushroomSporeSuccess() {
		initClearVariables();

		t1 = new Tecton();
		insect = new Insect(t1);
		spore = new MushroomSpore(t1);
	}

	/**
	 * Az EatMushroomSpore sikertelen tesztesetének inicializációja.
	 * (ld. kommunikációs diagram)
	 */
	private static void initMushroomSporeFail() {
		initClearVariables();

		t1 = new Tecton();
		insect = new Insect(t1);
		spore = null;
	}

	/**
	 * A MoveToTecton teszteseteinek inicializációi.
	 * (ld. kommunikációs diagram)
	 */
	private static void initMoveToTecton() {
		initClearVariables();

		t1 = new Tecton();
		t2 = new Tecton();
		t3 = new Tecton();
		t4 = new Tecton();

		t1.registerNeighbour(t2);
		t1.registerNeighbour(t3);

		t4.registerNeighbour(t3);

		insect = new Insect(t1);
		mt1 = new MushroomThread(t1);

		try {
			mt2 = mt1.createConnection(t3);
			mt3 = mt2.createConnection(t4);
		} catch (Exception exc) {
			Logger.printError("Caught exception in initialization: " + exc.getMessage());
		}
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
			Logger.printError("Exception: " + exc.getMessage());
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

	public static void testMoveToTecton1() {
		initMoveToTecton();

		try {
			insect.moveToTecton(t3);
		} catch (Exception exc) {
			Logger.printError("MoveToTecton threw, but it shouldn't have.");
			Logger.printError("Exception: " + exc.getMessage());
		}
	}
}
