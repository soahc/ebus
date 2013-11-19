package de.ebus.emarket.api;

public interface IDAOProvider {
	public ISystemUserDAO getSystemUserDAO();
	public IProductDAO getProductDAO();
	public ICompanyDAO getCompanyDAO();
}
