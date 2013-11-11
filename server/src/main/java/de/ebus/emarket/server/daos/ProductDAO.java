package de.ebus.emarket.server.daos;

import de.ebus.emarket.api.IProductDAO;
import de.ebus.emarket.persistence.entities.AEntity;
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
}
