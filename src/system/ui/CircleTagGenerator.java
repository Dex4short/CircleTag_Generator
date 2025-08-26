package system.ui;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import system.ui.panels.GenerationPanel;
import system.ui.panels.InputPanel;

public class CircleTagGenerator extends JFrame{
	private static final long serialVersionUID = -1499242398683450690L;
	private final GenerationPanel generation_panel;
	private final InputPanel input_panel;
	
	public CircleTagGenerator() {
		setTitle("CircleTag Generator");
		setLayout(new GridLayout(2, 1));
		
		generation_panel = new GenerationPanel() {
			private static final long serialVersionUID = -5246676362719121252L;
			
		};
		input_panel = new InputPanel() {
			private static final long serialVersionUID = 3482645221099109849L;
			@Override
			public void generateCircleTag(int id) {
				generation_panel.getGenerator().generateCircleTag(id);
			}
		};
		
		add(generation_panel);
		add(input_panel);
		
	}
}
