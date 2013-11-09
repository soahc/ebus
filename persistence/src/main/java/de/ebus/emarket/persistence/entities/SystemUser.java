package de.ebus.emarket.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class SystemUser extends AEntity {

	private static final long serialVersionUID = 5768237668903485327L;
	private String username;
	private String password;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Company company;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}
