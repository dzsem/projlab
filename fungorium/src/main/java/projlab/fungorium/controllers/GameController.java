package projlab.fungorium.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import projlab.fungorium.actions.game.PassAction;
import projlab.fungorium.interfaces.GameComponentViewVisitor;
import projlab.fungorium.models.Game;
import projlab.fungorium.models.GameObject;
import projlab.fungorium.models.Insect;
import projlab.fungorium.models.KeepAliveTecton;
import projlab.fungorium.models.MushroomBody;
import projlab.fungorium.models.MushroomThread;
import projlab.fungorium.models.SingleThreadTecton;
import projlab.fungorium.models.Tecton;
import projlab.fungorium.models.player.*;
import projlab.fungorium.utilities.ConnectionMap;
import projlab.fungorium.views.gamecomponents.*;
import projlab.fungorium.windowing.game.BottomPanel;
import projlab.fungorium.windowing.game.MainPanel;
import projlab.fungorium.windowing.game.SidePanel;

import java.awt.Point;

public class GameController implements GameComponentViewVisitor {
	private static final int GRID_SPACING_PX = 25;
	private static final int GRID_MARGIN_PX = 50;

	public GameController(List<Insectologist> insectologists, List<Mycologist> mycologists) {
		insectologistController = new InsectologistController(this);
		mycologistController = new MycologistController(this);

		this.insectologists = insectologists;
		this.mycologists = mycologists;

		setInsectologistIdx(0);
		setMycologistIdx(0);

		gameComponentViews = new ArrayList<>();

		selectedInsect = null;
		selectedThread = null;
		selectedBody = null;
		selectedTecton = null;

		tectonViews = new ArrayList<>();
		nextRoundAction = new PassAction(this);

		buildMap(insectologists.size() + mycologists.size());

		activeType = PlayerType.MYCOLOGIST;

		currentTurn = 1;

		setupGameActionCallbacks();
	}

	private PlayerType activeType;

	private int insectologistIdx;
	private int mycologistIdx;

	private List<Insectologist> insectologists;
	private List<Mycologist> mycologists;

	private InsectologistController insectologistController;
	private MycologistController mycologistController;

	private List<GameComponentView<? extends GameObject>> gameComponentViews;

	private InsectView selectedInsect;
	private ThreadView selectedThread;
	private MushroomBodyView selectedBody;
	private TectonView selectedTecton;

	private List<TectonView> tectonViews;

	private PassAction nextRoundAction;

	private MainPanel mainPanel;
	private SidePanel sidePanel;
	private BottomPanel bottomPanel;

	private Random random = new Random();

	private int currentTurn;

	public int getMycologistsSize() {
		return mycologists.size();
	}

	public int getInsectologistsSize() {
		return insectologists.size();
	}

	// @formatter:off
	public int getCurrentTurn() { return currentTurn; }

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
	public PassAction getNextRoundAction() { return nextRoundAction; }

	public void setActiveType(PlayerType type) {
		if (activeType == type)
			return;

		activeType = type;

		sidePanel.update(getActions());
		sidePanel.revalidate();
		sidePanel.repaint();
	}
	// @formatter:on

	private void setupGameActionCallbacks() {
		Game.getInstance().setInsectExhaustActionHandler((Insect insect) -> {
			Player currentPlayer = getCurrentPlayer();
			if (currentPlayer.getID() != insect.getInsectologistID())
				throw new Exception("Failed to exhaust action: Insect is not the current player's.");

			currentPlayer.exhaustAction();
		});

		Game.getInstance().setInsectRefreshActionsHandler((Insect insect) -> {
			Player currentPlayer = getCurrentPlayer();
			if (currentPlayer.getID() != insect.getInsectologistID())
				throw new Exception("Failed to refresh actions: Insect is not the current player's.");

			currentPlayer.refreshActions();
		});
	}

	public Player getCurrentPlayer() {
		return activeType == PlayerType.INSECTOLOGIST
				? insectologists.get(insectologistIdx)
				: mycologists.get(mycologistIdx);
	}

	public void setInsectologistIdx(int idx) {
		insectologistIdx = idx;
		if (insectologistIdx < insectologists.size())
			insectologistController.updateActive(insectologists.get(insectologistIdx));
	}

	public void setMycologistIdx(int idx) {
		mycologistIdx = idx;
		if (mycologistIdx < mycologists.size())
			mycologistController.updateActive(mycologists.get(mycologistIdx));
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
	 * elég == -vel checkolni, mert a program, utána nem vált vissza arra a
	 * csapatra, amelyik elérte a megfelelő size-t
	 */
	public boolean checkIfLastActive() {
		return mycologistIdx == mycologists.size() && insectologistIdx == insectologists.size();
	}

	public void showError(Exception e) {
		JOptionPane.showMessageDialog(null, "Error:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	}

	public void handleClick(int x, int y) {
		GameComponentView<? extends GameObject> gameView = null;
		for (GameComponentView<? extends GameObject> view : gameComponentViews) {
			if (view.isPointInside(new Point(x, y))) {
				if (gameView == null) {
					gameView = view;
				}
				if (gameView.getDrawPriority() > view.getDrawPriority()) {
					gameView = view;
				}
			}
		}
		if (gameView != null) {
			gameView.accept(this);
		}
	}

	public void redraw() {
		if (mainPanel == null) {
			return;
		}

		GameLayoutGenerator generator = new GameLayoutGenerator(
				GRID_SPACING_PX, GRID_MARGIN_PX,
				mainPanel.getWidth(), mainPanel.getHeight());

		int playerID = -1;

		switch (activeType) {
			case INSECTOLOGIST:
				playerID = insectologists.get(insectologistIdx).getID();
				break;

			case MYCOLOGIST:
				playerID = mycologists.get(mycologistIdx).getID();
				break;

			default:
				break;
		}

		generator.generate(activeType, playerID);
		gameComponentViews = generator.getGameComponentViews();

		mainPanel.setDrawables(generator.getDrawables());

		bottomPanel.update();
		mainPanel.revalidate();
		mainPanel.repaint();
	}

	public void onNextRound() {
		selectedBody = null;
		selectedInsect = null;
		selectedTecton = null;
		selectedThread = null;

		for (var action : getActions()) {
			action.setEnabled(true);
		}

	}

	public void setMainPanel(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

	public void setSidePanel(SidePanel sidePanel) {
		this.sidePanel = sidePanel;
	}

	public void setBottomPanel(BottomPanel bottomPanel) {
		this.bottomPanel = bottomPanel;
	}

	public void nextRound() {
		Game.getInstance().nextRound();

		for (var insectologist : insectologists)
			insectologist.refreshActions();

		for (var mycologist : mycologists)
			mycologist.refreshActions();

		currentTurn = currentTurn + 1;
	}

	public List<AbstractAction> getActions() {
		List<AbstractAction> actions = new ArrayList<>();

		actions.add(nextRoundAction);

		switch (activeType) {
			case MYCOLOGIST:
				actions.addAll(mycologistController.getActions());
				break;

			case INSECTOLOGIST:
				actions.addAll(insectologistController.getActions());
				break;

			default:
				break;
		}

		return actions;
	}

	/**
	 * Elkészíti a játék pályát viewokkal és game objectekkel a játékosszám alapján.
	 * <p>
	 * Tektonokat, hoz létre és ezekre véletlenszerűen felhelyezi az insecteket és a
	 * gombákat
	 * 
	 * @param numOfPlayers A játékosok száma
	 */
	private void buildMap(int numOfPlayers) {
		List<Tecton> tectons = Game.getInstance().buildMap(numOfPlayers);

		// Create mushroom bodies and threads
		for (Mycologist mycologist : mycologists) {
			int tectonIdx = random.nextInt(tectons.size());
			Tecton tecton = tectons.get(tectonIdx);

			new MushroomBody(tecton, mycologist.getID());
			new MushroomThread(tecton, mycologist.getID());

			var ktecton = new KeepAliveTecton();
			ktecton.registerNeighbour(tecton);

			tectons.remove(tectonIdx);
		}

		// Create insects
		for (Insectologist insectologist : insectologists) {
			int tectonIdx = random.nextInt(tectons.size());
			Tecton tecton = tectons.get(tectonIdx);

			new Insect(insectologist.getID(), tecton);

			tectons.remove(tectonIdx);
		}
	}

	@Override
	public void visit(TectonView tectonView) {
		selectedTecton = tectonView;
		redraw();
		sidePanel.add(new JTextArea("Tecton selected with id: " + tectonView.getGameObject().getID()));
	}

	@Override
	public void visit(ThreadView threadView) {
		selectedThread = threadView;
		redraw();
		sidePanel.add(new JTextArea("Thread selected with id: " + threadView.getGameObject().getID() + "\n"
				+ "Belonging to player: " + threadView.getGameObject().getMushroomID()));
	}

	@Override
	public void visit(InsectView insectView) {
		selectedInsect = insectView;
		redraw();
		sidePanel.add(new JTextArea("Insect selected with id: " + insectView.getGameObject().getID() + "\n"
				+ "Belonging to player: " + insectView.getGameObject().getInsectologistID()));
	}

	@Override
	public void visit(MushroomBodyView mushroomBodyView) {
		selectedBody = mushroomBodyView;
		redraw();
		sidePanel.add(new JTextArea("Body selected with id: " + mushroomBodyView.getGameObject().getID() + "\n"
				+ "Belonging to player: " + mushroomBodyView.getGameObject().getMushroomID()));
	}

	public void showMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.INFORMATION_MESSAGE);
	}

}
