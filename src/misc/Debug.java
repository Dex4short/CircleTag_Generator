package misc;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Debug {
	public static boolean isDebugOn=false;
	private static ConsoleWindow consoleWindow = new ConsoleWindow();
	
	private Debug() {}
	
	public static void showConsoleWindow() {
		consoleWindow.setVisible(isDebugOn);
	}
	
	public static void print(String str) {
		if(isDebugOn) consoleWindow.print(str);
	}
	
	public static void println(String str) {
		if(isDebugOn) consoleWindow.println(str);
	}
	
	public static void clear() {
		if(isDebugOn) consoleWindow.clear();
	}
	
	private static class ConsoleWindow extends JFrame {
		private static final long serialVersionUID = -2323321684518645082L;
		private JTextArea txtArea;

		public ConsoleWindow() {
			setSize(600, 600);
			setLocationRelativeTo(null);
			
			txtArea = new JTextArea();
			add(new JScrollPane(txtArea));
		}
		
		public void print(String str) {
			txtArea.append(str);
		}
		
		public void println(String str) {
			print(str + "\n");
		}
		
		public void clear() {
			txtArea.setText("");
		}
		
	}
	
}
