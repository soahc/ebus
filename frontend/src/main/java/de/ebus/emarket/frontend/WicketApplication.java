package de.ebus.emarket.frontend;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;

import de.ebus.emarket.frontend.auth.AuthenticatedSession;
import de.ebus.emarket.frontend.auth.AuthorizationStrategy;
import de.ebus.emarket.frontend.pages.HomePage;

public class WicketApplication extends WebApplication {
	public WicketApplication() {
	}

	@Override
	public Class<? extends WebPage> getHomePage() {
		return HomePage.class;
	}

	@Override
	public Session newSession(Request request, Response response) {
		return new AuthenticatedSession(request);
	}

	@Override
	protected void init() {
		super.init();
		getSecuritySettings().setAuthorizationStrategy(new AuthorizationStrategy());
	}
}