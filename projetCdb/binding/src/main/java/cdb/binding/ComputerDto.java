package cdb.binding;

public class ComputerDto {
	private Long id;
	private String name;
	private String introduced;
	private String discontinued;
	private String company;
	private Long companyId;
	
	

	public ComputerDto() {}

	public ComputerDto(Long id, String name, String introduced, String discontinued, String company, Long companyId) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
		this.companyId = companyId;
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

	public void setCompany(String compagny) {
		this.company = compagny;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

}
