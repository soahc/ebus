package de.ebus.emarket.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;

import de.ebus.emarket.camel.products.Product;

public class CompanyIDSaver implements Processor {
	
	static Logger log = Logger.getLogger(CompanyIDAdder.class.getName());
	
	public void process(Exchange exchange) throws Exception {
		String fileName = exchange.getProperty("zipFileName").toString();
		String cid = fileName.substring(0, fileName.indexOf('_'));
		exchange.setProperty("companyId", cid);
	}
}

