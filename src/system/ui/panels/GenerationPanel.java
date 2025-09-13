package system.ui.panels;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import marker.CircleTag;
import oop.Generator;

public abstract class GenerationPanel extends JPanel{
	private static final long serialVersionUID = 7904869368096164666L;
	private final Generator generator;
	private final CircleTag circleTag;
	private Graphics2D g2d;
	
	public GenerationPanel(int initialId) {
		generator = new Generator() {
			@Override
			public void onIdGenerated(CircleTag circleTag) {
				if(getRootPane() != null) getRootPane().repaint();
			}
		};
		
		circleTag = generator.getCircleTag();
		circleTag.setMarkerBodyVisible(true);
		circleTag.setOrbitsVisible(true);
		circleTag.setScale(2);
		
		SwingUtilities.invokeLater(() -> {
			generator.generateCircleTag(initialId);
		});
	}
	
	@Override
	public void paint(Graphics g) {
		g2d = (Graphics2D)g;
		
		circleTag.draw(g2d, (getWidth()/2) - (circleTag.getTagSize()/2), (getHeight()/2) - (circleTag.getTagSize()/2));
	}
	
	public CircleTag getCircleTag() {
		return circleTag;
	}
	
	public Generator getGenerator() {
		return generator;
	}
	
}
