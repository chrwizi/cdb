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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.projetCdb.exceptions.IDCompanyNotFoundException;
import app.projetCdb.exceptions.ValidatorFormException;
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
	private final long DEFAULT_ID=0L;
	//
	IFormEditComputerValidator validator=new FormEditComputerValidator();
	Logger logger=LoggerFactory.getLogger(this.getClass());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		final String ADD_COMPUTER_VIEW = "/WEB-INF/addComputer.jsp";
		//get computers from database 
		List<Company> companies = companyService.getAll();
		List<CompanyDto> companiesDto = mapperCompany.mapListCompany(companies);
		req.setAttribute("companies", companiesDto);
		this.getServletContext().getRequestDispatcher(ADD_COMPUTER_VIEW).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		final String POST_ADD_COMPUTER = "/WEB-INF/tmp.jsp";
		// get form data
		String computerName = (String) req.getParameter("computerName");
		String introducedDate = (String) req.getParameter("introducedDate");
		String discontinueddDate = (String) req.getParameter("discontinuedDate");
		Long idCompany =req.getParameter("idCompany")!=null? Long.valueOf(req.getParameter("idCompany")):DEFAULT_ID;
		// treat data
		Optional<Company> company = companyService.findById(idCompany);
		ComputerDto computerDto = new ComputerDto(DEFAULT_ID, computerName, introducedDate, discontinueddDate,
				(company.isPresent() ? company.get().getName() : null),
				company.isPresent() ? company.get().getId() : null);
		Computer newComputer = mapper.mapDto(computerDto); 
		try {
			validator.isValidEditForm(computerName, introducedDate, discontinueddDate, idCompany);
			computerService.createComputer(newComputer);
			req.setAttribute("computer", computerDto);
			this.getServletContext().getRequestDispatcher(POST_ADD_COMPUTER).forward(req, resp);
		} catch (ValidatorFormException e) {
			switch (e.getInvalidityCause()) {
			case INCONSITENT_DATES:
				req.setAttribute("errorMessage", "les dates de sont incohÃ©rentes id vaut ");
				req.setAttribute("computer", computerDto);
				this.getServletContext().getRequestDispatcher(POST_ADD_COMPUTER).forward(req, resp);
				logger.debug("Les dates non conformes ");
				break;
			case EMPTY_NAME:
				req.setAttribute("errorMessage", "Le champ computer ne doit pas Ãªtre vide");
				req.setAttribute("computer", computerDto);
				this.getServletContext().getRequestDispatcher(POST_ADD_COMPUTER).forward(req, resp);
				logger.debug("Le champs d'édition du nom du computer est vide");
				break;
			default:
				logger.warn("Erreur lors de la validation du formulaire d'édition du computeur "+computerName);
			}
		}
	}

}
