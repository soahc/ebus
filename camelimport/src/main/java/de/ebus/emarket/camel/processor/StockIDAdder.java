package de.ebus.emarket.camel.processor;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.jpa.JpaComponent;
import org.apache.log4j.Logger;

import de.ebus.emarket.camel.entities.Stock;
import de.ebus.emarket.camel.entities.StockItem;
import de.ebus.emarket.camel.products.Product;

public class StockIDAdder  implements Processor {
	
	static Logger log = Logger.getLogger(CompanyIDAdder.class.getName());
	
	public void process(Exchange exchange) throws Exception {
		StockItem stockitem = exchange.getIn().getBody(StockItem.class);
	
		JpaComponent jpaComp = (JpaComponent) exchange.getContext().getRegistry().lookup("jpaStock");
		EntityManager em = jpaComp.getEntityManagerFactory().createEntityManager();
		Query q = em.createQuery("SELECT c.id FROM Stock c WHERE c.stockName = '"+stockitem.getStockName()+"'");
		long id = (long) q.getSingleResult();
		
		stockitem.setStock_id(id);
		
		exchange.getIn().setBody(stockitem);	
	}
}
