package system;

import system.ui.CircleTagGeneratorWindow;

public class CircleTagGenerator {
	public static CircleTagGeneratorWindow window = new CircleTagGeneratorWindow(3888);

	public static void main(String[]args) {
		window.setSize(800,600);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(CircleTagGeneratorWindow.EXIT_ON_CLOSE);
		window.setVisible(true);
		
	}
	
}
