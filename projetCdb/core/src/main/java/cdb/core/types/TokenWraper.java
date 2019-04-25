package cdb.core.types;

public class TokenWraper {
	private String jwt;

	public TokenWraper(String token) {
		super();
		this.jwt = token;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String token) {
		this.jwt = token;
	}
}
