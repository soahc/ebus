package de.ebus.emarket.api;

import java.util.List;

import de.ebus.emarket.persistence.entities.Company;
import de.ebus.emarket.persistence.entities.Product;

public interface IProductDAO extends IDAO {
	Product readProduct(final long id);
	List<Product> readAllFromCompany(Company company);
	Product readProductBySerialnumber(final String sn, long comanyID);
}
