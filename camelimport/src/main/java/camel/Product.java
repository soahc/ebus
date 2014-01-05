package camel;

import java.math.BigDecimal;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@CsvRecord(separator = "\\|", skipFirstLine=true)
public class Product {
	
	@DataField(pos = 1, required=true)
	private String serialNumber;
	@DataField(pos = 2, required=true)
	private String name;
	@DataField(pos = 3, precision = 2, required=true)
	private BigDecimal price;
	
	public String toString(){
		return serialNumber + ", "+name+", "+ price;
	}
	
	
}
