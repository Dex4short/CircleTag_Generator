package system.ui.panels;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public abstract class HeaderPanel extends JPanel{
	
	private static final long serialVersionUID = 6719129745457134409L;
	private SavePanel savePanel;
	private ScalePanel scalePanel;

	public HeaderPanel() {
		setLayout(new BorderLayout());
		
		savePanel = new SavePanel() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void onSave(String url) {
				HeaderPanel.this.onSave(url);
			}
			
		};
		
		scalePanel = new ScalePanel() {
			
			private static final long serialVersionUID = -3901254288851041250L;

			@Override
			public void onScaleChanged(float scale) {
				HeaderPanel.this.onScaleChanged(scale);
			}
			
		};
		
		add(savePanel, BorderLayout.CENTER);
		add(scalePanel, BorderLayout.EAST);
		
	}
	
	public abstract void onSave(String url);
	
	public abstract void onScaleChanged(float scale);
	
}
