package de.ebus.emarket.frontend.pages.panel;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.ops4j.pax.wicket.api.PaxWicketBean;

import de.ebus.emarket.api.IDAOProvider;
import de.ebus.emarket.frontend.ServiceProvider;
import de.ebus.emarket.frontend.pages.panel.Image.ReadImageModel;
import de.ebus.emarket.persistence.entities.Product;
import de.ebus.emarket.persistence.entities.Stock;
import de.ebus.emarket.persistence.entities.StockItem;

public class InventoryPanel extends ExtendedPanel{

	@PaxWicketBean(name = "serviceProviderBean")
	private ServiceProvider serviceProvider;

	private static final long serialVersionUID = -8278614129608604062L;

	public InventoryPanel(String id) {
		super(id);
		IDAOProvider daoProvider = serviceProvider.getDaoProvider();
		long companyID = daoProvider.getCompanyDAO().readCompanyFromUser(getAuthenticatedSession().getCurrentUser()).getId();
		List<Stock> stocks = daoProvider.getStockDAO().readAllWithCompanyID(companyID);
		add(new StockListView("products", stocks));
	}

	private class StockListView extends ListView<Stock> {

		private static final long serialVersionUID = 405269496693447691L;

		public StockListView(String id, List<? extends Stock> list) {
			super(id, list);
		}

		@Override
		protected void populateItem(ListItem<Stock> listItem) {
			Stock stock = listItem.getModelObject();
			listItem.add(new Label("stockName", stock.getStockName()));
			
			List<StockItem> items = serviceProvider.getDaoProvider().getStockItemDAO().readAllFromStock(stock.getId());
			listItem.add(new StockItemListView("stockitems", items));
		}
	}
	
	private class StockItemListView extends ListView<StockItem> {

		private static final long serialVersionUID = 405269496693447691L;

		public StockItemListView(String id, List<? extends StockItem> list) {
			super(id, list);
		}

		@Override
		protected void populateItem(ListItem<StockItem> listItem) {
			StockItem stockItem = listItem.getModelObject();
			listItem.add(new Label("stockItemSerial", stockItem.getProductSerialNumber()));
			listItem.add(new Label("stockItemUnits", stockItem.getCount()));
			
			Stock stock = serviceProvider.getDaoProvider().getStockDAO().readStock(stockItem.getStock_id());
			Product product = serviceProvider.getDaoProvider().getProductDAO().readProductBySerialnumber(stockItem.getProductSerialNumber(), stock.getCompanyID());

			listItem.add(new NonCachingImage("stockitemImage", new ReadImageModel(product)));
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
