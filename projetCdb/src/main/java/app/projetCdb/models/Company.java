package app.projetCdb.models;

public class Company {
	private Long id;
	private String name;

	public Company(String name) {
		this.name = name;
	}

	public Company(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("[").append(getName());
		buffer.append("]");
		return buffer.toString();
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
