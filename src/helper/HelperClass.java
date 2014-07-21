package helper;

import java.util.Random;

public class HelperClass {
	
	
	public HelperClass(){
		
	}
	
	
	
	public static int randInt(final int min, final int max) {
		// Usually this can be a field rather than a method variable
		final Random rand = new Random();
		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		final int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

}
