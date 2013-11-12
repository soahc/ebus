package de.ebus.emarket.frontend;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.ops4j.pax.wicket.api.PaxWicketBean;

import de.ebus.emarket.persistence.entities.SystemUser;

/**
 * Very simple page providing entry points into various other examples.
 */
public class SignIn extends WebPage {

    private static final long serialVersionUID = 1L;
 
    @PaxWicketBean(name = "serviceProviderBean")
    private ServiceProvider serviceProvider;
    
    public SignIn() {
    	add(new FeedbackPanel("feedback"));
    	add(new SignInForm("signInForm"));
    }
    
    private class SignInForm extends Form<Void> {
    	private static final long serialVersionUID = 1L;

    	@PaxWicketBean(name = "serviceProviderBean")
        private ServiceProvider serviceProvider;
    	
    	private SystemUser user;
    	
    	public SignInForm(String id){
    		super(id);
    		user = new SystemUser();
    		user.setUsername("Test");
    		user.setPassword("test");
        	
    		add(new TextField<String>("username", new PropertyModel<String>(user, "username")));
        	add(new PasswordTextField("userpassword", new PropertyModel<String>(user, "password")));		
    	}
    	
    	@Override
    	protected void onSubmit() {
    		
    		//Hier wird jetzt ein User erwartet, der tatsächlich in der Datenbank steht!
    		SystemUser loggeruser = serviceProvider.checkUser(user.getUsername(), user.getPassword());
    		
    		if (loggeruser != null) {
    			setResponsePage(new Homepage());				
			} else {
				error("Wrong login!");
			}
    	}
    }
}