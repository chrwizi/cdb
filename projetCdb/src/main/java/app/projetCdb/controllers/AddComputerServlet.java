package app.projetCdb.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.projetCdb.exceptions.IDCompanyNotFoundException;
import app.projetCdb.models.Company;
import app.projetCdb.models.Computer;
import app.projetCdb.persistance.CompanyDao;
import app.projetCdb.persistance.ComputerDao;
import app.projetCdb.persistance.DbAccess;
import app.projetCdb.persistance.dto.CompanyDto;
import app.projetCdb.persistance.dto.ComputerDto;
import app.projetCdb.persistance.dto.IMapperCompanyDto;
import app.projetCdb.persistance.dto.IMapperComputerDto;
import app.projetCdb.persistance.dto.MapperCompanyDto;
import app.projetCdb.persistance.dto.MapperComputer;
import app.projetCdb.services.CompanyService;
import app.projetCdb.services.ComputerServices;
import app.projetCdb.services.ICompanyServices;
import app.projetCdb.services.IComputerService;

@WebServlet(name = "add", urlPatterns = "/addComputer")
public class AddComputerServlet extends HttpServlet {
	// services
	private IComputerService computerService = new ComputerServices();
	private ICompanyServices companyService = new CompanyService();
	// mapping objects
	private IMapperComputerDto mapper = new MapperComputer();
	private IMapperCompanyDto mapperCompany = new MapperCompanyDto();
	//
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		final String ADD_COMPUTER_VIEW = "/WEB-INF/addComputer.jsp";
		//get computers from database 
		Optional<List<Company>> optionalCompanies = companyService.getAll();
		Optional<List<CompanyDto>> companiesDto = mapperCompany.mapListCompany(optionalCompanies.get());
		//send computers Dto to view 
		req.setAttribute("companies", companiesDto.get());
		this.getServletContext().getRequestDispatcher(ADD_COMPUTER_VIEW).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		final String POST_ADD_COMPUTER = "/WEB-INF/tmp.jsp";
		// get form data
		String computerName = (String) req.getParameter("computerName");
		String introducedDate = (String) req.getParameter("introducedDate");
		String discontinueddDate = (String) req.getParameter("discontinuedDate");
		Long idCompany = Long.valueOf(req.getParameter("idCompany"));
		// treat data
		Optional<Company> company = companyService.findById(idCompany);
		ComputerDto computerDto = new ComputerDto(0L, computerName, introducedDate, discontinueddDate,
				(company.isPresent() ? company.get().getName() : null),
				company.isPresent() ? company.get().getId() : null);
		Computer newComputer = mapper.mapDto(computerDto);
		//create new computer in database
		computerService.createComputer(newComputer);
		req.setAttribute("computer", computerDto);
		// redirect request
		this.getServletContext().getRequestDispatcher(POST_ADD_COMPUTER).forward(req, resp);
	}

}
