package system.ui.panels;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import marker.CircleTag;
import marker.CircleTagGenerator;

public abstract class GenerationPanel extends JPanel{
	private static final long serialVersionUID = 7904869368096164666L;
	private final CircleTagGenerator generator;
	private final CircleTag circleTag;
	private Graphics2D g2d;
	
	public GenerationPanel(int initialId) {
		generator = new CircleTagGenerator() {
			@Override
			public void onIdGenerated(CircleTag circleTag) {
				onCircleTagGenerated(circleTag);
				
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
	
	public abstract void onCircleTagGenerated(CircleTag circleTag);
	
	@Override
	public void paint(Graphics g) {
		g2d = (Graphics2D)g;
		
		circleTag.draw(g2d, (getWidth()/2) - (circleTag.getTagSize()/2), (getHeight()/2) - (circleTag.getTagSize()/2));
	}
	
	public void generateCircleTag(int id) {
		new Thread( () -> {
			generator.generateCircleTag(id);
		}).start();
	}
	
	public CircleTag getCircleTag() {
		return circleTag;
	}
	
}
