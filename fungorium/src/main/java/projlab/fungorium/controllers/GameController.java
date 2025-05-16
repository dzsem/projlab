package projlab.fungorium.controllers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import projlab.fungorium.actions.game.PassAction;
import projlab.fungorium.interfaces.GameComponentViewVisitor;
import projlab.fungorium.models.Game;
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
import projlab.fungorium.views.gamecomponents.SporeView;
import projlab.fungorium.views.gamecomponents.TectonView;
import projlab.fungorium.views.gamecomponents.ThreadView;
import projlab.fungorium.windowing.game.MainPanel;

import java.awt.Point;

public class GameController implements GameComponentViewVisitor {
	public GameController(List<Insectologist> insectologists, List<Mycologist> mycologists) {
		canvasWidth = 512; // todo: a view visszaadhatná rögtön
		canvasHeight = 512;

		insectologistController = new InsectologistController(this);
		mycologistController = new MycologistController(this);

		this.insectologists = insectologists;
		this.mycologists = mycologists;

		setInsectologistIdx(0);
		setMycologistIdx(0);

		selectedInsect = null;
		selectedThread = null;
		selectedBody = null;
		selectedTecton = null;

		tectonViews = new ArrayList<>();
		gameComponentViews = new ArrayList<>();
		nextRoundAction = new PassAction(this);

		buildMap(insectologists.size() + mycologists.size());

		activeType = PlayerType.MYCOLOGIST;
	}

	private int canvasWidth;
	private int canvasHeight;

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

	private MainPanel mainPanel;

	private Random random = new Random();

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

	public void handleClick(int x, int y) {
		throw new UnsupportedOperationException("handleClick not implemented");
		// TODO: implement
	}

	public void redraw() {
		updateGameComponents();

		mainPanel.setGameComponents(gameComponentViews);

		mainPanel.revalidate();
		mainPanel.repaint();
	}

	private Map<Integer, TectonView> calculateTectonViewMap() {
		List<Tecton> tectons = Game.getInstance().getRegistry().getTectons();
		int gridSize = (int) Math.ceil(Math.sqrt(tectons.size()));

		int pixelsPerCell = Math.min(canvasWidth, canvasHeight) / gridSize;
		int centerOffset = pixelsPerCell / 2;

		Map<Integer, TectonView> tectonViewMap = new HashMap<>();

		for (int i = 0; i < tectons.size(); i++) {
			int gridX = i % gridSize;
			int gridY = i / gridSize;

			Point position = new Point(gridX * pixelsPerCell + centerOffset, gridY * pixelsPerCell + centerOffset);

			Tecton tecton = tectons.get(i);
			TectonView tectonView = new TectonView(
					tectons.get(i),
					position,
					new Point(pixelsPerCell, pixelsPerCell));

			tectonViewMap.put(tecton.getID(), tectonView);
			gameComponentViews.add(tectonView);
		}

		return tectonViewMap;
	}

	private void updateTectonViews(Map<Integer, TectonView> tectonViewMap) {
		for (var tectonView : tectonViewMap.values()) {
			gameComponentViews.add(tectonView);
		}
	}

	private void updateInsectViews(Map<Integer, TectonView> tectonViewMap) {
		for (var insect : Game.getInstance().getRegistry().getInsects()) {
			Tecton insectTecton = insect.getTecton();
			TectonView insectTectonView = tectonViewMap.get(insectTecton.getID());

			int radius = (int) (insectTectonView.getSize().x * 0.5 * InsectView.RADIUS_MULTIPLIER);

			Point position = insectTectonView.calculateMobileObjectPosition(insectTecton, radius);

			gameComponentViews.add(new InsectView(insect, position));
		}
	}

	private void updateGameComponents() {
		gameComponentViews.clear();

		Map<Integer, TectonView> tectonViewMap = calculateTectonViewMap();

		updateTectonViews(tectonViewMap);
		updateInsectViews(tectonViewMap);

		/*
		 * for (var mb : Game.getInstance().getRegistry().getMushroomBodies()) {
		 * gameComponentViews.add(new MushroomBodyView(mb));
		 * }
		 * 
		 * for (var thread : Game.getInstance().getRegistry().getMushroomThreads()) {
		 * gameComponentViews.add(new ThreadView(thread));
		 * }
		 * 
		 * for (var spore : Game.getInstance().getRegistry().getMushroomSpores()) {
		 * gameComponentViews.add(new SporeView(spore));
		 * }
		 */
	}

	public void setMainPanel(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
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
