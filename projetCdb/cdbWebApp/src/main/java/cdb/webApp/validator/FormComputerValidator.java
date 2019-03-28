package cdb.webApp.validator;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import cdb.binding.ComputerDto;

public class FormComputerValidator  implements Validator{
	@NotEmpty
	@Size(min=2)
	private String name;
	private String introduced;
	private String discontinued;
	private String company;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIntroduced() {
		return introduced;
	}
	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}
	public String getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return ComputerDto.class.equals(clazz);
	}
	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
	}
	
	
}
