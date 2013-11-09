package de.ebus.emarket.server;

import de.ebus.emarket.api.IDAOProvider;
import de.ebus.emarket.api.ISystemUserDAO;

public class DAOProvider implements IDAOProvider {

	private ISystemUserDAO systemUserDAO;
	
	public void setSystemUserDAO(ISystemUserDAO systemUserDAO) {
		this.systemUserDAO = systemUserDAO;
	}
	
	@Override
	public ISystemUserDAO getSystemUserDAO() {
		return systemUserDAO;
	}

	

}
