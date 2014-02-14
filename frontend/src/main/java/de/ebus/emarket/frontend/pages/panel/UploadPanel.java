package de.ebus.emarket.frontend.pages.panel;

import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.ops4j.pax.wicket.api.PaxWicketBean;

import de.ebus.emarket.api.ICompanyDAO;
import de.ebus.emarket.frontend.ServiceProvider;
import de.ebus.emarket.frontend.pages.components.FileUploadForm;

public class UploadPanel extends ExtendedPanel {

	private static final long serialVersionUID = 1L;

	@PaxWicketBean(name = "serviceProviderBean")
	private ServiceProvider serviceProvider;

	public UploadPanel(String id) {
		super(id);

		final ICompanyDAO companyDAO = serviceProvider.getDaoProvider().getCompanyDAO();
		add(new FeedbackPanel("feedback"));

		final String companyID = String.valueOf(companyDAO.readCompanyFromUser(getAuthenticatedSession().getCurrentUser()).getId());
		add(new FileUploadForm("form", companyID));
	}
}
