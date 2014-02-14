package de.ebus.emarket.frontend.auth;

import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.request.component.IRequestableComponent;

import de.ebus.emarket.frontend.pages.LoginPage;

public class AuthorizationStrategy implements IAuthorizationStrategy {

	@Override
	public boolean isActionAuthorized(Component arg0, Action arg1) {
		return true;
	}

	@Override
	public <T extends IRequestableComponent> boolean isInstantiationAuthorized(Class<T> componentClass) {
		if (AuthenticatedWebPage.class.isAssignableFrom(componentClass)) {
			if (((AuthenticatedSession) Session.get()).isSignedIn()) {
				return true;
			}
			throw new RestartResponseAtInterceptPageException(LoginPage.class);
		}
		return true;
	}

}
