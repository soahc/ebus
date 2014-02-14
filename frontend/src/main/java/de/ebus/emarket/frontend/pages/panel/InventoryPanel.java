package de.ebus.emarket.frontend.pages.panel;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;
import org.ops4j.pax.wicket.api.PaxWicketBean;

import de.ebus.emarket.api.IDAOProvider;
import de.ebus.emarket.frontend.ServiceProvider;
import de.ebus.emarket.frontend.pages.panel.Image.ReadImageModel;
import de.ebus.emarket.persistence.entities.Product;
import de.ebus.emarket.persistence.entities.Stock;
import de.ebus.emarket.persistence.entities.StockItem;

public class InventoryPanel extends ExtendedPanel {

	@PaxWicketBean(name = "serviceProviderBean")
	private ServiceProvider serviceProvider;

	private static final long serialVersionUID = -8278614129608604062L;

	public InventoryPanel(String id) {
		super(id);
		final IDAOProvider daoProvider = serviceProvider.getDaoProvider();
		final long companyID = daoProvider.getCompanyDAO().readCompanyFromUser(getAuthenticatedSession().getCurrentUser()).getId();
		final List<Stock> stocks = daoProvider.getStockDAO().readAllWithCompanyID(companyID);
		add(new StockListView("products", stocks));
	}

	private class StockListView extends ListView<Stock> {

		private static final long serialVersionUID = 405269496693447691L;

		public StockListView(String id, List<? extends Stock> list) {
			super(id, list);
		}

		@Override
		protected void populateItem(ListItem<Stock> listItem) {
			final Stock stock = listItem.getModelObject();
			listItem.add(new Label("stockName", stock.getStockName()));

			final List<StockItem> items = serviceProvider.getDaoProvider().getStockItemDAO().readAllFromStock(stock.getId());
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
			final StockItem stockItem = listItem.getModelObject();

			final Stock stock = serviceProvider.getDaoProvider().getStockDAO().readStock(stockItem.getStock_id());
			final Product product = serviceProvider.getDaoProvider().getProductDAO()
					.readProductBySerialnumber(stockItem.getProductSerialNumber(), stock.getCompanyID());

			listItem.add(new NonCachingImage("stockitemImage", new ReadImageModel(product)));
			listItem.add(new Label("stockItemSerial", stockItem.getProductSerialNumber()));
			// listItem.add(new Label("stockItemUnits", stockItem.getCount()));

			listItem.add(new UnitForm("units_edit", stockItem));

		}
	}

	private class UnitForm extends Form<Void> {

		private static final long serialVersionUID = -7985624275419858938L;

		private final UnitChange value;

		public UnitForm(String id, StockItem item) {
			super(id);

			value = new UnitChange();
			value.setValue(0);
			value.setId(item.getId());

			this.add(new Label("stockItemUnits", item.getCount()));
			this.add(new NumberTextField<Integer>("stockItemUnitChange", new PropertyModel<Integer>(value, "value")));
		}

		@Override
		public void onSubmit() {
			System.out.println("==>Change units");
			final IDAOProvider provider = serviceProvider.getDaoProvider();
			if (value.getValue() > 0) {
				provider.getStockItemDAO().addToStock(value.getId(), value.getValue());
			} else {
				provider.getStockItemDAO().removeFromStock(value.getId(), -value.getValue());
			}
			System.out.println("==> " + value.getId() + " : " + value.getValue());
		}

		private class UnitChange {
			private Integer value;
			private long id;

			public Integer getValue() {
				return value;
			}

			public void setValue(Integer value) {
				this.value = value;
			}

			public long getId() {
				return id;
			}

			public void setId(long id) {
				this.id = id;
			}
		}
	}
}
