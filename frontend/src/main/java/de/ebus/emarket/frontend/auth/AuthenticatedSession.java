package de.ebus.emarket.frontend.auth;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import de.ebus.emarket.api.IDAOProvider;
import de.ebus.emarket.persistence.entities.SystemUser;

public class AuthenticatedSession extends AuthenticatedWebSession {

	private static final long serialVersionUID = -4906177659736961601L;

	public IDAOProvider getDAOProviderService()
	{
	    // get bundle instance via the OSGi Framework Util class
	    BundleContext ctx = FrameworkUtil.getBundle(AuthenticatedSession.class).getBundleContext();
	    ServiceReference serviceReference = ctx.getServiceReference(IDAOProvider.class.getName());
	    return IDAOProvider.class.cast(ctx.getService(serviceReference));
	}
	
	SystemUser currentUser;
	
	public AuthenticatedSession(Request request) {
		super(request);
	}

	@Override
	public boolean authenticate(String username, String password) {
		SystemUser user = getDAOProviderService().getSystemUserDAO().readSystemUser(username, password); 
		
		if (user==null){
			return false;
		}
		
		currentUser = user;
		signIn(true);
		return true;
	}
	
	@Override
	public void signOut(){
		super.signOut();
		currentUser = null;
	}
	
	public SystemUser getCurrentUser() {
		return currentUser;
	}

	@Override
	public Roles getRoles() {
		return null;
	}
}
