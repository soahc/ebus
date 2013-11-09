package de.ebus.emarket.frontend;

import de.ebus.emarket.api.IDAOProvider;
import de.ebus.emarket.persistence.entities.SystemUser;

public class ServiceProvider {
	
	private IDAOProvider daoProvider;
	
	public SystemUser checkUser(String username, String password){
		return daoProvider.getSystemUserDAO().getSystemUser(username, password);
	}
	
	public IDAOProvider getDaoProvider() {
		return daoProvider;
	}

	public void setDaoProvider(IDAOProvider daoProvider) {
		this.daoProvider = daoProvider;
	}
	
	// --------------------------------
	
}
