package app.projetCdb.models;

import java.util.Date;

public class Computer {
	private Long id;
	private String name	;
	private Date introduced;
	private Date discontinued	;
	private Long companyID;
	
	
	
	public Computer(Long id, String name, Date introduced, Date discontinued, Long company_id) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyID = company_id;
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
	public Long getCompany_id() {
		return companyID;
	}
	public void setCompany_id(Long company_id) {
		this.companyID = company_id;
	}


	@Override
	public String toString() {
		StringBuffer buffer=new StringBuffer("[");
		buffer.append(getName()+" companyId: "+companyID+" introduced: "+introduced+"]");
		//buffer.append(")
		return buffer.toString();
	}
 

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyID == null) ? 0 : companyID.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Computer other = (Computer) obj;
		if (companyID == null) {
			if (other.companyID != null)
				return false;
		} else if (!companyID.equals(other.companyID))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	
	


}
