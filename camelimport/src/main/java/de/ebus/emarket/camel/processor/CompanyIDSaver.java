package de.ebus.emarket.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;

public class CompanyIDSaver implements Processor {

	static Logger log = Logger.getLogger(CompanyIDAdder.class.getName());

	@Override
	public void process(Exchange exchange) throws Exception {
		final String fileName = exchange.getProperty("zipFileName").toString();
		final String cid = fileName.substring(0, fileName.indexOf('_'));
		exchange.setProperty("companyId", cid);
	}
}
