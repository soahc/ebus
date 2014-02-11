package de.ebus.emarket.frontend.pages.panel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.ContextRelativeResource;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.util.io.ByteArrayOutputStream;
import org.apache.wicket.util.string.StringValue;
import org.ops4j.pax.wicket.api.PaxWicketBean;

import de.ebus.emarket.api.IDAOProvider;
import de.ebus.emarket.frontend.ServiceProvider;
import de.ebus.emarket.frontend.pages.panel.Image.ReadImageModel;
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
			final Product product = listItem.getModelObject();
			listItem.add(new Label("productSerial", product.getSerialNumber()));
			listItem.add(new Label("productName", product.getName()));
			listItem.add(new Label("productPrice", product.getPrice()));

			listItem.add(new StockLink("stockLink"));
			
			listItem.add(new NonCachingImage("productImage", new ReadImageModel(product)));
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
