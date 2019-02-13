package app.projetCdb.models;

import java.sql.Timestamp;

public class Computer {
	private Long id;
	private String name	;
	private Timestamp introduced;
	private Timestamp discontinued	;
	private Long company_id;
	
	
	
	public Computer(Long id, String name, Timestamp introduced, Timestamp discontinued, Long company_id) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company_id = company_id;
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
	public Timestamp getIntroduced() {
		return introduced;
	}
	public void setIntroduced(Timestamp introduced) {
		this.introduced = introduced;
	}
	public Timestamp getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(Timestamp discontinued) {
		this.discontinued = discontinued;
	}
	public Long getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Long company_id) {
		this.company_id = company_id;
	}


	@Override
	public String toString() {
		StringBuffer buffer=new StringBuffer("[");
		buffer.append(getName()+"]");
		//buffer.append(")
		return buffer.toString();
	}
	
	


}
