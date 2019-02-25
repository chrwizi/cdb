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

import app.projetCdb.models.Computer;
import app.projetCdb.persistance.ComputerDao;
import app.projetCdb.persistance.ComputersPage;
import app.projetCdb.persistance.DbAccess;
import app.projetCdb.persistance.IPageSelect;
import app.projetCdb.persistance.dto.ComputerDto;
import app.projetCdb.persistance.dto.IMapperComputerDto;
import app.projetCdb.persistance.dto.MapperComputer;
import app.projetCdb.services.IComputerService;
import app.projetCdb.services.ComputerServices;

@WebServlet(name = "cdb", urlPatterns = "/")
public class Homeservlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String REDIRECT_VIEW = "/WEB-INF/dashboard.jsp";

	private ComputerDao computerDao = new ComputerDao(DbAccess.getInstance());
	private IComputerService computerService = new ComputerServices(computerDao);
	private IMapperComputerDto mapper = new MapperComputer();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Optional<List<Computer>> optionalComputers = computerService.getPage(1);
		ArrayList<ComputerDto> computers = (ArrayList<ComputerDto>) mapper.mapListComputer(optionalComputers.get());

		request.setAttribute("nbPages", computerService.getNbPages());
		request.setAttribute("computers", computers);
		request.setAttribute("nbComputers", computers.size());

		this.getServletContext().getRequestDispatcher(REDIRECT_VIEW).forward(request, response);
	}

}
