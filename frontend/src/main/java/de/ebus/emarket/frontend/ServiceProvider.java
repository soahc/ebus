package de.ebus.emarket.frontend;

import de.ebus.emarket.api.IDAOProvider;

public class ServiceProvider {
	
	private IDAOProvider daoProvider;
	
	public IDAOProvider getDaoProvider() {
		return daoProvider;
	}

	public void setDaoProvider(IDAOProvider daoProvider) {
		this.daoProvider = daoProvider;
	}

	public int returnInt(){
		return 6;
	}
}
