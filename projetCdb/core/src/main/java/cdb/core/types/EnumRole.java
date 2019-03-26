package cdb.core.types;

public enum EnumRole {
	Admin("ADMIN"),Premium("PREMIUM");
	private String role;
	
	private EnumRole(String role) {
		this.role=role;
	}
	
	EnumRole(){}

	public String getRole() {
		return role;
	}



}
