package de.ebus.emarket.frontend;


import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.ops4j.pax.wicket.api.PaxWicketBean;


import de.ebus.emarket.api.ISystemUserDAO;
import de.ebus.emarket.persistence.entities.SystemUser;

/**
 * Very simple page providing entry points into various other examples.
 */
public class Homepage extends WebPage {

    private static final long serialVersionUID = 1L;
 
    @PaxWicketBean(name = "serviceProviderBean")
    private ServiceProvider serviceProvider;
    
	public Homepage(SystemUser user) {
		ISystemUserDAO dao = serviceProvider.getDaoProvider().getSystemUserDAO();
		
		user = dao.create(user);
		
        add(new Label("oneComponent", "Welcome to the most simple pax-wicket application based on blueprint. Hallo " + user.getId() + " " + user.getUsername()));
        add(new Link("logout") {
			@Override
			public void onClick() {
				setResponsePage(SignIn.class);
			}
        	
        });
    }
}