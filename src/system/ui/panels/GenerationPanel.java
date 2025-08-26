package system.ui.panels;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import marker.CircleTag;
import oop.Generator;

public abstract class GenerationPanel extends JPanel{
	private static final long serialVersionUID = 7904869368096164666L;
	private final CircleTag circle_tag;
	private final Generator generator;
	private Graphics2D g2d;
	
	public GenerationPanel() {
		circle_tag = new CircleTag();
		circle_tag.setMarkerBodyVisible(true);
		circle_tag.setOrbitsVisible(true);
		
		generator = new Generator(circle_tag) {
			@Override
			public void onIdGenerated() {
				getRootPane().repaint();
			}
		};
	}
	@Override
	public void paint(Graphics g) {
		g2d = (Graphics2D)g;
		
		circle_tag.draw(g2d, (getWidth()/2) - (circle_tag.getTagSize()/2), (getHeight()/2) - (circle_tag.getTagSize()/2));
		
	}
	public CircleTag getCircleTag() {
		return circle_tag;
	}
	public Generator getGenerator() {
		return generator;
	}
	
}
