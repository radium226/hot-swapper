package radium.example;

import java.io.PrintWriter;

import radium.example.impl.OneByOneLooper;

public class Loop {

	public static void main(String[] arguments) {
		Looper looper = new OneByOneLooper(1, 20);
		looper.loop(new PrintWriter(System.out));
	}
	
}
