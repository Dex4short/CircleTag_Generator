package misc;

public class Debug {
	public static boolean debugOn;
	
	private Debug() {
		debugOn = false;
	}
	
	public static void print(String str) {
		if(debugOn) System.out.println(str);
	}
	
}
