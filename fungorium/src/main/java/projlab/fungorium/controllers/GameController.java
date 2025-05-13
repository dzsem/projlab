package projlab.fungorium.controllers;

import java.util.List;

import javax.swing.JOptionPane;

import projlab.fungorium.interfaces.GameComponentViewVisitor;
import projlab.fungorium.models.Game;
import projlab.fungorium.models.player.Insectologist;
import projlab.fungorium.models.player.Mycologist;
import projlab.fungorium.models.player.PlayerType;
import projlab.fungorium.views.gamecomponents.InsectView;
import projlab.fungorium.views.gamecomponents.MushroomBodyView;
import projlab.fungorium.views.gamecomponents.SporeView;
import projlab.fungorium.views.gamecomponents.TectonView;
import projlab.fungorium.views.gamecomponents.ThreadView;
import java.awt.Point;

public class GameController implements GameComponentViewVisitor {
	// Ne példányosítsuk kívülről.
	private GameController() {
	}

	private static GameController instance = null;

	private PlayerType activeType;

	private int insectologistIdx;
	private int mycologistIdx;

	private List<Insectologist> insectologists;
	private List<Mycologist> mycologists;

	// @formatter:off
	public PlayerType getActiveType() { return activeType; }
	public int getInsectologistIdx() { return insectologistIdx; }
	public int getMycologistIdx() { return mycologistIdx; }

	public void setActiveType(PlayerType type) { activeType = type; }
	public void setInsectologistIdx(int idx) { insectologistIdx = idx; }
	public void setMycologistIdx(int idx) { mycologistIdx = idx; }
	// @formatter:on

	public void setPlayers(List<Insectologist> insectologists, List<Mycologist> mycologists) {
		this.insectologists = insectologists;
		this.mycologists = mycologists;

		// újraindításkor jól jöhet:
		this.insectologistIdx = 0;
		this.mycologistIdx = 0;
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

	public void showError(Exception e) {
		JOptionPane.showMessageDialog(null, "Error:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void visit(TectonView tectonView) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'visit'");
	}

	@Override
	public void visit(ThreadView threadView) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'visit'");
	}

	@Override
	public void visit(InsectView insectView) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'visit'");
	}

	@Override
	public void visit(MushroomBodyView mushroomBodyView) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'visit'");
	}

	@Override
	public void visit(SporeView sporeView) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'visit'");
	}

}
