package de.ebus.emarket.server.daos;

import de.ebus.emarket.api.IStockDAO;
import de.ebus.emarket.persistence.entities.AEntity;
import de.ebus.emarket.persistence.entities.Stock;

public class StockDAO extends DAO implements IStockDAO {

	@Override
	public Class<? extends AEntity> getEntityClass() {
		return Stock.class;
	}

	@Override
	public Stock readStock(long id) {
		return getEntityManager().find(Stock.class, id);
	}
}
