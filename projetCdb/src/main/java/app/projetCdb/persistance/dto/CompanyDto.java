package app.projetCdb.persistance.dto;

import app.projetCdb.models.Company;

public class CompanyDto {
	private Long id;
	private String name;
	
	public CompanyDto(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public CompanyDto(Company company) {
		if(company!=null) {
			this.id=company.getId();
			this.name=company.getName();
		}
	}

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
