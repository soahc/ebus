package de.ebus.emarket.api;

import de.ebus.emarket.persistence.entities.SystemUser;

public interface ISystemUserDAO extends IDAO {
	
	public SystemUser getSystemUser(String username, String password);
	
}
