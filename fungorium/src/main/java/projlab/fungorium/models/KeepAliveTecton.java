package projlab.fungorium.models;

import java.util.List;

/**
 * Annak a tektonnak a modellje, ami az összes rajta lévő gombafonalat életben
 * tartja.
 * 
 * @see #keepThreadsAlive()
 */
public final class KeepAliveTecton extends Tecton {
	/**
	 * Konstuktor. Létrehoz egy tektont, aminek nincsenek szomszédjai.
	 */
	public KeepAliveTecton() {
		super();
	}

	/**
	 * Létrehoz egy új Tectont, a megadott szomszédokkal.
	 * 
	 * @param neighbour Szomszédos tektonok.
	 */
	public KeepAliveTecton(List<Tecton> neighbours) {
		super(neighbours);
	}

	/**
	 * Életben tartja az összes tektonra beregisztrált gombafonalat.
	 */
	private void keepThreadsAlive() {
		for (MushroomThread thread : mushroomThreads) {
			thread.resetTurnsToDie();
		}
	}

	@Override
	public void onEndOfTheRound() {
		keepThreadsAlive();

		super.onEndOfTheRound();
	}

	@Override
	public String getOutputString() {
		StringBuilder sb = new StringBuilder("KEEPALIVETECTON ");
		sb.append(getID() + " ");
		sb.append(neighbours.size() + " ");
		sb.append(hasBody() ? mushroomBody.getID() + " " : -1 + " ");
		sb.append(mushroomThreads.size() + " ");
		sb.append(mushroomSpores.size());

		return sb.toString();
	}
}
