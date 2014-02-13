package de.ebus.emarket.server.daos;

import java.util.List;

import de.ebus.emarket.api.IProductDAO;
import de.ebus.emarket.persistence.entities.AEntity;
import de.ebus.emarket.persistence.entities.Company;
import de.ebus.emarket.persistence.entities.Product;

public class ProductDAO extends DAO implements IProductDAO {

	@Override
	public Class<? extends AEntity> getEntityClass() {
		return Product.class;
	}

	@Override
	public Product readProduct(long id) {
		return getEntityManager().find(Product.class, id);
	}

	@Override
	public List<Product> readAllFromCompany(Company company) {
		return readByJPQL("SELECT p FROM Product p WHERE p.company.id = " + company.getId());
	}

	@Override
	public Product readProductBySerialnumber(String sn, long comanyID) {
		final List<Product> products = readByJPQL("SELECT p FROM Product p WHERE p.serialNumber = '" + sn + "' AND p.company.id = " + comanyID);
		if (products.isEmpty()) {
			return null;
		}
		return products.get(0);
	}
}
