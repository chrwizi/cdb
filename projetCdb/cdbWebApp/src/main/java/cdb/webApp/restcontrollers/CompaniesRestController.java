package cdb.webApp.restcontrollers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import cdb.binding.CompanyDto;
import cdb.binding.IMapperCompanyDto;
import cdb.core.models.Company;
import cdb.service.ICompanyServices;

@RestController("/api/companies")
@CrossOrigin
public class CompaniesRestController {
	private ICompanyServices companyService; 
	private IMapperCompanyDto mapperCompany;

	public CompaniesRestController(ICompanyServices companyService, IMapperCompanyDto mapperCompany) {
		this.companyService = companyService;
		this.mapperCompany = mapperCompany;
	}
	
	
	@GetMapping
	public List<CompanyDto> all(){
		List<CompanyDto> companiesDto=mapperCompany.mapListCompany(companyService.getAll());
		return companiesDto;
	}
	
	
	@GetMapping("/{id}")
	public CompanyDto one(@PathVariable Long id) {
		CompanyDto dto=null;
		Optional<Company> optionalCompany=companyService.findById(id);
		dto=optionalCompany.isPresent() ? mapperCompany.mapCompany(optionalCompany.get()):null;	
		return dto;
	}
	
	
	
	@GetMapping("cred")
	public String testToken(){
		return "Good Credentials";
	}
	
	
	
	
	
	
}
