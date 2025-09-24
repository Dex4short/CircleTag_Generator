package system.ui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import marker.CircleTag;
import marker.CircleTagIO;
import system.ui.panels.GenerationPanel;
import system.ui.panels.InputPanel;
import system.ui.panels.SavePanel;

public class CircleTagGeneratorWindow extends JFrame{
	private static final long serialVersionUID = -1499242398683450690L;
	private final SavePanel savePanel;
	private final GenerationPanel generationPanel;
	private final InputPanel inputPanel;
	
	private CircleTag circleTag;
	
	public CircleTagGeneratorWindow(int initialId) {
		setTitle("CircleTag Generator");
		setLayout(new BorderLayout());
		setAlwaysOnTop(true);
		
		savePanel = new SavePanel() {
			private static final long serialVersionUID = 6572018936980911148L;

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
			
			public void errorDialog(String message) {
				SwingUtilities.invokeLater(() -> {
					JOptionPane.showMessageDialog(CircleTagGeneratorWindow.this, message, "Save Image Failed", JOptionPane.ERROR_MESSAGE);
				});
			}
			
			public void infoDialog(String fileName) {
				SwingUtilities.invokeLater(() -> {
					JOptionPane.showMessageDialog(CircleTagGeneratorWindow.this, "Circle Tag " + fileName + " saved!", "Image Saved!", JOptionPane.INFORMATION_MESSAGE);
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
		
		
		add(savePanel, BorderLayout.NORTH);
		add(generationPanel, BorderLayout.CENTER);
		add(inputPanel, BorderLayout.SOUTH);
		
	}
}
