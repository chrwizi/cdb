package cdb.webApp.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import cdb.binding.ComputerDto;
import cdb.binding.IMapperComputerDto;
import cdb.binding.MapperComputer;
import cdb.core.models.Computer;
import cdb.service.IComputerService;

@WebServlet(name = "sort", urlPatterns = "/sortComputers")
public class SortServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String DASHBOARD_VIEW = "/WEB-INF/dashboard.jsp";
	@Autowired 
	private IComputerService computerService;
	private IMapperComputerDto mapper;// = new MapperComputer();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		final String view =req.getContextPath()+"/dashboard";
		String selectedPage=req.getParameter("selectedPage");
		boolean sortOrder=Boolean.parseBoolean(req.getParameter("asc"));
		
		
		List<Computer> computers =new ArrayList<Computer>();
		
		try {
			
			computers=computerService.getPageSortedByName(selectedPage==null?0:Integer.parseInt(selectedPage),sortOrder);
			
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
