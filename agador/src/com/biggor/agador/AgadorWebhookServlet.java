package com.biggor.agador;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@SuppressWarnings("serial")
public class AgadorWebhookServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(AgadorWebhookServlet.class.getName());
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/plain");
		
		if (req.getParameter("challenge") != null) {
			resp.getWriter().print(req.getParameter("challenge"));
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		Gson gson = new Gson();
		
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = req.getReader();
		String line;
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		
		Delta delta = (Delta) gson.fromJson(buffer.toString(), Delta.class);

		log.warning(gson.toJson(delta));
	}

}
