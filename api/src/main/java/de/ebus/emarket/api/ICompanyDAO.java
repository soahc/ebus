package de.ebus.emarket.api;

import de.ebus.emarket.persistence.entities.Company;

public interface ICompanyDAO extends IDAO {
	Company readCompany(final long id);
}
