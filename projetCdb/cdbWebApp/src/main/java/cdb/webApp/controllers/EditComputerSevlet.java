package cdb.webApp.controllers;

import java.sql.SQLException;
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

import app.projetCdb.services.ICompanyServices;
import app.projetCdb.services.IComputerService;
import app.projetCdb.services.ValidatorFormException;
import cdb.binding.CompanyDto;
import cdb.binding.ComputerDto;
import cdb.binding.IMapperCompanyDto;
import cdb.binding.IMapperComputerDto;
import cdb.binding.MapperCompanyDto;
import cdb.binding.MapperComputer;
import cdb.core.models.Company;
import cdb.core.models.Computer;

@Controller
@RequestMapping("/editComputer")
public class EditComputerSevlet {
	// views
	private static final String GET_VIEW = "editComputer";
	private static final String REDIRECT_COMPUTER_NOT_FOUND = "500";

	// services
	private IComputerService computerService;
	private ICompanyServices companyService;

	@RequestMapping("delete")
	public String delete() {

		return null;
	}

	public EditComputerSevlet(IComputerService computerService, ICompanyServices companyService) {
		this.computerService = computerService;
		this.companyService = companyService;
	}

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

		System.out.println(computerDto);

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
		ComputerDto computerDto = new ComputerDto(idComputer, computerName, introducedDate, discontinuedDate,
				(company.isPresent() ? company.get().getName() : null),
				company.isPresent() ? company.get().getId() : null);

		try {
			// functional data validation
			validator.isValidEditForm(computerName, introducedDate, discontinuedDate, idCompany);
			// data mapping
			Computer editingComputer = mapper.mapDto(computerDto);
			computerService.updateComputer(editingComputer);

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

		return new RedirectView("/projetCdb");
	}

}
