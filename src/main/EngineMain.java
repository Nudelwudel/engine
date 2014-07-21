package main;


public class EngineMain {

	public static void main(String[] args) {
		System.out.println("Booting up...");
		System.setProperty("sun.java2d.opengl","True");
		new database.EngineDatabase();
	}

}
