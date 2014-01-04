package de.ebus.emarket.server.daos;

import de.ebus.emarket.api.IStockItemDAO;
import de.ebus.emarket.persistence.entities.AEntity;
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
}
