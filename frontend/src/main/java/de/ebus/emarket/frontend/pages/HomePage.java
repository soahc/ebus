package de.ebus.emarket.frontend.pages;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.ops4j.pax.wicket.api.PaxWicketBean;

import de.ebus.emarket.api.ICompanyDAO;
import de.ebus.emarket.frontend.ServiceProvider;
import de.ebus.emarket.frontend.auth.AuthenticatedWebPage;
import de.ebus.emarket.frontend.pages.components.FileUploadForm;


/**
 * Very simple page providing entry points into various other examples.
 */

public class HomePage extends ExtendedWebPage implements AuthenticatedWebPage {
    private static final long serialVersionUID = 1L;
 
    @PaxWicketBean(name = "serviceProviderBean")
    private ServiceProvider serviceProvider;
   
	public HomePage() {	
		ICompanyDAO companyDAO = serviceProvider.getDaoProvider().getCompanyDAO();
		add(new FeedbackPanel("feedback"));	
		
		String companyID = String.valueOf(companyDAO.readCompanyFromUser(getAuthenticatedSession().getCurrentUser()).getId());
		add(new FileUploadForm("form", companyID));
		
		System.out.println();
		
        add(new Link("logout") {
			@Override
			public void onClick() {
				getAuthenticatedSession().signOut();
				setResponsePage(LoginPage.class);
			}     	
        });
    }
}