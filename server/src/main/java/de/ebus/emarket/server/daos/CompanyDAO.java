package de.ebus.emarket.server.daos;

import de.ebus.emarket.api.ICompanyDAO;
import de.ebus.emarket.persistence.entities.AEntity;
import de.ebus.emarket.persistence.entities.Company;

public class CompanyDAO extends DAO implements ICompanyDAO {

	@Override
	public Class<? extends AEntity> getEntityClass() {
		return Company.class;
	}
	
	@Override
	public Company readCompany(long id) {
		return getEntityManager().find(Company.class, id);
	}
}
