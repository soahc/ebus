package de.ebus.emarket.server.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.ebus.emarket.api.ISystemUserDAO;
import de.ebus.emarket.persistence.entities.AEntity;
import de.ebus.emarket.persistence.entities.SystemUser;

public class SystemUserDAO extends DAO implements ISystemUserDAO {

	@Override
	public Class<? extends AEntity> getEntityClass() {
		return SystemUser.class;
	}
	
	@Override
	public SystemUser readSystemUser(long id) {
		return getEntityManager().find(SystemUser.class, id);
	}
	
	@Override
	public SystemUser readSystemUser(String username, String password) {
		Object obj = readSingleResultByJPQL("select a from SystemUser a where a.username = '"+username+"' and a.password = '"+password+"'");
		return (obj==null)?null:(SystemUser)obj;
	}

	
}
