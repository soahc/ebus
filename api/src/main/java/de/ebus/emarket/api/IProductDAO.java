package de.ebus.emarket.api;

import de.ebus.emarket.persistence.entities.Product;

public interface IProductDAO extends IDAO {
	Product readProduct(final long id);
}
