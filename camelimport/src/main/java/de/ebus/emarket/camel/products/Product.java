package de.ebus.emarket.camel.products;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import de.ebus.emarket.camel.entities.AEntity;

@Entity
@CsvRecord(separator = ";")
public class Product extends AEntity {
	private static final long serialVersionUID = -4503595932183481371L;

	private long company_id;
	@DataField(pos = 1, required=true)
	private String serialNumber;
	@DataField(pos = 2, required=true)
	private String name;
	@DataField(pos = 3, precision = 2, required=true)
	@Column(name = "amount", precision = 19, scale = 2)
	private BigDecimal price;
	@DataField(pos = 4, required=true)
	private String imagePath;
	
	
	public Product(String serialNumber, String name, BigDecimal price, long company_id){
		setSerialNumber(serialNumber);
		setName(name);
		setPrice(price);
		setCompany_id(company_id);
	}
	
	public Product(){}
	
	public long getCompany_id() {
		return company_id;
	}

	public void setCompany_id(long company_id) {
		this.company_id = company_id;
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

	@Override
	public String toString(){
		return "companyID: " +company_id + ", serialnumber: "+ serialNumber + ", name: " + name + ", price: "+price;
	}
}
