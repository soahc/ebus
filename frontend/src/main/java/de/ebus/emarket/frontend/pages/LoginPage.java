package de.ebus.emarket.frontend.pages;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import de.ebus.emarket.persistence.entities.SystemUser;

public class LoginPage extends ExtendedWebPage {

    private static final long serialVersionUID = 1L;
        
    public LoginPage() {
    	add(new FeedbackPanel("feedback"));
    	add(new SignInForm("signInForm"));
    	
    	
    }
    
    private class SignInForm extends Form<Void> {
    	private static final long serialVersionUID = 1L;
    	private SystemUser user=null;
    	
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
    		if (LoginPage.this.getAuthenticatedSession().authenticate(user.getUsername(), user.getPassword())) {
    			setResponsePage(new HomePage());				
			} else {
				error("Wrong login!");
			}
    	}
    }
}