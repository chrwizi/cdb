package app.projetCdb.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name="edit",urlPatterns="/editComputer")
public class EditComputerSevlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private static final String REDIRECT_VIEW="/static/views/editComputer.html";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(REDIRECT_VIEW).forward(req, resp);
	}
	
	

}
