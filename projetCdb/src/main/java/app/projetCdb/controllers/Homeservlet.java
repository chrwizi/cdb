package app.projetCdb.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.projetCdb.dao.ComputerDao;
import app.projetCdb.dao.DbAccess;
import app.projetCdb.models.Computer;
import app.projetCdb.services.IListComputersService;
import app.projetCdb.services.ListComputersService;
   

@WebServlet(name="cdb",urlPatterns="/cdb")
public class Homeservlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String REDIRECT_VIEW="/WEB-INF/dashboard.jsp";
	
	private ComputerDao computerDao=new ComputerDao(DbAccess.getInstance());
	private IListComputersService listcomputerSService = new ListComputersService(computerDao);
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ArrayList<Computer>computers=(ArrayList<Computer>) listcomputerSService.getAll();
			String msg="hello";
			request.setAttribute("hello", msg);
			if(computers.isEmpty())System.out.println("c'est empty ");
			request.setAttribute("nbComputers", computers.size());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.getServletContext().getRequestDispatcher(REDIRECT_VIEW).forward(request, response);
	}
	
	
 

}
