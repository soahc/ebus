package de.ebus.emarket.api;

import de.ebus.emarket.persistence.entities.Company;
import de.ebus.emarket.persistence.entities.SystemUser;

public interface ICompanyDAO extends IDAO {
	Company readCompany(final long id);
	Company readCompanyFromUser(final SystemUser user);
}
