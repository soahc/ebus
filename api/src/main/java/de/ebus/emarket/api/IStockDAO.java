package de.ebus.emarket.api;

import de.ebus.emarket.persistence.entities.Stock;

public interface IStockDAO extends IDAO {
	Stock readStock(final long id);
}
