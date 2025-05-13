package projlab.fungorium.interfaces;

import projlab.fungorium.views.gamecomponents.InsectView;
import projlab.fungorium.views.gamecomponents.MushroomBodyView;
import projlab.fungorium.views.gamecomponents.SporeView;
import projlab.fungorium.views.gamecomponents.TectonView;
import projlab.fungorium.views.gamecomponents.ThreadView;

/**
 * A visitor pattern-t megvalósító osztály ({@link GameController}) interface-e.
 * A visit függvényeket az adott típusok (TectonView, ThreadView, stb...)
 * accept() függvényei hívják.
 */
public interface GameComponentViewVisitor {
	void visit(TectonView tectonView);

	void visit(ThreadView threadView);

	void visit(InsectView insectView);

	void visit(MushroomBodyView mushroomBodyView);

	void visit(SporeView sporeView);
}
