package projlab.fungorium.models;

import projlab.fungorium.interfaces.TurnAware;

public class Insect implements TurnAware {
	private boolean canMove;
	private boolean canCut;
	private int counter;

	private Tecton tecton;

	public Insect(Tecton startingTecton) {
		tecton = startingTecton;
		canMove = true;
		canCut = true;
		counter = 0;
	}

	public void cutMushroomThread(MushroomThread mt) {
		throw new UnsupportedOperationException("Unimplemented method");
	}

	public void moveToTecton(Tecton t) {
		// TODO: typo fix
		boolean isNeighbour = tecton.isNegihbour(t);
		boolean isConnected = tecton.verifyConnection(t);

		if (isNeighbour && isConnected && canMove) {
			tecton.unregisterInsect(this);
			t.registerInsect(this);

			tecton = t;
		} else {
			throw new RuntimeException(
					"moveToTecton failed: isNeighbour=" + isNeighbour +
							" isConnected=" + isConnected
							+ " canMove=" + canMove);
		}
	}

	public void eatMushroomSpore() {
		// TODO: tekton műveletek ~tams
		throw new UnsupportedOperationException("Unimplemented method");

		// MushroomSpore spore = tecton.getRandomSpore();
		// spore.applyEffect(this);
	}

	public void refreshActions() {
		throw new UnsupportedOperationException("Unimplemented method");
	}

	public void exhaustActions() {
		throw new UnsupportedOperationException("Unimplemented method");
	}

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

	public void setCanMove(boolean newCanMove) {
		canMove = newCanMove;
	}

	public void setCanCut(boolean newCanCut) {
		canCut = newCanCut;
	}

	public void setCounter() {
		// TODO ne legyen hardcodeolva? ~tams
		counter = 5;
	}
}
