package de.ebus.emarket.api;

import java.util.List;

import de.ebus.emarket.persistence.entities.Stock;
import de.ebus.emarket.persistence.entities.StockItem;

public interface IStockItemDAO extends IDAO {
	StockItem create(StockItem item, int units);
	StockItem readStockItem(final long id);
	List<StockItem> readAllFromStock(final Stock stock);
	StockItem addToStock(long id, int units);
	StockItem removeFromStock(long id, int units);
}
