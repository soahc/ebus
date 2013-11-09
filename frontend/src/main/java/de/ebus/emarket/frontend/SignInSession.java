package de.ebus.emarket.frontend;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

import de.ebus.emarket.persistence.entities.SystemUser;

public class SignInSession extends AuthenticatedWebSession {
	private static final long serialVersionUID = 1L;

	SystemUser user;
	
	public SignInSession(Request request) {
		super(request);
	}

	@Override
	public boolean authenticate(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Roles getRoles() {
		// TODO Auto-generated method stub
		return null;
	}

}
