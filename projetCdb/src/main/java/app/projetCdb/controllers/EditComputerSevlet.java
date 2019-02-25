package app.projetCdb.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import app.projetCdb.services.validators.FormEditComputerValidator;
import app.projetCdb.services.validators.IFormEditComputerValidator;


@WebServlet(name="edit",urlPatterns="/editComputer")
public class EditComputerSevlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	//views
	private static final String GET_VIEW="/WEB-INF/editComputer.jsp";
	private static final String REDIRECT_COMPUTER_NOT_FOUND="/WEB-INF/500.jsp";
	private static final String POST_VIEW="/WEB-INF/tmp.jsp";
	//database access
	private ComputerDao computerDao=new ComputerDao(DbAccess.getInstance());
	private CompanyDao companyDao=new CompanyDao(DbAccess.getInstance());
	//services
	private IComputerService computerService = new ComputerServices(computerDao);
	private ICompanyServices companyService=new CompanyService(companyDao);
	//mappers
	private IMapperComputerDto mapper=new MapperComputer();
	private IMapperCompanyDto mapperCompany=new MapperCompanyDto();
	//edit computer validator
	private IFormEditComputerValidator validator=new FormEditComputerValidator();
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long idComputer=Long.valueOf(req.getParameter("idComputer"));
		//erreur sur id à gérer 

		Optional<Computer> optionalComputer=computerService.finById(idComputer);
		if(!optionalComputer.isPresent()) {
			//there is no computer corresponding with Id
			String errorMessage="Aucun ordinateur trouvé";
			req.setAttribute("errorMessage", errorMessage);
			this.getServletContext().getRequestDispatcher(REDIRECT_COMPUTER_NOT_FOUND).forward(req, resp);
		}
		else {
			Optional<List<Company>> optionalCompanies=companyService.getAll();  
			ComputerDto computerDto=mapper.mapComputer(optionalComputer.get());		
			Optional<List<CompanyDto>>companiesDto=mapperCompany.mapListCompany(optionalCompanies.get());
			
			req.setAttribute("companies", companiesDto.get());
			req.setAttribute("computer", computerDto);
			
			this.getServletContext().getRequestDispatcher(GET_VIEW).forward(req, resp);
		}
	}



	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// get form data
		String computerName = (String) req.getParameter("computerName");
		String introducedDate = (String) req.getParameter("introducedDate");
		String discontinueddDate = (String) req.getParameter("discontinuedDate");
		Long idCompany = Long.valueOf(req.getParameter("idCompany"));
		//treat data
		Optional<Company> company = companyService.findById(idCompany);
		ComputerDto computerDto = new ComputerDto(0L, computerName, introducedDate, discontinueddDate,
				(company.isPresent() ? company.get().getName() : null),
				company.isPresent() ? company.get().getId() : null);

		Computer editingComputer = mapper.mapDto(computerDto);
		computerService.updateComputer(editingComputer);
		req.setAttribute("computer", computerDto);
		
		this.getServletContext().getRequestDispatcher(GET_VIEW).forward(req, resp);
	}
	
	
	



}
