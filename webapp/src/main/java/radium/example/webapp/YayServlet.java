package radium.example.webapp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Yay", urlPatterns = { "/*" })
public class YayServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public YayServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter outputWriter = response.getWriter( );
		YayPrinter yayPrinter = new YayPrinter();
		yayPrinter.printYay(outputWriter);
		
		outputWriter.flush();
	}

}
