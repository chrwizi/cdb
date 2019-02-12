package app.projetCdb.models;

public class Computer {
	private Long id;
	private String name	;
	private String introduced;
	private String discontinued	;
	private Long company_id;
	
	
	
	public Computer(Long id, String name, String introduced, String discontinued, Long company_id) {
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
