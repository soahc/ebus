package de.ebus.emarket.frontend.pages.panel;

import org.apache.wicket.markup.html.panel.Panel;

import de.ebus.emarket.frontend.auth.AuthenticatedSession;

public abstract class ExtendedPanel extends Panel {
	
	private static final long serialVersionUID = 1L;

	public ExtendedPanel(String id) {
		super(id);

	}
	
	protected AuthenticatedSession getAuthenticatedSession(){
		return (AuthenticatedSession) getSession();
	}

}
