package de.ebus.emarket.persistence.entities;

import javax.persistence.Entity;

@Entity
public class Stock extends AEntity {

	private static final long serialVersionUID = -4829711434009233731L;
	private long companyID;
	private String stockName;

	public Stock() {
	}

	public Stock(long companyID, String stockName) {
		setCompanyID(companyID);
		setStockName(stockName);
	}

	public long getCompanyID() {
		return companyID;
	}

	public void setCompanyID(long companyID) {
		this.companyID = companyID;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

}
