package system;

import system.ui.CircleTagGenerator;

public class Main {

	public static void main(String[]args) {
		CircleTagGenerator generator = new CircleTagGenerator();
		generator.setSize(400,300);
		generator.setLocationRelativeTo(null);
		generator.setDefaultCloseOperation(CircleTagGenerator.EXIT_ON_CLOSE);
		generator.setVisible(true);
	}

}
