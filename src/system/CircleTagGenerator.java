package system;

import system.ui.CircleTagGeneratorWindow;

public class CircleTagGenerator {

	public static void main(String[]args) {
		CircleTagGeneratorWindow generator = new CircleTagGeneratorWindow(3888);
		generator.setSize(400,400);
		generator.setLocationRelativeTo(null);
		generator.setDefaultCloseOperation(CircleTagGeneratorWindow.EXIT_ON_CLOSE);
		generator.setVisible(true);
	}
	
}
