package projlab.fungorium.controllers;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import projlab.fungorium.models.Game;
import projlab.fungorium.models.GameObject;
import projlab.fungorium.models.MushroomThread;
import projlab.fungorium.models.Tecton;
import projlab.fungorium.utilities.ConnectionMap;
import projlab.fungorium.views.gamecomponents.DrawableComponent;
import projlab.fungorium.views.gamecomponents.GameComponentView;
import projlab.fungorium.views.gamecomponents.InsectView;
import projlab.fungorium.views.gamecomponents.MushroomBodyView;
import projlab.fungorium.views.gamecomponents.NeighbourConnectionView;
import projlab.fungorium.views.gamecomponents.SporeView;
import projlab.fungorium.views.gamecomponents.TectonView;
import projlab.fungorium.views.gamecomponents.ThreadConnectionView;
import projlab.fungorium.views.gamecomponents.ThreadView;

/**
 * A view-ok helyének kiosztásáért felelős osztály. Ez kezeli a rácsszerkezet
 * elkészítését, és a GameObjectek View-jainak elhelyezését.
 * <p>
 * 
 * @see GameLayoutGenerator#generate()
 */
public class GameLayoutGenerator {
	private List<DrawableComponent> drawables;
	private List<GameComponentView<? extends GameObject>> gameComponentViews;
	private int gridSpacing;
	private int gridMargin;
	private int gridWidth;
	private int gridHeight;

	private Map<Integer, TectonView> tectonViewMap;

	public GameLayoutGenerator(int gridSpacing, int gridMargin, int gridWidth, int gridHeight) {
		this.gridSpacing = gridSpacing;
		this.gridMargin = gridMargin;
		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;

		gameComponentViews = new ArrayList<>();
		drawables = new ArrayList<>();
		tectonViewMap = new HashMap<>();
	}

	/**
	 * A modell alapján létrehozza az összes TectonView-t, és egy GameObject ID
	 * szerinti hash map-ben adja vissza.
	 * <p>
	 * Itt történik a rácsszerkezet elkészítése.
	 */
	private Map<Integer, TectonView> calculateTectonViewMap() {
		List<Tecton> tectons = Game.getInstance().getRegistry().getTectons();
		int gridSize = (int) Math.ceil(Math.sqrt(tectons.size()));

		int usefulCanvasSpace = Math.min(gridWidth, gridHeight);
		int pixelsPerCell = (usefulCanvasSpace - gridMargin * 2 + gridSpacing) / gridSize;
		int contentPerCell = pixelsPerCell - gridSpacing;

		int centerOffset = pixelsPerCell / 2;

		Map<Integer, TectonView> tectonViewMap = new HashMap<>();

		for (int i = 0; i < tectons.size(); i++) {
			int gridX = i % gridSize;
			int gridY = i / gridSize;

			Point position = new Point(
					gridMargin - gridSpacing / 2 + gridX * pixelsPerCell + centerOffset,
					gridMargin - gridSpacing / 2 + gridY * pixelsPerCell + centerOffset);

			Tecton tecton = tectons.get(i);
			TectonView tectonView = new TectonView(
					tectons.get(i),
					position,
					new Point(contentPerCell, contentPerCell));

			tectonViewMap.put(tecton.getID(), tectonView);
		}

		return tectonViewMap;
	}

	/**
	 * A {@link tectonViewMap} ismeretében kiszámítja az összes ThreadView-t, és
	 * visszaadja őket egy Thread GameObject ID szerinti hash map-ben.
	 */
	private Map<Integer, ThreadView> calculateThreadViewMap() {
		Map<Integer, ThreadView> threadViewMap = new HashMap<>();

		for (var thread : Game.getInstance().getRegistry().getMushroomThreads()) {
			Tecton threadTecton = thread.getTecton();
			TectonView threadTectonView = tectonViewMap.get(threadTecton.getID());

			int radius = (int) (threadTectonView.getSize().x * 0.5 * ThreadView.RADIUS_MULTIPLIER);

			Point position = threadTectonView.calculateMobileObjectPosition(threadTecton, radius);

			threadViewMap.put(thread.getID(), new ThreadView(thread, position));
		}

		return threadViewMap;
	}

	/**
	 * Hozzáadja a drawables-hez és a gameComponentViews-hoz a paraméterként
	 * megadott view-t.
	 */
	private void addView(GameComponentView<? extends GameObject> gameComponentView) {
		drawables.add(gameComponentView);
		gameComponentViews.add(gameComponentView);
	}

	/** Hozzáadja a drawables-hez a paraméterként megadott view-t. */
	private void addView(DrawableComponent drawableComponent) {
		drawables.add(drawableComponent);
	}

	/**
	 * Felviszi a view-ok közé az összes tektont, a tectonViewMap ismeretében. A
	 * tektonok közötti szomszédságokat is hozzáadja a view-okhoz.
	 * <p>
	 * 
	 * @see NeighbourConnectionView
	 */
	private void updateTectonViews() {
		ConnectionMap connections = new ConnectionMap();

		for (var tectonView : tectonViewMap.values()) {
			drawables.add(tectonView);

			Tecton tecton = tectonView.getGameObject();
			for (Tecton neighbor : tecton.getNeighbours()) {
				if (connections.hasConnection(tecton.getID(), neighbor.getID()))
					continue;

				connections.addConnection(tecton.getID(), neighbor.getID());

				TectonView neighborView = tectonViewMap.get(neighbor.getID());

				addView(new NeighbourConnectionView(tectonView.getCenter(), neighborView.getCenter()));
			}
		}
	}

	private void updateInsectViews() {
		for (var insect : Game.getInstance().getRegistry().getInsects()) {
			Tecton insectTecton = insect.getTecton();
			TectonView insectTectonView = tectonViewMap.get(insectTecton.getID());

			int radius = (int) (insectTectonView.getSize().x * 0.5 * InsectView.RADIUS_MULTIPLIER);

			Point position = insectTectonView.calculateMobileObjectPosition(insectTecton, radius);

			addView(new InsectView(insect, position));
		}
	}

	private void updateSporeViews() {
		for (var spore : Game.getInstance().getRegistry().getMushroomSpores()) {
			Tecton sporeTecton = spore.getTecton();
			TectonView sporeTectonView = tectonViewMap.get(sporeTecton.getID());

			int radius = (int) (sporeTectonView.getSize().x * 0.5 * SporeView.RADIUS_MULTIPLIER);

			Point position = sporeTectonView.calculateMobileObjectPosition(sporeTecton, radius);

			addView(new SporeView(spore, position));
		}
	}

	/**
	 * Felviszi a view-ok közé az összes MushroomThread-et, a threadViewMap és
	 * tectonViewMap ismeretében. A Thread-ek tektonjai közé
	 * ThreadConnectionView-okat vesz fel.
	 */
	private void updateThreadViews() {
		Map<Integer, ThreadView> threadViewMap = calculateThreadViewMap();

		ConnectionMap connections = new ConnectionMap();

		for (var threadView : threadViewMap.values()) {
			addView(threadView);

			MushroomThread thread = threadView.getGameObject();

			int threadIndex = 0;
			for (var connectedThread : threadView.getGameObject().getConnectedThreads()) {
				if (connections.hasConnection(thread.getID(), connectedThread.getID()))
					continue;

				connections.addConnection(thread.getID(), connectedThread.getID());

				int threadTectonID = thread.getTecton().getID();
				int connectedThreadTectonID = connectedThread.getTecton().getID();
				int smallerID = Math.min(threadTectonID, connectedThreadTectonID);
				int biggerID = Math.max(threadTectonID, connectedThreadTectonID);

				Point startingPoint = tectonViewMap.get(smallerID).getCenter();
				Point endingPoint = tectonViewMap.get(biggerID).getCenter();

				ThreadConnectionView connectionView = new ThreadConnectionView(startingPoint, endingPoint,
						ThreadConnectionView.getVisualOffset(threadIndex, thread.getConnectedThreads().size()),
						ThreadConnectionView.isValidConnection(thread, connectedThread));

				drawables.add(connectionView);
				threadIndex += 1;
			}
		}
	}

	private void updateMushroomBodyViews() {
		for (var body : Game.getInstance().getRegistry().getMushroomBodies()) {
			Tecton bodyTecton = body.getTecton();
			TectonView bodyTectonView = tectonViewMap.get(bodyTecton.getID());

			Point position = bodyTectonView.getCenter();

			drawables.add(new MushroomBodyView(body, position));
		}
	}

	/**
	 * Legenerálja a modellhez tartozó view-okat.
	 * <p>
	 * A kimenet a {@link #getDrawables()} és {@link #getGameComponentViews()}
	 * függvényekkel kérhető le.
	 */
	public void generate() {
		drawables.clear();
		this.tectonViewMap = calculateTectonViewMap();

		updateTectonViews();
		updateInsectViews();
		updateSporeViews();
		updateThreadViews();
		updateMushroomBodyViews();
	}

	public List<DrawableComponent> getDrawables() {
		return drawables;
	}

	public List<GameComponentView<? extends GameObject>> getGameComponentViews() {
		return gameComponentViews;
	}
}
