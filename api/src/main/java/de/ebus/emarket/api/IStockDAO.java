package de.ebus.emarket.api;

import java.util.List;

import de.ebus.emarket.persistence.entities.Stock;

public interface IStockDAO extends IDAO {
	public Stock readStock(final long id);

	public List<Stock> readAllWithCompanyID(final long companyID);
}
