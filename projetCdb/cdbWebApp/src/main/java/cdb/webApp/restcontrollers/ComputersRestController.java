package cdb.webApp.restcontrollers;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cdb.binding.ComputerDto;
import cdb.binding.IMapperCompanyDto;
import cdb.binding.IMapperComputerDto;
import cdb.core.models.Company;
import cdb.core.models.Computer;
import cdb.service.ICompanyServices;
import cdb.service.IComputerService;
import cdb.service.ValidatorFormException;
import cdb.webApp.controllers.FormEditComputerValidator;
import cdb.webApp.controllers.IFormEditComputerValidator;

@RestController
@RequestMapping("/computers")
public class ComputersRestController {
	// services
	private IComputerService computerService;
	private ICompanyServices companyService; 
	private IMapperComputerDto mapper;
	private IMapperCompanyDto mapperCompany;
	//
	private IFormEditComputerValidator validator;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private final long DEFAULT_ID = 0L;

	public ComputersRestController(IComputerService computerService, ICompanyServices companyService,
			IMapperComputerDto mapper, IMapperCompanyDto mapperCompany) {
		this.computerService = computerService;
		this.companyService = companyService;
		this.mapper = mapper;
		this.mapperCompany = mapperCompany;
		this.validator = new FormEditComputerValidator();
	}

	@GetMapping
	public List<ComputerDto> all(@Nullable @RequestParam("rowsPage")Integer rowsPage,@Nullable @RequestParam("pageNumber")Integer pageNumber) {
		List<ComputerDto> computersDto = mapper.mapListComputer(computerService.getAll(rowsPage,pageNumber));
		return computersDto;
	}

	@GetMapping("/{id}")
	public ComputerDto one(@PathVariable Long id) {
		ComputerDto computerDto = null;
		try {
			Optional<Computer> optionalComputer;
			optionalComputer = computerService.finById(id);
			computerDto = optionalComputer.isPresent() ? mapper.mapComputer(optionalComputer.get()) : null;
		} catch (SQLException e) {
			logger.debug("Erreur sur find one computer : " + e.getMessage());
		}
		return computerDto;
	}
	
	
	
	public String getPage(@PathVariable(name="rowsPage", required=true)Long rowsPage,@RequestParam(name="pageNumber",required=true)Long pageNumber) {

		return "too";
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		computerService.delete(id);
	}

	@PostMapping
	public void create(@RequestParam(name = "computerName", required = true) String computerName,
			@RequestParam(name = "introducedDate") String introducedDate,
			@RequestParam(name = "discontinuedDate") String discontinuedDate,
			@RequestParam(name = "idCompany", required = true) String strIdCompany) {
		
		Long idCompany = strIdCompany != null ? Long.valueOf(strIdCompany) : DEFAULT_ID;
		Optional<Company> company = companyService.findById(idCompany);
		
		ComputerDto computerDto = new ComputerDto(DEFAULT_ID, computerName, introducedDate, discontinuedDate,
				(company.isPresent() ? company.get().getName() : null),
				company.isPresent() ? company.get().getId() : null);
		Computer newComputer = mapper.mapDto(computerDto);
		computerService.createComputer(newComputer);
	}
	

	@PutMapping("/{id}")
	public void update(@RequestParam(name = "computerName", required = true) String computerName,
			@RequestParam(name = "idComputer") String strIdComputer,
			@RequestParam(name = "introducedDate") String introducedDate,
			@RequestParam(name = "discontinuedDate") String discontinuedDate,
			@RequestParam(name = "idCompany", required = true) String strIdCompany) {
		Long idCompany = Long.parseLong(strIdCompany);
		Long idComputer = Long.parseLong(strIdComputer);
		Optional<Company> company = companyService.findById(idCompany);
		ComputerDto computerDto = new ComputerDto(idComputer, computerName, introducedDate, discontinuedDate,
				(company.isPresent() ? company.get().getName() : null),
				company.isPresent() ? company.get().getId() : null);

		try {
			validator.isValidEditForm(computerName, introducedDate, discontinuedDate, idCompany);
			Computer editingComputer = mapper.mapDto(computerDto);
			computerService.updateComputer(editingComputer);

		} catch (ValidatorFormException e) {
			switch (e.getInvalidityCause()) {
			case INCONSITENT_DATES:
				logger.debug("Les dates non conformes : " + e.getMessage());
				break;
			case EMPTY_NAME:
				logger.debug("Le champs d'édition du nom du computer est vide : " + e.getMessage());
				break;
			default:
				logger.warn("Erreur lors de la validation du formulaire d'�dition du computeur : " + e.getMessage());
				break;
			}
		}

	}

	

	
	/*
	public List<ComputerDto> search(@RequestParam(name = "research", required = true) String research) {
		List<ComputerDto> computersDto = new ArrayList<ComputerDto>();
		List<Computer> computers = new ArrayList<Computer>();

		try {
			computers = computerService.getPage(1, research);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		computersDto = (ArrayList<ComputerDto>) mapper.mapListComputer(computers);
		return computersDto;
	}
	*/

}
