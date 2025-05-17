package projlab.fungorium.views.gamecomponents;

import java.awt.Graphics2D;
import java.awt.Point;

public class NeighbourConnectionView extends ConnectionView {
	public NeighbourConnectionView(Point start, Point end) {
		super(start, end);
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawLine(start.x, start.y, end.x, end.y);
	}
}
