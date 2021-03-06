package cdb.webApp.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import cdb.binding.CompanyDto;
import cdb.binding.ComputerDto;
import cdb.binding.IMapperCompanyDto;
import cdb.binding.IMapperComputerDto;
import cdb.core.models.Company;
import cdb.core.models.Computer;
import cdb.service.ICompanyServices;
import cdb.service.IComputerService;
import cdb.service.ValidatorFormException;


@Controller
@RequestMapping("/addComputer")
@PreAuthorize("hasAnyRole('ADMIN')")
public class AddComputerServlet {
	// services
	private IComputerService computerService; 
	private ICompanyServices companyService;	
	private IMapperComputerDto mapper;
	private IMapperCompanyDto mapperCompany;
	//
	private final long DEFAULT_ID = 0L;
	IFormEditComputerValidator validator = new FormEditComputerValidator();
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
public AddComputerServlet(IComputerService computerService, ICompanyServices companyService,
			IMapperComputerDto mapper, IMapperCompanyDto mapperCompany) {
		this.computerService = computerService;
		this.companyService = companyService;
		this.mapper = mapper;
		this.mapperCompany = mapperCompany;
	}

	@GetMapping
	protected String doGet(Model model) {
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
		} catch (ValidatorFormException e) {
			switch (e.getInvalidityCause()) {
			case INCONSITENT_DATES:
				logger.debug("Les dates non conformes ");
				break;
			case EMPTY_NAME:
				logger.debug("Le champs d'�dition du nom du computer est vide");
				break;
			default:
				logger.warn("Erreur lors de la validation du formulaire d'�dition du computeur " + computerName);
			}
		}
		return new RedirectView("/projetCdb");
	}

}
