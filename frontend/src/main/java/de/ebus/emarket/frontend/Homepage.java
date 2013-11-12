package de.ebus.emarket.frontend;


import java.io.File;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.util.lang.Bytes;
import org.ops4j.pax.wicket.api.PaxWicketBean;

import de.ebus.emarket.api.ISystemUserDAO;
import de.ebus.emarket.frontend.fileupload.FileUploadForm;
import de.ebus.emarket.persistence.entities.SystemUser;

/**
 * Very simple page providing entry points into various other examples.
 */



public class Homepage extends WebPage {
    private static final long serialVersionUID = 1L;
 
    @PaxWicketBean(name = "serviceProviderBean")
    private ServiceProvider serviceProvider;
   
	public Homepage() {	
		 
		add(new FeedbackPanel("feedback"));		
		add(new FileUploadForm("form"));
		
        //add(new Label("oneComponent", "Ene mene muh!!! " + user.getId() + " " + user.getUsername()));
        add(new Link("logout") {
			@Override
			public void onClick() {
				setResponsePage(SignIn.class);
			}     	
        });
    }
}