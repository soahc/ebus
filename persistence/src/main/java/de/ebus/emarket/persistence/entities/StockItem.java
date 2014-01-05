package de.ebus.emarket.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class StockItem extends AEntity {

	private static final long serialVersionUID = 8724417543214530634L;
	@ManyToOne(fetch = FetchType.LAZY)
	private Stock stock;
	private String productSerialNumber;
	private int count;
	
	public StockItem(){}
	
	public StockItem(Stock stock,String productSerialNumber){
		setStock(stock);
		setProductSerialNumber(productSerialNumber);
	}
	
	public Stock getStock() {
		return stock;
	}
	public void setStock(Stock stock) {
		this.stock = stock;
	}
	public String getProductSerialNumber() {
		return productSerialNumber;
	}
	public void setProductSerialNumber(String productSerialNumber) {
		this.productSerialNumber = productSerialNumber;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
