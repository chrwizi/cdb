package app.projetCdb.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
import app.projetCdb.services.ICompanyServices;
import app.projetCdb.services.IComputerService;
import app.projetCdb.services.validators.FormEditComputerValidator;
import app.projetCdb.services.validators.IFormEditComputerValidator;

@Controller
@RequestMapping("/addComputer")
public class AddComputerServlet {
	// services
	private IComputerService computerService;
	private ICompanyServices companyService;

	// mapping objects
	private IMapperComputerDto mapper = new MapperComputer();
	private IMapperCompanyDto mapperCompany = new MapperCompanyDto();
	//
	private final long DEFAULT_ID = 0L;
	//
	IFormEditComputerValidator validator = new FormEditComputerValidator();
	Logger logger = LoggerFactory.getLogger(this.getClass());

	public AddComputerServlet(IComputerService computerService, ICompanyServices companyService) { 
		this.computerService = computerService;
		this.companyService = companyService;
	}

	@GetMapping
	protected String doGet(Model model) {
		// get computers from database
		List<Company> companies = companyService.getAll();
		List<CompanyDto> companiesDto = mapperCompany.mapListCompany(companies);
		model.addAttribute("companies", companiesDto);
		return "addComputer";
	}

	@PostMapping
	protected RedirectView doPost(@RequestParam(name = "computerName", required = true) String computerName,
			@RequestParam(name = "introducedDate") String introducedDate,
			@RequestParam(name = "discontinuedDate") String discontinuedDate,
			@RequestParam(name = "idCompany", required = true) String strIdCompany, RedirectAttributes attributes) {
		
		Long idCompany = strIdCompany != null ? Long.valueOf(strIdCompany) : DEFAULT_ID;
		Optional<Company> company = companyService.findById(idCompany);
		
		ComputerDto computerDto = new ComputerDto(DEFAULT_ID, computerName, introducedDate, discontinuedDate,
				(company.isPresent() ? company.get().getName() : null),
				company.isPresent() ? company.get().getId() : null);
		
		Computer newComputer = mapper.mapDto(computerDto);

		try {
			validator.isValidEditForm(computerName, introducedDate, discontinuedDate, idCompany);
			computerService.createComputer(newComputer);
		//	attributes.addAttribute("computer", computerDto);
		} catch (ValidatorFormException e) {
			switch (e.getInvalidityCause()) {
			case INCONSITENT_DATES:
			//	attributes.addAttribute("errorMessage", "les dates de sont incohérentes id vaut ");
				//attributes.addAttribute("computer", computerDto);
				logger.debug("Les dates non conformes ");
				break;
			case EMPTY_NAME:
				//attributes.addAttribute("errorMessage", "Le champ computer ne doit pas être vide");
				//attributes.addAttribute("computer", computerDto);
				logger.debug("Le champs d'�dition du nom du computer est vide");
				break;
			default:
				logger.warn("Erreur lors de la validation du formulaire d'�dition du computeur " + computerName);
			}
		}
		
		return new RedirectView("/projetCdb");
	}

}
