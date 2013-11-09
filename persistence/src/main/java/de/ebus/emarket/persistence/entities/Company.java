package de.ebus.emarket.persistence.entities;

public class Company extends AEntity {

	private static final long serialVersionUID = 278207301893685687L;
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
