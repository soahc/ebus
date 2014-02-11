package de.ebus.emarket.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class StockItem extends AEntity {

	private static final long serialVersionUID = 8724417543214530634L;
	//@ManyToOne(fetch = FetchType.LAZY)
	private long stock_id;
	private String productSerialNumber;
	private int count;
	
	public StockItem(){}
	
	public StockItem(long stockID, String productSerialNumber){
		setStock_id(stockID);
		setProductSerialNumber(productSerialNumber);
	}
	
	public long getStock_id() {
		return stock_id;
	}

	public void setStock_id(long stock_id) {
		this.stock_id = stock_id;
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
