package radium.example.webapp;

import java.io.PrintWriter;

public class YayPrinter implements Printer {

	public void printYay(PrintWriter writer) {
		writer.println("<h1>Bonjour!</h1>");
	}
	
}
