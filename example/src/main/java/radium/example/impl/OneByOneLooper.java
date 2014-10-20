package radium.example.impl;

import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import radium.example.Looper;

public class OneByOneLooper implements Looper {

	private int firstIndex;
	private int lastIndex;
	
	public OneByOneLooper(int firstIndex, int lastIndex) {
		super();
		
		this.firstIndex = firstIndex;
		this.lastIndex = lastIndex;
	}
	
	public void loop(PrintWriter writer) {
		for (int index = firstIndex; index <= lastIndex; index+=1) {
			printIteration(writer, index);
			sleep(1, TimeUnit.SECONDS);
		}
	}
	
	private void printIteration(PrintWriter writer, int index) {
		writer.println("Index Incrementation #" + Integer.toString(index));
		writer.flush();
	}
	
	public static void sleep(int duration, TimeUnit unit) {
		try {
			Thread.sleep(unit.toMillis(duration));
		} catch (InterruptedException e) {
			
		}
	}
	
}
