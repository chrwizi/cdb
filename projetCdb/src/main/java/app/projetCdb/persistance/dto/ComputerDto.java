package app.projetCdb.persistance.dto;

import java.util.Date;

public class ComputerDto {
	private Long id;
	private String name	;
	private Date introduced;
	private Date discontinued	;
	private String compagny;
	private Long companyId;
	
	
	
	
	
	public ComputerDto(Long id, String name, Date introduced, Date discontinued, String compagny, Long companyId) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.compagny = compagny;
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
	public Date getIntroduced() {
		return introduced;
	}
	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}
	public Date getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(Date discontinued) {
		this.discontinued = discontinued;
	}
	public String getCompagny() {
		return compagny;
	}
	public void setCompagny(String compagny) {
		this.compagny = compagny;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	
	
}
