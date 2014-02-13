package de.ebus.emarket.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;

import de.ebus.emarket.camel.products.Product;

public class CompanyIDAdder implements Processor {

	static Logger log = Logger.getLogger(CompanyIDAdder.class.getName());

	@Override
	public void process(Exchange exchange) throws Exception {
		final Product product = exchange.getIn().getBody(Product.class);
		final String fileName = exchange.getProperty("csvFileName").toString();
		final long companyId = Long.parseLong(fileName.substring(0, fileName.indexOf('_')));
		product.setCompany_id(companyId);
		product.setImagePath(companyId + getFileSeparate() + product.getImagePath());
		exchange.getIn().setBody(product);
	}

	private String getFileSeparate() {
		final String OS = System.getProperty("os.name").toLowerCase();
		if ((OS.indexOf("win") >= 0)) {
			return "\\";
		} else {
			return "/";
		}
	}

}
