package de.ebus.emarket.camel.processor;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.jpa.JpaComponent;
import org.apache.log4j.Logger;

import de.ebus.emarket.camel.entities.StockItem;

public class StockIDAdder implements Processor {

	static Logger log = Logger.getLogger(CompanyIDAdder.class.getName());

	@Override
	public void process(Exchange exchange) throws Exception {
		final StockItem stockitem = exchange.getIn().getBody(StockItem.class);

		final JpaComponent jpaComp = (JpaComponent) exchange.getContext().getRegistry().lookup("jpaStock");
		final EntityManager em = jpaComp.getEntityManagerFactory().createEntityManager();
		final Query q = em.createQuery("SELECT c.id FROM Stock c WHERE c.stockName = '" + stockitem.getStockName() + "'");
		final long id = (long) q.getSingleResult();

		stockitem.setStock_id(id);

		exchange.getIn().setBody(stockitem);
	}
}
