package de.ebus.emarket.server.daos;

import java.util.List;

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

	@Override
	public List<Stock> readAllWithCompanyID(final long companyID) {
		return readByJPQL("SELECT c FROM Stock c WHERE c.companyID = " + companyID);
	}
}
