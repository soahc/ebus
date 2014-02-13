package de.ebus.emarket.camel.processor;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.jpa.JpaComponent;
import org.apache.log4j.Logger;

public class StockIDAdder  implements Processor {
	
	static Logger log = Logger.getLogger(CompanyIDAdder.class.getName());
	
	
	public void process(Exchange exchange) throws Exception {
		
		JpaComponent jpaComp = (JpaComponent) exchange.getContext().getRegistry().lookup("jpaStock");
		EntityManager em = jpaComp.getEntityManagerFactory().createEntityManager();
		Query q = em.createQuery("SELECT c.companyID FROM Stock c WHERE c.stockName = 'Stock 1'");
		
		
		
		log.warn("JPA: " +q.getSingleResult());
		
		
			
	}
}
