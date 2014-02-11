package de.ebus.emarket.frontend.pages;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.apache.commons.logging.impl.ServletContextCleaner;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.util.file.File;
import org.ops4j.pax.wicket.api.PaxWicketBean;

import de.ebus.emarket.api.ICompanyDAO;
import de.ebus.emarket.api.IDAOProvider;
import de.ebus.emarket.api.IProductDAO;
import de.ebus.emarket.api.IStockDAO;
import de.ebus.emarket.api.IStockItemDAO;
import de.ebus.emarket.api.ISystemUserDAO;
import de.ebus.emarket.frontend.ServiceProvider;
import de.ebus.emarket.frontend.pages.panel.ProductsPanel;
import de.ebus.emarket.persistence.entities.Company;
import de.ebus.emarket.persistence.entities.Product;
import de.ebus.emarket.persistence.entities.Stock;
import de.ebus.emarket.persistence.entities.StockItem;
import de.ebus.emarket.persistence.entities.SystemUser;

public class LoginPage extends ExtendedWebPage {

	@PaxWicketBean(name = "serviceProviderBean")
    private ServiceProvider serviceProvider;
	
	private static final long serialVersionUID = 4327790718284443537L;
	
	public LoginPage() {
		add(new FeedbackPanel("feedback"));
		add(new SignInForm("signInForm"));
		add(new ResetDatabaseLink("resetDBLink"));

	}

	private class SignInForm extends Form<Void> {
		private static final long serialVersionUID = 1L;
		private SystemUser user = null;

		public SignInForm(String id) {
			super(id);
			user = new SystemUser();
			user.setUsername("Test");
			user.setPassword("test");

			add(new TextField<String>("username", new PropertyModel<String>(
					user, "username")));
			add(new PasswordTextField("userpassword",
					new PropertyModel<String>(user, "password")));
		}

		@Override
		protected void onSubmit() {
			if (LoginPage.this.getAuthenticatedSession().authenticate(user.getUsername(), user.getPassword())) {
				setResponsePage(new HomePage());
			} else {
				error("Wrong login!");
			}
		}
	}
	
	private class ResetDatabaseLink extends Link<Void> {
		
		public ResetDatabaseLink(String id) {
			super(id);
		}

		private static final long serialVersionUID = 1L;

		@Override
		public void onClick() {
			//hiden link for reseting the DataBase:
			
			IDAOProvider daoProvider = serviceProvider.getDaoProvider();
			IProductDAO productDAO = daoProvider.getProductDAO();
			ISystemUserDAO systemUserDAO = daoProvider.getSystemUserDAO();
			ICompanyDAO companyDAO = daoProvider.getCompanyDAO();
			IStockDAO stockDAO = daoProvider.getStockDAO();
			IStockItemDAO stockItemDAO = daoProvider.getStockItemDAO();
			
			productDAO.deleteAll();
			systemUserDAO.deleteAll();
			companyDAO.deleteAll();
			stockDAO.deleteAll();
			stockItemDAO.deleteAll();
	
			Company company = companyDAO.create(new Company("Die Firma"));
			systemUserDAO.create(new SystemUser("Test","test",company));
			Product product1 = productDAO.create(new Product("SN00001", "HD WD20010Cavier", new BigDecimal("120.49"), company));
			product1.setImagePath(moveImage(product1, "9939-fujitsu25hdd.jpg"));
			productDAO.update(product1);

			Product product2 = productDAO.create(new Product("SN00002", "SSD 840 Series", new BigDecimal("242.28"), company));
			Image i = new Image("productImage", new PackageResourceReference(ProductsPanel.class, "/images/products/SSD_840_Basic_250_GB_open.JPG"));
			product2.setImagePath(moveImage(product2, "SSD_840_Basic_250_GB_open.JPG"));
			productDAO.update(product2);
			Product product3 = productDAO.create(new Product("SN00003", "HD NPQS217 H3", new BigDecimal("220.99"), company));
			product3.setImagePath(moveImage(product3, "9939-fujitsu25hdd.jpg"));
			productDAO.update(product3);
			
			Stock stock1 = stockDAO.create(new Stock(company.getId(), "Stock 1"));
			Stock stock2 = stockDAO.create(new Stock(company.getId(), "Stock 2"));

			stockItemDAO.create(new StockItem(stock1.getId(), product1.getSerialNumber()), 50);
			stockItemDAO.create(new StockItem(stock1.getId(), product2.getSerialNumber()), 20);
			stockItemDAO.create(new StockItem(stock2.getId(), product1.getSerialNumber()), 30);
			stockItemDAO.create(new StockItem(stock2.getId(), product3.getSerialNumber()), 40);
			
			System.out.println("\nDataBase reseted");
		}
		
		private String getUploadFolder() {
			String OS = System.getProperty("os.name").toLowerCase();
			if ((OS.indexOf("win") >= 0)) {
				return "C:\\data\\emarketImages\\images\\products";
			} else {
				return "/data/emarketImages/images/products";
			}
		}
		
		private String moveImage(Product product, String imagename){

			File folder = new File(getUploadFolder() + "\\" + product.getCompany().getId());
			if( !folder.exists() ) {
				System.out.println("===> Folder");
				if(!folder.mkdirs()){
					System.out.println("===> Folder creat failed");
				}
				
			}
			File file = new File(getUploadFolder() + "\\" + imagename);
			File move = new File(getUploadFolder() + "\\" + product.getCompany().getId() + "\\" + product.getId()+ "-" + imagename);
			System.out.println("===> " + file.getAbsolutePath());
			System.out.println("===> " + move.getAbsolutePath());
			String result;
			if (move.exists()) {
				move.delete();
			}
			try {
				move.write(file);
				move.createNewFile();
				result = move.getAbsolutePath();
			} catch (IOException e) {
				e.printStackTrace();
				if ((System.getProperty("os.name").toLowerCase().indexOf("win") >= 0)) {
    	    		result = getUploadFolder() + "\\default.jpg";
    			} else {
    				result = getUploadFolder() + "\\default.jpg";
    			}
			}
			
			return result;
		}
	}
}