package de.ebus.emarket.persistence.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class Product extends AEntity {
	private static final long serialVersionUID = -4503595932183481371L;

	@ManyToOne(fetch = FetchType.LAZY)
	private Company company;
	private String serialNumber;
	private String name;
	@Column(name = "amount", precision = 19, scale = 2)
	private BigDecimal price;
	private String imagePath;

	public Product(String serialNumber, String name, BigDecimal price, Company company){
		setSerialNumber(serialNumber);
		setName(name);
		setPrice(price);
		setCompany(company);
	}
	
	public Product(){}
	
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}
