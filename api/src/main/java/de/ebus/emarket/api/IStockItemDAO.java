package de.ebus.emarket.api;

import de.ebus.emarket.persistence.entities.StockItem;

public interface IStockItemDAO extends IDAO {
	StockItem readStockItem(final long id);
}
