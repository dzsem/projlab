package projlab.fungorium.views.gamecomponents;

import java.awt.Graphics2D;
import java.awt.Point;

public class ThreadConnectionView extends ConnectionView {
	public ThreadConnectionView(Point start, Point end) {
		super(start, end);
	}

	@Override
	public void draw(Graphics2D g) {
		// TODO: más grafika mint a NeighbourConnectionView
		g.drawLine(start.x, start.y, end.x, end.y);
	}
}
