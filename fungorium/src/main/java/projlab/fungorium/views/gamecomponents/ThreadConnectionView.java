package projlab.fungorium.views.gamecomponents;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import projlab.fungorium.models.MushroomThread;

public class ThreadConnectionView extends ConnectionView {
	/**
	 * Azt tárolja, hogy a gombafonál-kapcsolat mindkét végén levő fonál vágatlan,
	 * és kifejlett-e, azaz hogy rovarokkal átjárható-e a kapcsolat.
	 */
	private boolean isValidConnection;

	private static Point addPoints(Point a, Point b) {
		return new Point(a.x + b.x, a.y + b.y);
	}

	private static Point calculateOffsetVector(Point start, Point end, int offset) {
		double directionLength = start.distance(end);
		double normalX = -(end.y - start.y) / directionLength;
		double normalY = (end.x - start.x) / directionLength;

		return new Point((int) (normalX * offset), (int) (normalY * offset));
	}

	public ThreadConnectionView(Point start, Point end, int offset, boolean isValidConnection) {
		super(start, end);

		Point offsetVector = calculateOffsetVector(start, end, offset);
		this.start = addPoints(start, offsetVector);
		this.end = addPoints(end, offsetVector);

		this.isValidConnection = isValidConnection;
	}

	/**
	 * Visszaadja a kirajzolandó gombafonál vonalának távolságát a tektonok
	 * szomszédságát mutató egyenestől, pixelben.
	 * 
	 * @param k A gombafonál kapcsolat sorszáma
	 * @param n A két tekton között futó összes gombafonál-kapcsolat száma.
	 * @return
	 */
	public static int getVisualOffset(int k, int n) {
		// TODO: implement
		return 15;
	}

	public static boolean isValidConnection(MushroomThread thread1, MushroomThread thread2) {
		return thread1.isConnectingTecton(thread2.getTecton());
	}

	@Override
	public void draw(Graphics2D g) {
		// TODO: más grafika mint a NeighbourConnectionView
		g.setColor(Color.RED);
		g.drawLine(start.x, start.y, end.x, end.y);
	}
}
