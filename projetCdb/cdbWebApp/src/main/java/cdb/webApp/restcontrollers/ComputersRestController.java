package cdb.webApp.restcontrollers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("api/computers")
public class ComputersRestController {
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
	@CrossOrigin
	public List<ComputerDto> all(@Nullable @RequestParam("rowsPage") Integer rowsPage,
			@Nullable @RequestParam("pageNumber") Integer pageNumber) {
		List<ComputerDto> computersDto = mapper.mapListComputer(computerService.getAll(rowsPage, pageNumber));
		return computersDto;
	}

	@GetMapping("/{id}")
	@CrossOrigin
	public ComputerDto one(@PathVariable Long id) {
		ComputerDto computerDto = null;
		try {
			Optional<Computer> optionalComputer = computerService.finById(id);
			computerDto = optionalComputer.isPresent() ? mapper.mapComputer(optionalComputer.get()) : null;
		} catch (SQLException e) {
			logger.debug("Erreur sur find one computer : " + e.getMessage());
		}
		return computerDto;
	}

	@DeleteMapping("/{id}")
	@CrossOrigin
	public void delete(@PathVariable Long id) {
		computerService.delete(id);
	}

	@PostMapping
	@CrossOrigin
	public ComputerDto create(@RequestBody ComputerDto computerDto) {
		System.out.println("Request Post cmputer done ");
		Computer newComputer = mapper.mapDto(computerDto);
		// TODO vérification technique et fonctionnelle du Dto
		computerService.createComputer(newComputer);
		System.out.println("New Computer created ");
		computerDto.setId(newComputer.getId());
	
		return computerDto;
	}

	@PutMapping("/{id}")
	@CrossOrigin
	public void update(@RequestBody ComputerDto computerDto) {
		try {
			validator.isValidEditForm(computerDto.getName(), computerDto.getIntroduced(), computerDto.getDiscontinued(),
					computerDto.getCompanyId());
			Computer editingComputer = mapper.mapDto(computerDto);
			
			computerService.updateComputer(editingComputer);

		} catch (ValidatorFormException e) {
			switch (e.getInvalidityCause()) {
			case INCONSITENT_DATES:
				logger.debug("Les dates non conformes : " + e.getMessage());
				break;
			case EMPTY_NAME:
				logger.debug("Le champs d'Ã©dition du nom du computer est vide : " + e.getMessage());
				break;
			default:
				logger.warn("Erreur lors de la validation du formulaire d'ï¿½dition du computeur : " + e.getMessage());
				break;
			}
		}
	}

	@CrossOrigin
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

}
