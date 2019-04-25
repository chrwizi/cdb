package cdb.webApp.validator;

import javax.validation.constraints.NotEmpty;

import org.springframework.lang.Nullable;

public class CompanyForm {
	@Nullable
	private Long id;
	@NotEmpty
	private String name;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
