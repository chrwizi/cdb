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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

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

//@WebServlet(name = "edit", urlPatterns = "/editComputer")

@Controller
@RequestMapping("/editComputer")
public class EditComputerSevlet {
	// views
	private static final String GET_VIEW = "editComputer";
	private static final String REDIRECT_COMPUTER_NOT_FOUND = "500";

	// services
	@Autowired
	@Qualifier("computerService")
	private IComputerService computerService;
	@Autowired
	@Qualifier("companyService")
	private ICompanyServices companyService;

	// mappers
	private IMapperComputerDto mapper = new MapperComputer();
	private IMapperCompanyDto mapperCompany = new MapperCompanyDto();
	// edit computer validator
	private IFormEditComputerValidator validator = new FormEditComputerValidator();

	//
	private long DEFAULT_ID = 0L;
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping
	public String getComputerEditForm(@RequestParam(name = "idComputer", required = true) String idComputer,
			Model model) {
		Long id = Long.parseLong(idComputer);
		Optional<Computer> optionalComputer = Optional.empty();

		try {
			optionalComputer = computerService.finById(id);
		} catch (SQLException e) {
			logger.debug("erreur Sql lors de la recherche de computer à éditer");
		}

		if (!optionalComputer.isPresent()) {
			// there is no computer corresponding with Id
			String errorMessage = "Aucun ordinateur trouvé";
			model.addAttribute("errorMessage", errorMessage);
			return REDIRECT_COMPUTER_NOT_FOUND;
		}
		List<Company> companies = companyService.getAll();
		ComputerDto computerDto = mapper.mapComputer(optionalComputer.get());
		List<CompanyDto> companiesDto = mapperCompany.mapListCompany(companies);
		model.addAttribute("companies", companiesDto);
		model.addAttribute("computer", computerDto);
		return GET_VIEW;
	}

	@PostMapping
	public RedirectView postComputerEditForm(@RequestParam(name = "computerName", required = true) String computerName,
			@RequestParam(name = "idComputer") String strIdComputer,
			@RequestParam(name = "introducedDate") String introducedDate,
			@RequestParam(name = "discontinuedDate") String discontinuedDate,
			@RequestParam(name = "idCompany", required = true) String strIdCompany, RedirectAttributes attributes) {

		// TODO validation technique
		Long idCompany = Long.parseLong(strIdCompany);
		Long idComputer = Long.parseLong(strIdComputer);
		Optional<Company> company = companyService.findById(idCompany);
		ComputerDto computerDto = new ComputerDto(DEFAULT_ID, computerName, introducedDate, discontinuedDate,
				(company.isPresent() ? company.get().getName() : null),
				company.isPresent() ? company.get().getId() : null);

		try {
			// functional data validation
			validator.isValidEditForm(computerName, introducedDate, discontinuedDate, idCompany);
			// data mapping
			Computer editingComputer = mapper.mapDto(computerDto);
			computerService.updateComputer(editingComputer);
			//model.addAttribute("computer", computerDto);

		} catch (ValidatorFormException e) {
			switch (e.getInvalidityCause()) {
			case INCONSITENT_DATES:
				attributes.addAttribute("errorMessage", "les dates de sont incohérentes id vaut ");
				attributes.addAttribute("computer", computerDto);

				logger.debug("Les dates non conformes ");
				break;
			case EMPTY_NAME:
				attributes.addAttribute("errorMessage", "Le champ computer ne doit pas être vide");
				attributes.addAttribute("computer", computerDto);

				logger.debug("Le champs d'édition du nom du computer est vide");
				break;
			default:
				logger.warn("Erreur lors de la validation du formulaire d'�dition du computeur " + computerName);
			}
		}

		return new RedirectView("/");
	} 

	/*
	 * protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	 * throws ServletException, IOException { // get form data String computerName =
	 * (String) req.getParameter("computerName"); String introducedDate = (String)
	 * req.getParameter("introducedDate"); String discontinueddDate = (String)
	 * req.getParameter("discontinuedDate"); Long idCompany =
	 * (!(req.getParameter("idCompany") == null) &&
	 * req.getParameter("idCompany").equals("")) ?
	 * Long.valueOf(req.getParameter("idCompany")) : DEFAULT_ID; Optional<Company>
	 * company = companyService.findById(idCompany); // prepare Dto ComputerDto
	 * computerDto = new ComputerDto(DEFAULT_ID, computerName, introducedDate,
	 * discontinueddDate, (company.isPresent() ? company.get().getName() : null),
	 * company.isPresent() ? company.get().getId() : null); try { // data validation
	 * validator.isValidEditForm(computerName, introducedDate, discontinueddDate,
	 * idCompany); // data mapping Computer editingComputer =
	 * mapper.mapDto(computerDto); computerService.updateComputer(editingComputer);
	 * req.setAttribute("computer", computerDto); // redirect request
	 * this.getServletContext().getRequestDispatcher(GET_VIEW).forward(req, resp); }
	 * catch (ValidatorFormException e) { switch (e.getInvalidityCause()) { case
	 * INCONSITENT_DATES: req.setAttribute("errorMessage",
	 * "les dates de sont incohérentes id vaut "); req.setAttribute("computer",
	 * computerDto);
	 * this.getServletContext().getRequestDispatcher(GET_VIEW).forward(req, resp);
	 * logger.debug("Les dates non conformes "); break; case EMPTY_NAME:
	 * req.setAttribute("errorMessage", "Le champ computer ne doit pas être vide");
	 * req.setAttribute("computer", computerDto);
	 * this.getServletContext().getRequestDispatcher(GET_VIEW).forward(req, resp);
	 * logger.debug("Le champs d'�dition du nom du computer est vide"); break;
	 * default: logger.
	 * warn("Erreur lors de la validation du formulaire d'�dition du computeur " +
	 * computerName); } } }
	 * 
	 */

	//
	// @Override
	// public void init() throws ServletException {
	// super.init();
	// SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	// }

}
