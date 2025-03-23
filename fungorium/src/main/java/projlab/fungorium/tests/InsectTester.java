package projlab.fungorium.tests;

import projlab.fungorium.models.*;
import projlab.fungorium.utilities.Logger;

/**
 * A rovarok egyes teszteit egységbe záró osztály. Kizárólag statikus
 * függvényeket tartalmaz.
 * <p>
 * Tartalmazott tesztek:
 * <ul>
 * <li>{@link #test_eatMushroomSpore1()}: EatMushroomSpore success</li>
 * <li>{@link #test_eatMushroomSpore2()}: EatMushroomSpore fail (no spore on
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

	/**
	 * Az EatMushroomSpore sikeres tesztesetének inicializációja.
	 * (ld.: Kommunikációs diagram)
	 */
	private static class EatMushroomSporeSuccessModel {
		public Tecton t1;
		public Insect ins;
		public MushroomSpore spore;

		public EatMushroomSporeSuccessModel() {
			t1 = new Tecton();
			ins = new Insect(t1);
			spore = new MushroomSpore(t1);
		}
	}

	/**
	 * Az EatMushroomSpore sikertelen tesztesetének inicializációja.
	 * (ld. kommunikációs diagram)
	 */
	private static class EatMushroomSporeFailModel {
		public Tecton t1;
		public Insect ins;

		public EatMushroomSporeFailModel() {
			t1 = new Tecton();
			ins = new Insect(t1);
		}
	}

	/**
	 * Az "EatMushroomSpore success" teszt szekvenciadiagramot megvalósító függvény.
	 * <p>
	 * Itt a rovar a vele azonos tektonon lévő egyetlen spórát eszi meg. Az effekt
	 * hatása nem számít, a lényeg, hogy az eatMushroomSpore függvény ne dobjon
	 * kivételt, és ne legyen spóra a tektonon a művelet elvégézése után.
	 */
	public static void test_eatMushroomSpore1() {
		EatMushroomSporeSuccessModel model = new EatMushroomSporeSuccessModel();

		try {
			Logger.print("void", "eatMushroomSpore");
			model.ins.eatMushroomSpore();
		} catch (Exception exc) {
			Logger.printError("EatMushroomSpore threw exception when it wasn't supposed to.");
		}

		if (model.t1.getSporeCount() != 0) {
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
	public static void test_eatMushroomSpore2() {
		EatMushroomSporeFailModel model = new EatMushroomSporeFailModel();

		boolean eatMushroomSporeThrew = false;
		try {
			Logger.print("void", "eatMushroomSpore");
			model.ins.eatMushroomSpore();
		} catch (Exception exc) {
			eatMushroomSporeThrew = true;
		}

		if (!eatMushroomSporeThrew) {
			Logger.printError("EatMushroomSpore didn't throw when there were no spores on the tecton");
		}
	}
}
