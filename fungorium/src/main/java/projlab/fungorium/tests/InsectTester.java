package projlab.fungorium.tests;

import projlab.fungorium.models.*;
import projlab.fungorium.models.MushroomThread.GrowState;
import projlab.fungorium.models.effects.StunEffect;
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
 * <li>{@link #testMoveToTecton1()}: MoveToTecton success</li>
 * <li>{@link #testMoveToTecton2()}: MoveToTecton fail (non-neighboring
 * tecton)</li>
 * <li>{@link #testMoveToTecton3()}: MoveToTecton fail (unconnected tecton)</li>
 * <li>{@link #testMoveToTecton4()}: MoveToTecton fail (while stunned)</li>
 * </ul>
 */
public class InsectTester {
	/** Ne példányosítsuk. */
	private InsectTester() {
	}

	private static final int MUSHROOM_ID = 0;
	private static final int INSECT_ID = 1;

	private static Tecton t1;
	private static Tecton t2;
	private static Tecton t3;
	private static Tecton t4;
	private static MushroomThread mt1;
	private static MushroomThread mt2;
	private static MushroomThread mt3;
	private static MushroomSpore spore;
	private static Insect insect;

	/**
	 * Visszanulláz minden statikus változót, hogy egy teszt lefutása ne hathasson
	 * ki egy másikéra.
	 */
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
		insect = new Insect(INSECT_ID, t1);
		spore = new MushroomSpore(t1);
	}

	/**
	 * Az EatMushroomSpore sikertelen tesztesetének inicializációja.
	 * (ld. kommunikációs diagram)
	 */
	private static void initMushroomSporeFail() {
		initClearVariables();

		t1 = new Tecton();
		insect = new Insect(INSECT_ID, t1);
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

		insect = new Insect(INSECT_ID, t1);
		mt1 = new MushroomThread(t1, MUSHROOM_ID);

		mt1.setGrowState(GrowState.GROWN);

		try {
			mt2 = mt1.createConnection(t3);
			mt2.setGrowState(GrowState.GROWN);

			mt3 = mt2.createConnection(t4);
			mt3.setGrowState(GrowState.GROWN);
		} catch (Exception exc) {
			Logger.printError("Caught exception in initialization: " + exc.getMessage());
		}
	}

	/**
	 * A MoveToTecton bénított tesztesetének inicializációja.
	 */
	private static void initMoveToTectonStun() {
		initClearVariables();

		t1 = new Tecton();
		t2 = new Tecton();

		t1.registerNeighbour(t2);

		insect = new Insect(INSECT_ID, t1);

		mt1 = new MushroomThread(t1, MUSHROOM_ID);
		mt1.setGrowState(GrowState.GROWN);

		try {
			mt2 = mt1.createConnection(t2);
			mt2.setGrowState(GrowState.GROWN);
		} catch (Exception exc) {
			Logger.printError("Caught exception in initialization: " + exc.getMessage());
		}

		StunEffect stunEffect = new StunEffect();

		try {
			stunEffect.applyEffect(insect);
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
	 * 
	 * @see #initMushroomSporeSuccess()
	 */
	public static void testEatMushroomSpore1() {
		initMushroomSporeSuccess();

		try {
			Logger.printState(insect);

			Logger.print("void", "eatMushroomSpore");
			insect.eatMushroomSpore();

			Logger.printState(insect);
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
	 * 
	 * @see #initMushroomSporeFail()
	 */
	public static void testEatMushroomSpore2() {
		initMushroomSporeFail();

		boolean eatMushroomSporeThrew = false;
		try {
			Logger.printState(insect);

			Logger.print("void", "eatMushroomSpore");
			insect.eatMushroomSpore();

			Logger.printState(insect);
		} catch (Exception exc) {
			eatMushroomSporeThrew = true;
		}

		if (!eatMushroomSporeThrew) {
			Logger.printError("EatMushroomSpore didn't throw when there were no spores on the tecton");
		}
	}

	/**
	 * A "MoveToTecton Success" teszt szekvenciadiagramot megvalósító függvény.
	 * <p>
	 * Itt a rovar egy gombafonálon sikeresen átlép egy vele szomszédos tektonra,
	 * amihez köti egy gombafonál. A rovar nincsen megbénítva.
	 * 
	 * @see #initMoveToTecton()
	 */
	public static void testMoveToTecton1() {
		initMoveToTecton();

		try {
			Logger.printState(insect);

			Logger.print("void", "moveToTecton", t3.toString());
			insect.moveToTecton(t3);

			Logger.printState(insect);

			if (!insect.getTecton().equals(t3)) {
				Logger.printError("Insect.tecton didn't change after moveToTecton.");
			}
		} catch (Exception exc) {
			Logger.printError("MoveToTecton threw, but it shouldn't have.");
			Logger.printError("Exception: " + exc.getMessage());
		}
	}

	/**
	 * A "MoveToTecton fail (non-neighboring tecton)" teszt szekvenciadiagramot
	 * megvalósító függvény.
	 * <p>
	 * A rovar megpróbál átlépni egy másik tektonra, ahová egyébként vezet
	 * gombafonál, de a rovar tektonjának nem közvetlen szomszédja. Ezért a
	 * moveToTecton kivételt dob.
	 * 
	 * @see #initMoveToTecton()
	 */
	public static void testMoveToTecton2() {
		initMoveToTecton();

		boolean moveToTectonThrew = false;

		try {
			Logger.printState(insect);

			Logger.print("void", "moveToTecton", t4.toString());
			insect.moveToTecton(t4);

		} catch (Exception exc) {
			moveToTectonThrew = true;
		}

		Logger.printState(insect);

		if (!moveToTectonThrew) {
			Logger.printError("MoveToTecton didn't throw, as it was supposed to. " +
					"Target tecton was not a neighbour.");
		}
	}

	/**
	 * A "MoveToTecton fail (unconnected)" teszt szekvenciadiagramot megvalósító
	 * függvény.
	 * <p>
	 * Itt a rovar megpróbál átlépni egy szomszédos tektonra, amit nem köt össze
	 * gombafonal a rovar tektonjával; így a lépés nem sikerül, a moveToTecton
	 * kivételt dob.
	 * 
	 * @see #initMoveToTecton()
	 */
	public static void testMoveToTecton3() {
		initMoveToTecton();

		boolean moveToTectonThrew = false;

		try {
			Logger.printState(insect);

			Logger.print("void", "moveToTecton", t2.toString());
			insect.moveToTecton(t2);
		} catch (Exception exc) {
			moveToTectonThrew = true;
		}

		if (!moveToTectonThrew) {
			Logger.printError(
					"MoveToTecton didn't throw, as it was supposed to. " +
							"Target tecton was neighboring, but unconnected.");
		}
	}

	/**
	 * A "MoveToTecton fail (while stunned)" teszt szekvenciadiagramot megvalósító
	 * függvény.
	 * <p>
	 * A rovar megpróbál átlépni egy vele szomszédos tektonra, ahová köt gombafonál,
	 * viszont bénító effekt hatása alatt van (ld. kommunikációs diagram). Ezért a
	 * moveToTecton kivételt dob.
	 * 
	 * @see #initMoveToTectonStun()
	 */
	public static void testMoveToTecton4() {
		initMoveToTectonStun();

		boolean moveToTectonThrew = false;

		try {
			Logger.printState(insect);

			Logger.print("void", "moveToTecton", t2.toString());
			insect.moveToTecton(t2);
		} catch (Exception exc) {
			moveToTectonThrew = true;
		}

		Logger.printState(insect);

		if (!moveToTectonThrew) {
			Logger.printError(
					"MoveToTecton didn't throw, as it was supposed to. " +
							"Target was valid, but insect was stunned.");
		}
	}
}
