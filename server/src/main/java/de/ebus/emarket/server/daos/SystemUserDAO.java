package de.ebus.emarket.server.daos;

import de.ebus.emarket.api.ISystemUserDAO;
import de.ebus.emarket.persistence.entities.SystemUser;

public class SystemUserDAO extends DAO implements ISystemUserDAO {

	@Override
	public SystemUser getSystemUser(String username, String password) {
		return new SystemUser();
	}

}
