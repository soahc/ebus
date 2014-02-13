package de.ebus.emarket.api;

public interface IDAOProvider {
	public ISystemUserDAO getSystemUserDAO();

	public IProductDAO getProductDAO();

	public ICompanyDAO getCompanyDAO();

	public IStockDAO getStockDAO();

	public IStockItemDAO getStockItemDAO();
}
