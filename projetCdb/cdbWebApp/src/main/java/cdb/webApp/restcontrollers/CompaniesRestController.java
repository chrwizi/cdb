package cdb.webApp.restcontrollers;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cdb.binding.CompanyDto;
import cdb.binding.IMapperCompanyDto;
import cdb.core.models.Company;
import cdb.service.ICompanyServices;
import cdb.webApp.validator.CompanyForm;

@RestController
@RequestMapping("api/companies")
public class CompaniesRestController {
	private ICompanyServices companyService; 
	private IMapperCompanyDto mapperCompany;
	private Logger logger=LoggerFactory.getLogger(getClass());

	public CompaniesRestController(ICompanyServices companyService, IMapperCompanyDto mapperCompany) {
		this.companyService = companyService;
		this.mapperCompany = mapperCompany;
	}
	
	
	@GetMapping
	@CrossOrigin
	public List<CompanyDto> all(){
		List<CompanyDto> companiesDto=mapperCompany.mapListCompany(companyService.getAll());
		return companiesDto;
	}
	
	
	@GetMapping("/{id}")
	@CrossOrigin
	public CompanyDto one(@PathVariable Long id) {
		CompanyDto dto=null;
		Optional<Company> optionalCompany=companyService.findById(id);
		dto=optionalCompany.isPresent() ? mapperCompany.mapCompany(optionalCompany.get()):null;	
		return dto;
	}
	
	
	@PostMapping
	@CrossOrigin
	public void create(@Valid @RequestBody CompanyForm companyForm) {
		if((companyForm.getName()!=null) & !(companyForm.getName().equals(" "))) {
			companyService.create(new Company(companyForm.getId(),companyForm.getName()));
		}
	}	
	
	@PutMapping
	@CrossOrigin
	public void update(@Valid @RequestBody CompanyForm companyForm) {
		if((companyForm.getName()!=null) & !(companyForm.getName().equals(" "))) {
			Company company=new Company(companyForm.getId(),companyForm.getName());
			companyService.update(company);
		}
	}
	
	
	@DeleteMapping("/{id}")
	@CrossOrigin
	public void delete(@PathVariable Long id) {
		try {
			companyService.delete(id);
		} catch (SQLException e) {
			logger.debug("Errreur sur delete : "+e.getMessage());
		}
	}
	
	@CrossOrigin
	@GetMapping("/cred")
	@Secured("ROLE_ADMIN")
	public String testToken(){
		return "Good Credentials";
	}
	
	
	
	
	
	
	
}
