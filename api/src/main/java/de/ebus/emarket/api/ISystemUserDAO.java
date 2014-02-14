package de.ebus.emarket.api;

import de.ebus.emarket.persistence.entities.SystemUser;

public interface ISystemUserDAO extends IDAO {
	public SystemUser readSystemUser(String username, String password);

	public SystemUser readSystemUser(final long id);
}
