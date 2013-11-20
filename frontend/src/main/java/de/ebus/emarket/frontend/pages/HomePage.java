package de.ebus.emarket.frontend.pages;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.ops4j.pax.wicket.api.PaxWicketBean;

import de.ebus.emarket.api.ICompanyDAO;
import de.ebus.emarket.frontend.ServiceProvider;
import de.ebus.emarket.frontend.auth.AuthenticatedWebPage;
import de.ebus.emarket.frontend.pages.components.FileUploadForm;
import de.ebus.emarket.frontend.pages.panel.ProductsPanel;
import de.ebus.emarket.frontend.pages.panel.UploadPanel;


/**
 * Very simple page providing entry points into various other examples.
 */

public class HomePage extends ExtendedWebPage implements AuthenticatedWebPage {
    private static final long serialVersionUID = 1L;
 
	public HomePage() {	
		for (Navtype navtype : Navtype.values()){
			add(new NavigationLink(navtype.toString()+"_1"));
			add(new NavigationLink(navtype.toString()+"_2"));
		}
		
		add(new ProductsPanel("contentPanel"));
        add(new LogoutLink("logout"));
    }
	
	private class NavigationLink extends AjaxLink<Void>{

		private static final long serialVersionUID = 1693382000927491447L;

		public NavigationLink(String id) {super(id);}

		@Override
		public void onClick(AjaxRequestTarget target) {
			HomePage homepage = HomePage.this;
			Panel contentPanel = null;
			
			
			String componentId = getId();
			switch (Navtype.valueOf(componentId.substring(0, componentId.indexOf('_')))) {
			case productsNavLink:
				contentPanel = new ProductsPanel("contentPanel");
				break;
			case dbupdateNavLink:
				contentPanel = new UploadPanel("contentPanel");
				break;

			default:
				contentPanel = new ProductsPanel("contentPanel");
				break;
			}
			
			
			contentPanel.setOutputMarkupId(true);
			homepage.get("contentPanel").replaceWith(contentPanel);
			target.add(homepage);
		}
	}
	
	private class LogoutLink extends Link<Void>{
		
		private static final long serialVersionUID = 5644061728361308400L;

		public LogoutLink(String id) {super(id);}

		@Override
		public void onClick() {
			getAuthenticatedSession().signOut();
			setResponsePage(LoginPage.class);
		}     	
	}
	
	private enum Navtype {
		productsNavLink, dbupdateNavLink
	}
}