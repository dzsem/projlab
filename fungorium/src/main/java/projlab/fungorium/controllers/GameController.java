package projlab.fungorium.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import projlab.fungorium.actions.game.PassAction;
import projlab.fungorium.interfaces.GameComponentViewVisitor;
import projlab.fungorium.models.GameObject;
import projlab.fungorium.models.Insect;
import projlab.fungorium.models.MushroomBody;
import projlab.fungorium.models.MushroomThread;
import projlab.fungorium.models.Tecton;
import projlab.fungorium.models.player.Insectologist;
import projlab.fungorium.models.player.Mycologist;
import projlab.fungorium.models.player.PlayerType;
import projlab.fungorium.views.gamecomponents.GameComponentView;
import projlab.fungorium.views.gamecomponents.InsectView;
import projlab.fungorium.views.gamecomponents.MushroomBodyView;
import projlab.fungorium.views.gamecomponents.TectonView;
import projlab.fungorium.views.gamecomponents.ThreadView;
import java.awt.Point;

public class GameController implements GameComponentViewVisitor {
	// Ne példányosítsuk kívülről.
	private GameController() {
		insectologistController = new InsectologistController();
		mycologistController = new MycologistController();

		insectologists = new ArrayList<>();
		mycologists = new ArrayList<>();

		selectedInsect = null;
		selectedThread = null;
		selectedBody = null;
		selectedTecton = null;

		tectonViews = new ArrayList<>();
		gameComponentViews = new ArrayList<>();
		nextRoundAction = new PassAction(this);
	}

	private static GameController instance = null;

	private PlayerType activeType;

	private int insectologistIdx;
	private int mycologistIdx;

	private List<Insectologist> insectologists;
	private List<Mycologist> mycologists;

	private InsectologistController insectologistController;
	private MycologistController mycologistController;

	private InsectView selectedInsect;
	private ThreadView selectedThread;
	private MushroomBodyView selectedBody;
	private TectonView selectedTecton;

	private List<TectonView> tectonViews;

	private List<GameComponentView<? extends GameObject>> gameComponentViews;

	private PassAction nextRoundAction;

	// @formatter:off
	public PlayerType getActiveType() { return activeType; }
	public int getInsectologistIdx() { return insectologistIdx; }
	public int getMycologistIdx() { return mycologistIdx; }
	public InsectologistController getInsectologistController() { return insectologistController; }
	public MycologistController getMycologistController() { return mycologistController; }
	
	public Insect getSelectedInsect() { return selectedInsect != null ? selectedInsect.getGameObject() : null; }
	public MushroomThread getSelectedThread() { return selectedThread != null ? selectedThread.getGameObject() : null; }
	public MushroomBody getSelectedBody() { return selectedBody != null ? selectedBody.getGameObject() : null; }
	public Tecton getSelectedTecton() { return selectedTecton != null ? selectedTecton.getGameObject() : null; }
	
	public List<TectonView> getTectonViews() { return tectonViews; }
	public List<GameComponentView<? extends GameObject>> getGameComponentViews() { return gameComponentViews; }
	public PassAction getNextRoundAction() { return nextRoundAction; }

	public void setActiveType(PlayerType type) { activeType = type; }
	// @formatter:on

	public void setInsectologistIdx(int idx) {
		insectologistIdx = idx;
		insectologistController.updateActive(insectologists.get(insectologistIdx));
	}

	public void setMycologistIdx(int idx) {
		mycologistIdx = idx;
		mycologistController.updateActive(mycologists.get(mycologistIdx));
	}

	public void setPlayers(List<Insectologist> insectologists, List<Mycologist> mycologists) {
		this.insectologists = insectologists;
		this.mycologists = mycologists;

		// újraindításkor jól jöhet:
		setInsectologistIdx(0);
		setMycologistIdx(0);
	}

	public static GameController getInstance() {
		if (instance == null) {
			instance = new GameController();
		}

		return instance;
	}

	public Point getPoint(int x, int y) {
		return new Point(x, y);
	}

	/**
	 * Ellenőrzi, hogy az előbb elvégzett kis kör az utolsó volt-e a nagy körben.
	 * 
	 * Pl.: 3-3 player esetén:
	 * <code>
	 * ActiveType    M -> I -> M -> I -> M -> I
	 * Insectologist 0    0    1    1    2    2
	 * Mycologist    0    1    1    2    2    3
	 * </code>
	 * 
	 * TODO: megoldani nem egyenlő számú player típusokra,
	 * TODO: ezt jelenleg a PassAction tervezett logikája nem engedi meg! ~tams
	 */
	public boolean checkIfLastActive() {
		return mycologistIdx == mycologists.size() || insectologistIdx == insectologists.size();
	}

	public void showError(Exception e) {
		JOptionPane.showMessageDialog(null, "Error:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void visit(TectonView tectonView) {
		selectedTecton = tectonView;
	}

	@Override
	public void visit(ThreadView threadView) {
		selectedThread = threadView;
	}

	@Override
	public void visit(InsectView insectView) {
		selectedInsect = insectView;
	}

	@Override
	public void visit(MushroomBodyView mushroomBodyView) {
		selectedBody = mushroomBodyView;
	}

}
