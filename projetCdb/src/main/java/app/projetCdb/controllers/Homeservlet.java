package app.projetCdb.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.projetCdb.models.Computer;
import app.projetCdb.persistance.ComputerDao;
import app.projetCdb.persistance.DbAccess;
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
			ArrayList<Computer>computersList=(ArrayList<Computer>) listcomputerSService.getAll();
			
			ArrayList<Computer>computers=new ArrayList<Computer> ();
			computers.add(computersList.get(0));
			computers.add(computersList.get(1));
			computers.add(computersList.get(2));
			computers.add(computersList.get(3));
			computers.add(computersList.get(4));
			request.setAttribute("computers", computers);
			request.setAttribute("nbComputers", computers.size());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.getServletContext().getRequestDispatcher(REDIRECT_VIEW).forward(request, response);
	}
	
	
 

}
