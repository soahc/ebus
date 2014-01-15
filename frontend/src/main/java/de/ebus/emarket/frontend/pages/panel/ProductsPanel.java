package de.ebus.emarket.frontend.pages.panel;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.ops4j.pax.wicket.api.PaxWicketBean;

import de.ebus.emarket.api.IDAOProvider;
import de.ebus.emarket.frontend.ServiceProvider;
import de.ebus.emarket.persistence.entities.Company;
import de.ebus.emarket.persistence.entities.Product;

public class ProductsPanel extends ExtendedPanel {

	@PaxWicketBean(name = "serviceProviderBean")
	private ServiceProvider serviceProvider;

	private static final long serialVersionUID = -8278614129608604062L;

	public ProductsPanel(String id) {
		super(id);
		IDAOProvider daoProvider = serviceProvider.getDaoProvider();
		Company company = daoProvider.getCompanyDAO().readCompanyFromUser(getAuthenticatedSession().getCurrentUser());
		List<Product> products = serviceProvider.getDaoProvider().getProductDAO().readAllFromCompany(company);
		add(new ProductListView("products", products));
	}

	private class ProductListView extends ListView<Product> {

		private static final long serialVersionUID = 405269496693447691L;

		public ProductListView(String id, List<? extends Product> list) {
			super(id, list);
		}

		@Override
		protected void populateItem(ListItem<Product> listItem) {
			Product product = listItem.getModelObject();
			listItem.add(new Label("productSerial", product.getSerialNumber()));
			listItem.add(new Label("productName", product.getName()));
			listItem.add(new Label("productPrice", product.getPrice()));

			Label imagelbl = new Label(
					"productImage",
					"<img src=\""
							+ "http://www.infendo.com/wp-content/uploads/2008/09/3951-hdd.jpg"
							+ "\" width=\"134\" height=\"134\" />");
			imagelbl.setEscapeModelStrings(false);
			listItem.add(imagelbl);
			listItem.add(new StockLink("stockLink"));
			
		}
	}
	
	private class StockLink extends AjaxLink<Void>{
		
		public StockLink(String id) {
			super(id);
		}

		@Override
		public void onClick(final AjaxRequestTarget target) {
			System.out.println("ITEM: ");		
		}
	};
}
