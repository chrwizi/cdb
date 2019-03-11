package app.projetCdb.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import app.projetCdb.models.Computer;
import app.projetCdb.persistance.ComputerDao;
import app.projetCdb.persistance.ComputersPage;
import app.projetCdb.persistance.DbAccess;
import app.projetCdb.persistance.IPageSelect;
import app.projetCdb.persistance.dto.ComputerDto;
import app.projetCdb.persistance.dto.IMapperComputerDto;
import app.projetCdb.persistance.dto.MapperComputer;
import app.projetCdb.services.IComputerService;
import app.projetCdb.services.ComputerService;

@WebServlet(name = "cdb", urlPatterns = "/")
public class Homeservlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String DASHBOARD_VIEW = "/WEB-INF/dashboard.jsp";
	
	@Autowired 
	private IComputerService computerService;
	private IMapperComputerDto mapper = new MapperComputer();
 
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String selectedPage=request.getParameter("selectedPage");
		List<Computer> computers =new ArrayList<Computer>();
		
		try {
			computers = computerService.getPage((selectedPage==null?1:Integer.parseInt(selectedPage)));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ArrayList<ComputerDto> computersDto = (ArrayList<ComputerDto>) mapper.mapListComputer(computers);
		request.setAttribute("nbPages", computerService.getNbPages());
		request.setAttribute("computers", computersDto);
		request.setAttribute("nbComputers", computersDto.size()); 
		this.getServletContext().getRequestDispatcher(DASHBOARD_VIEW).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String search=(String)req.getParameter("search");
		String selectedPage=req.getParameter("selectedPage");
		List<Computer> computers = new ArrayList<Computer>();
		
		try {
			computers = computerService.getPage((selectedPage==null?1:Integer.parseInt(selectedPage)),search);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ArrayList<ComputerDto> computersDto = (ArrayList<ComputerDto>) mapper.mapListComputer(computers);
		req.setAttribute("nbPages", computerService.getNbPages());
		req.setAttribute("computers", computersDto);
		req.setAttribute("nbComputers", computersDto.size());
		this.getServletContext().getRequestDispatcher(DASHBOARD_VIEW).forward(req, resp);
	}
	
	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	

}
