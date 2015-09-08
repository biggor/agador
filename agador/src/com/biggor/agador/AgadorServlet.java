package com.biggor.agador;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class AgadorServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Agador: Hello, world");
	}
}
