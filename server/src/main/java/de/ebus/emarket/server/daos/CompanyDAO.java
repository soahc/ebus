package de.ebus.emarket.server.daos;

import de.ebus.emarket.api.ICompanyDAO;
import de.ebus.emarket.persistence.entities.AEntity;
import de.ebus.emarket.persistence.entities.Company;
import de.ebus.emarket.persistence.entities.SystemUser;

public class CompanyDAO extends DAO implements ICompanyDAO {

	@Override
	public Class<? extends AEntity> getEntityClass() {
		return Company.class;
	}

	@Override
	public Company readCompany(long id) {
		return getEntityManager().find(Company.class, id);
	}

	@Override
	public Company readCompanyFromUser(SystemUser user) {
		return (Company) readByJPQL("SELECT u.company FROM SystemUser u where u.id=" + user.getId()).get(0);
	}
}
