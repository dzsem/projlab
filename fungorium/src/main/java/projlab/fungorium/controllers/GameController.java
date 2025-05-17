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
import projlab.fungorium.utilities.ConnectionMap;
import projlab.fungorium.utilities.Logger;
import projlab.fungorium.views.gamecomponents.ConnectionView;
import projlab.fungorium.views.gamecomponents.DrawableComponent;
import projlab.fungorium.views.gamecomponents.GameComponentView;
import projlab.fungorium.views.gamecomponents.InsectView;
import projlab.fungorium.views.gamecomponents.MushroomBodyView;
import projlab.fungorium.views.gamecomponents.SporeView;
import projlab.fungorium.views.gamecomponents.TectonView;
import projlab.fungorium.views.gamecomponents.ThreadView;
import projlab.fungorium.windowing.game.MainPanel;

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

		selectedInsect = null;
		selectedThread = null;
		selectedBody = null;
		selectedTecton = null;

		tectonViews = new ArrayList<>();
		drawables = new ArrayList<>();
		nextRoundAction = new PassAction(this);

		buildMap(insectologists.size() + mycologists.size());

		activeType = PlayerType.MYCOLOGIST;
	}

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

	private List<DrawableComponent> drawables;
	private List<ConnectionView> connectionViews;

	private PassAction nextRoundAction;

	private MainPanel mainPanel;

	private Random random = new Random();

	public int getMycologistsSize() {return mycologists.size();}
	public int getInsectologistsSize() {return insectologists.size();}

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
	public List<DrawableComponent> getDrawables() { return drawables; }
	public PassAction getNextRoundAction() { return nextRoundAction; }

	public void setActiveType(PlayerType type) { activeType = type; }
	// @formatter:on

	public void setInsectologistIdx(int idx) {
		insectologistIdx = idx;
		try {
			insectologistController.updateActive(insectologists.get(insectologistIdx));
		} catch (IndexOutOfBoundsException ignored) {}
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
	 * elég == -vel checkolni, mert a program, utána nem vált vissza arra a csapatra, amelyik elérte a megfelelő size-t
	 */
	public boolean checkIfLastActive() {
		return mycologistIdx == mycologists.size() && insectologistIdx == insectologists.size();
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

		mainPanel.setGameComponents(drawables);

		mainPanel.revalidate();
		mainPanel.repaint();
	}

	private Map<Integer, TectonView> calculateTectonViewMap() {
		List<Tecton> tectons = Game.getInstance().getRegistry().getTectons();
		int gridSize = (int) Math.ceil(Math.sqrt(tectons.size()));

		int usefulCanvasSpace = Math.min(mainPanel.getWidth(), mainPanel.getHeight());
		int pixelsPerCell = (usefulCanvasSpace - GRID_MARGIN_PX * 2 + GRID_SPACING_PX) / gridSize;
		int contentPerCell = pixelsPerCell - GRID_SPACING_PX;

		int centerOffset = pixelsPerCell / 2;

		Map<Integer, TectonView> tectonViewMap = new HashMap<>();

		for (int i = 0; i < tectons.size(); i++) {
			int gridX = i % gridSize;
			int gridY = i / gridSize;

			Point position = new Point(
					GRID_MARGIN_PX - GRID_SPACING_PX / 2 + gridX * pixelsPerCell + centerOffset,
					GRID_MARGIN_PX - GRID_SPACING_PX / 2 + gridY * pixelsPerCell + centerOffset);

			Tecton tecton = tectons.get(i);
			TectonView tectonView = new TectonView(
					tectons.get(i),
					position,
					new Point(contentPerCell, contentPerCell));

			tectonViewMap.put(tecton.getID(), tectonView);
			drawables.add(tectonView);
		}

		return tectonViewMap;
	}

	private void updateTectonViews(Map<Integer, TectonView> tectonViewMap) {
		ConnectionMap connections = new ConnectionMap();

		for (var tectonView : tectonViewMap.values()) {
			drawables.add(tectonView);

			Tecton tecton = tectonView.getGameObject();
			for (Tecton neighbor : tecton.getNeighbours()) {
				if (connections.hasConnection(tecton.getID(), neighbor.getID()))
					continue;

				connections.addConnection(tecton.getID(), neighbor.getID());

				TectonView neighborView = tectonViewMap.get(neighbor.getID());

				connectionViews.add(new ConnectionView(tectonView.getCenter(), neighborView.getCenter()));
			}
		}
	}

	private void updateInsectViews(Map<Integer, TectonView> tectonViewMap) {
		for (var insect : Game.getInstance().getRegistry().getInsects()) {
			Tecton insectTecton = insect.getTecton();
			TectonView insectTectonView = tectonViewMap.get(insectTecton.getID());

			int radius = (int) (insectTectonView.getSize().x * 0.5 * InsectView.RADIUS_MULTIPLIER);

			Point position = insectTectonView.calculateMobileObjectPosition(insectTecton, radius);

			drawables.add(new InsectView(insect, position));
		}
	}

	private void updateSporeViews(Map<Integer, TectonView> tectonViewMap) {
		for (var spore : Game.getInstance().getRegistry().getMushroomSpores()) {
			Tecton sporeTecton = spore.getTecton();
			TectonView sporeTectonView = tectonViewMap.get(sporeTecton.getID());

			int radius = (int) (sporeTectonView.getSize().x * 0.5 * SporeView.RADIUS_MULTIPLIER);

			Point position = sporeTectonView.calculateMobileObjectPosition(sporeTecton, radius);

			drawables.add(new SporeView(spore, position));
		}
	}

	private void updateThreadViews(Map<Integer, TectonView> tectonViewMap) {
		for (var thread : Game.getInstance().getRegistry().getMushroomThreads()) {
			Tecton threadTecton = thread.getTecton();
			TectonView threadTectonView = tectonViewMap.get(threadTecton.getID());

			int radius = (int) (threadTectonView.getSize().x * 0.5 * ThreadView.RADIUS_MULTIPLIER);

			Point position = threadTectonView.calculateMobileObjectPosition(threadTecton, radius);

			drawables.add(new ThreadView(thread, position));
		}
	}

	private void updateMushroomBodyViews(Map<Integer, TectonView> tectonViewMap) {
		for (var body : Game.getInstance().getRegistry().getMushroomBodies()) {
			Tecton bodyTecton = body.getTecton();
			TectonView bodyTectonView = tectonViewMap.get(bodyTecton.getID());

			int radius = (int) (bodyTectonView.getSize().x * 0.5 * ThreadView.RADIUS_MULTIPLIER);

			Point position = bodyTectonView.calculateMobileObjectPosition(bodyTecton, radius);

			drawables.add(new MushroomBodyView(body, position));
		}
	}

	private void updateGameComponents() {
		drawables.clear();

		Map<Integer, TectonView> tectonViewMap = calculateTectonViewMap();

		updateTectonViews(tectonViewMap);
		updateInsectViews(tectonViewMap);
		updateSporeViews(tectonViewMap);
		updateThreadViews(tectonViewMap);
		updateMushroomBodyViews(tectonViewMap);

		// TODO: thread connection-ök
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
