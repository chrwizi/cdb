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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import app.projetCdb.exceptions.ValidatorFormException;
import app.projetCdb.models.Company;
import app.projetCdb.models.Computer;
import app.projetCdb.persistance.dto.CompanyDto;
import app.projetCdb.persistance.dto.ComputerDto;
import app.projetCdb.persistance.dto.IMapperCompanyDto;
import app.projetCdb.persistance.dto.IMapperComputerDto;
import app.projetCdb.persistance.dto.MapperCompanyDto;
import app.projetCdb.persistance.dto.MapperComputer;
import app.projetCdb.services.CompanyService;
import app.projetCdb.services.ComputerService;
import app.projetCdb.services.ICompanyServices;
import app.projetCdb.services.IComputerService;
import app.projetCdb.services.validators.FormEditComputerValidator;
import app.projetCdb.services.validators.IFormEditComputerValidator;

@WebServlet(name = "edit", urlPatterns = "/editComputer")

//@Controller
//@RequestMapping("/editComputer")
public class EditComputerSevlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// views
	private static final String GET_VIEW = "/WEB-INF/editComputer.jsp";
	private static final String REDIRECT_COMPUTER_NOT_FOUND = "/WEB-INF/500.jsp";
	
	// services
	@Autowired @Qualifier("computerService")
	private IComputerService computerService ;
	@Autowired @Qualifier("companyService")
	private ICompanyServices companyService;
	
	
	// mappers
	private IMapperComputerDto mapper = new MapperComputer();
	private IMapperCompanyDto mapperCompany = new MapperCompanyDto();
	// edit computer validator
	private IFormEditComputerValidator validator = new FormEditComputerValidator();
	
	//
	private long DEFAULT_ID=0L;
	Logger logger=LoggerFactory.getLogger(this.getClass());

	@Override
	protected void doGet(
			HttpServletRequest req, HttpServletResponse resp
			) throws ServletException, IOException {
		
		
		Long idComputer = Long.valueOf(req.getParameter("idComputer"));
		// parse error to handle
		Optional<Computer> optionalComputer = Optional.empty();
		
		try {
			optionalComputer = computerService.finById(idComputer);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		if (!optionalComputer.isPresent()) {
			// there is no computer corresponding with Id
			String errorMessage = "Aucun ordinateur trouvé";
			req.setAttribute("errorMessage", errorMessage);
			this.getServletContext().getRequestDispatcher(REDIRECT_COMPUTER_NOT_FOUND).forward(req, resp);
		} 
		
		else {
			List<Company> companies = companyService.getAll();
			ComputerDto computerDto = mapper.mapComputer(optionalComputer.get());
			List<CompanyDto> companiesDto = mapperCompany.mapListCompany(companies);
			req.setAttribute("companies", companiesDto);
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
		Long idCompany = (!(req.getParameter("idCompany")==null)&&req.getParameter("idCompany").equals("")) ? Long.valueOf(req.getParameter("idCompany")) : DEFAULT_ID;
		Optional<Company> company = companyService.findById(idCompany);
		//prepare Dto
		ComputerDto computerDto = new ComputerDto(DEFAULT_ID, computerName, introducedDate, discontinueddDate,
				(company.isPresent() ? company.get().getName() : null),
				company.isPresent() ? company.get().getId() : null);
		try {
			// data validation
			validator.isValidEditForm(computerName, introducedDate, discontinueddDate, idCompany);
			// data mapping
			Computer editingComputer = mapper.mapDto(computerDto);
			computerService.updateComputer(editingComputer);  
			req.setAttribute("computer", computerDto);
			// redirect request
			this.getServletContext().getRequestDispatcher(GET_VIEW).forward(req, resp);
		} catch (ValidatorFormException e) {
			switch (e.getInvalidityCause()) {
			case INCONSITENT_DATES:
				req.setAttribute("errorMessage", "les dates de sont incohérentes id vaut ");
				req.setAttribute("computer", computerDto);
				this.getServletContext().getRequestDispatcher(GET_VIEW).forward(req, resp);
				logger.debug("Les dates non conformes ");
				break;
			case EMPTY_NAME:
				req.setAttribute("errorMessage", "Le champ computer ne doit pas être vide");
				req.setAttribute("computer", computerDto);
				this.getServletContext().getRequestDispatcher(GET_VIEW).forward(req, resp);
				logger.debug("Le champs d'�dition du nom du computer est vide");
				break;
			default:
				logger.warn("Erreur lors de la validation du formulaire d'�dition du computeur "+computerName);
			}
		}
	}
	
	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

}
