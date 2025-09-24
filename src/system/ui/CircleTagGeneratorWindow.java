package system.ui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

import marker.CircleTag;
import marker.CircleTagIO;
import system.CircleTagGenerator;
import system.ui.panels.GenerationPanel;
import system.ui.panels.HeaderPanel;
import system.ui.panels.InputPanel;
import system.ui.panels.SavePanel;

public class CircleTagGeneratorWindow extends JFrame{
	private static final long serialVersionUID = -1499242398683450690L;
	private final HeaderPanel headerPanel;
	private final GenerationPanel generationPanel;
	private final InputPanel inputPanel;
	
	private CircleTag circleTag;
	
	public CircleTagGeneratorWindow(int initialId) {
		setTitle("CircleTag Generator");
		setLayout(new BorderLayout());
		setAlwaysOnTop(true);
		
		headerPanel = new HeaderPanel() {
			private static final long serialVersionUID = 2569165676889259494L;

			@Override
			public void onSave(String url) {
				try {
					String fileName = "id" + circleTag.getTagId();
					CircleTagIO.saveAsImage(circleTag, url + fileName, "png");
					infoDialog(fileName + ".png");
				} catch (Exception e) {
					errorDialog(e.getMessage());
					e.printStackTrace();
				}
			}

			@Override
			public void onScaleChanged(float scale) {
				circleTag.setScale(scale);
				
				JRootPane root = getRootPane();
				if(root != null) root.repaint();
			}

			void errorDialog(String message) {
				SwingUtilities.invokeLater(() -> {
					JOptionPane.showMessageDialog(CircleTagGenerator.window, message, "Save Image Failed", JOptionPane.ERROR_MESSAGE);
				});
			}
			
			void infoDialog(String fileName) {
				SwingUtilities.invokeLater(() -> {
					JOptionPane.showMessageDialog(CircleTagGenerator.window, "Circle Tag " + fileName + " saved!", "Image Saved!", JOptionPane.INFORMATION_MESSAGE);
				});
			}
			
		};
		
		inputPanel = new InputPanel(initialId) {
			
			private static final long serialVersionUID = 3482645221099109849L;
			
			@Override
			public void onInputID(int id) {
				setEnabled(false);
				generationPanel.generateCircleTag(id);
			}
			
		};
		
		generationPanel = new GenerationPanel(initialId) {
			
			private static final long serialVersionUID = -5246676362719121252L;
			
			@Override
			public void onCircleTagGenerated(CircleTag circleTag) {
				CircleTagGeneratorWindow.this.circleTag = circleTag;
				inputPanel.setEnabled(true);
			}
			
		};
		
		add(headerPanel, BorderLayout.NORTH);
		add(generationPanel, BorderLayout.CENTER);
		add(inputPanel, BorderLayout.SOUTH);
		
	}
}
