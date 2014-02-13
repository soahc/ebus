package de.ebus.emarket.frontend.pages.panel.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.util.io.ByteArrayOutputStream;

import de.ebus.emarket.persistence.entities.Product;

public class ReadImageModel extends AbstractReadOnlyModel<DynamicImageResource> {

	private static final long serialVersionUID = 1L;

	private final Product product;

	public ReadImageModel(Product product) {
		super();
		this.product = product;
	}

	@Override
	public DynamicImageResource getObject() {
		final DynamicImageResource dir = new DynamicImageResource() {

			private static final long serialVersionUID = 1L;

			@Override
			protected byte[] getImageData(Attributes attributes) {
				byte[] imageBytes = null;
				imageBytes = getImageAsBytes(getUploadFolder() + product.getImagePath());

				return imageBytes;
			}
		};
		dir.setFormat("image/jpg");
		return dir;
	}

	private byte[] getImageAsBytes(String label) {
		final byte[] imageBytes = null;
		try {
			final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			final InputStream inStream = new FileInputStream(new File(label));
			copy(inStream, outStream);
			inStream.close();
			outStream.close();
			return outStream.toByteArray();

		} catch (final IOException e) {
			e.printStackTrace();
		}
		return imageBytes;
	}

	private void copy(InputStream source, OutputStream destination) throws IOException {
		final byte[] buf = new byte[1024];
		int len;
		while ((len = source.read(buf)) > 0) {
			destination.write(buf, 0, len);
		}
		source.close();
		destination.close();
	}

	private String getUploadFolder() {
		final String OS = System.getProperty("os.name").toLowerCase();
		if ((OS.indexOf("win") >= 0)) {
			return "C:\\data\\emarketImages\\images\\products\\";
		} else {
			return "/data/emarketImages/images/products/";
		}
	}
}
