package de.ebus.emarket.frontend.pages;

import org.apache.wicket.markup.html.WebPage;

import de.ebus.emarket.frontend.auth.AuthenticatedSession;

public class ExtendedWebPage extends WebPage {

	private static final long serialVersionUID = -1049612477125256506L;

	protected AuthenticatedSession getAuthenticatedSession() {
		return (AuthenticatedSession) getSession();
	}

}
