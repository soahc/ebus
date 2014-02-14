package de.ebus.emarket.server;

import de.ebus.emarket.api.ICompanyDAO;
import de.ebus.emarket.api.IDAOProvider;
import de.ebus.emarket.api.IProductDAO;
import de.ebus.emarket.api.IStockDAO;
import de.ebus.emarket.api.IStockItemDAO;
import de.ebus.emarket.api.ISystemUserDAO;

public class DAOProvider implements IDAOProvider {

	private ISystemUserDAO systemUserDAO;
	private IProductDAO productDAO;
	private ICompanyDAO companyDAO;
	private IStockDAO stockDAO;
	private IStockItemDAO stockItemDAO;

	public void setSystemUserDAO(ISystemUserDAO systemUserDAO) {
		this.systemUserDAO = systemUserDAO;
	}

	@Override
	public ISystemUserDAO getSystemUserDAO() {
		return systemUserDAO;
	}

	@Override
	public IProductDAO getProductDAO() {
		return productDAO;
	}

	public void setProductDAO(IProductDAO productDAO) {
		this.productDAO = productDAO;
	}

	@Override
	public ICompanyDAO getCompanyDAO() {
		return companyDAO;
	}

	public void setCompanyDAO(ICompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}

	@Override
	public IStockDAO getStockDAO() {
		return stockDAO;
	}

	public void setStockDAO(IStockDAO stockDAO) {
		this.stockDAO = stockDAO;
	}

	@Override
	public IStockItemDAO getStockItemDAO() {
		return stockItemDAO;
	}

	public void setStockItemDAO(IStockItemDAO stockItemDAO) {
		this.stockItemDAO = stockItemDAO;
	}
}
