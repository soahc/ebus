package de.ebus.emarket.server.daos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.ebus.emarket.api.IStockItemDAO;
import de.ebus.emarket.persistence.entities.AEntity;
import de.ebus.emarket.persistence.entities.Product;
import de.ebus.emarket.persistence.entities.Stock;
import de.ebus.emarket.persistence.entities.StockItem;

public class StockItemDAO extends DAO implements IStockItemDAO {

	@Override
	public Class<? extends AEntity> getEntityClass() {
		return StockItem.class;
	}

	@Override
	public StockItem readStockItem(long id) {
		return getEntityManager().find(StockItem.class, id);
	}

	@Override
	public StockItem create(StockItem item, int units) {
		item.setCount(units);
		getEntityManager().persist(item);
		return item;
	}

	@Override
	public StockItem addToStock(long id, int units) {
		StockItem item = getEntityManager().find(StockItem.class, id);
		item.setCount(item.getCount() + units);
		return item;
	}

	@Override
	public StockItem removeFromStock(long id, int units) {
		StockItem item = getEntityManager().find(StockItem.class, id);
		item.setCount(item.getCount() - units);
		return item;
	}

	@Override
	public List<StockItem> readAllFromStock(final long id) {
		return readByJPQL("SELECT c FROM StockItem c WHERE c.stock_id = " + id);
	}

	@Override
	public void deleteAllBySerialNumber(String sn) {
		List<StockItem> stockItems = readByJPQL("SELECT s FROM StockItem s where s.productSerialNumber = '" + sn + "'");
		for (StockItem item : stockItems) {			
			delete(item);
		}
	}

}
