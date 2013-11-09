package de.ebus.emarket.server.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.ebus.emarket.api.ISystemUserDAO;
import de.ebus.emarket.persistence.entities.SystemUser;

public class SystemUserDAO extends DAO implements ISystemUserDAO {

	@Override
	public SystemUser getSystemUser(String username, String password) {
		final EntityManager em = getEntityManager();
		final Query q = em.createQuery("select a from SystemUser a where a.username = '"+username+"' and a.password = '"+password+"'");
		@SuppressWarnings("unchecked")
		final List<SystemUser> list = q.getResultList();
		if (list.isEmpty()){
			return null;
		}else{
			return list.get(0);
		}
	}

}
