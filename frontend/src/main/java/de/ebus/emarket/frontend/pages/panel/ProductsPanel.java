package de.ebus.emarket.frontend.pages.panel;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.util.collections.MostRecentlyUsedMap;
import org.apache.wicket.util.file.File;
import org.ops4j.pax.wicket.api.PaxWicketBean;

import de.ebus.emarket.api.ICompanyDAO;
import de.ebus.emarket.api.IDAOProvider;
import de.ebus.emarket.api.IProductDAO;
import de.ebus.emarket.api.IStockDAO;
import de.ebus.emarket.api.IStockItemDAO;
import de.ebus.emarket.api.ISystemUserDAO;
import de.ebus.emarket.frontend.ServiceProvider;
import de.ebus.emarket.frontend.pages.HomePage;
import de.ebus.emarket.frontend.pages.panel.Image.ReadImageModel;
import de.ebus.emarket.persistence.entities.Company;
import de.ebus.emarket.persistence.entities.Product;
import de.ebus.emarket.persistence.entities.Stock;
import de.ebus.emarket.persistence.entities.StockItem;
import de.ebus.emarket.persistence.entities.SystemUser;

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
			final Product product = listItem.getModelObject();
			
			listItem.add(new Label("productSerial", product.getSerialNumber()));
			listItem.add(new Label("productName", product.getName()));
			listItem.add(new Label("productPrice", product.getPrice()));
			listItem.add(new StockLink("stockLink"));
			listItem.add(new NonCachingImage("productImage", new ReadImageModel(product)));
			
			listItem.add(new ProductRemove("productRemove", new Model<Product>(product), listItem));
		}
	}
	
	private class StockLink extends AjaxLink<Void>{
		
		private static final long serialVersionUID = 6624153382128391067L;

		public StockLink(String id) {
			super(id);
		}

		@Override
		public void onClick(final AjaxRequestTarget target) {
			System.out.println("ITEM: ");		
		}
	};
	
	private class ProductRemove extends Link<Product> {
		
		ListItem<Product> listItem;
		
		public ProductRemove(String id, IModel<Product> product, ListItem<Product> listItem) {
			super(id, product);
			this.listItem = listItem;
		}

		private static final long serialVersionUID = 1L;

		@Override
		public void onClick() {
			Product product = getModelObject();
			System.out.println("==> Remove: " + product.getId());
			IDAOProvider daoProvider = serviceProvider.getDaoProvider();
			IProductDAO productDAO = daoProvider.getProductDAO();
			IStockItemDAO stockItemDAO = daoProvider.getStockItemDAO();
			
			stockItemDAO.deleteAllBySerialNumber(product.getSerialNumber());

			productDAO.delete(product);
			setResponsePage(HomePage.class);
		}
		
	}
}
