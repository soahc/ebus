package de.ebus.emarket.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;

import de.ebus.emarket.camel.products.Product;

public class CompanyIDAdder implements Processor {
	
	static Logger log = Logger.getLogger(CompanyIDAdder.class.getName());
	
	public void process(Exchange exchange) throws Exception {
		Product product = exchange.getIn().getBody(Product.class);
		String fileName = exchange.getProperty("csvFileName").toString();
		long companyId = Long.parseLong(fileName.substring(0, fileName.indexOf('_')));
		product.setCompany_id(companyId);
		product.setImagePath(companyId + getFileSeparate() + product.getImagePath());
		exchange.getIn().setBody(product);
	}
	
	private String getFileSeparate() {
		String OS = System.getProperty("os.name").toLowerCase();
		if ((OS.indexOf("win") >= 0)) {
			return "\\";
		} else {
			return "/";
		}
	}

}
