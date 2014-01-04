package de.ebus.emarket.frontend.pages.components;

import java.io.File;
import java.util.Date;
import java.util.IllegalFormatException;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.util.lang.Bytes;

public class FileUploadForm extends Form<Void> {

	private static final long serialVersionUID = 4840983544285452921L;
	private FileUploadField fileUpload;
	private String fileID = null;
	
	public FileUploadForm(String id, String fileID) {
		super(id);
		this.fileID = fileID;
		//setMultiPart(true);
		setMaxSize(Bytes.megabytes(10));
		add(fileUpload = new FileUploadField("fileUpload"));
	}

	private String getUploadFolder() {
		String OS = System.getProperty("os.name").toLowerCase();
		if ((OS.indexOf("win") >= 0)) {
			return "C:\\data\\emarket\\";
		} else {
			return "/data/emarket/";
		}
	}

	@Override
	protected void onSubmit() {

		final FileUpload uploadedFile = fileUpload.getFileUpload();
		if (uploadedFile != null) {
			String[] filenameparts = uploadedFile.getClientFileName().split("\\.(?=[^\\.]+$)");
			
			if (!(filenameparts[1].toLowerCase().equals("zip"))){
				error("file extension is not zip");
				return;
			}
			
			// write to a new file
			File newFile = new File(getUploadFolder() + filenameparts[0] + "_" + new Date().getTime() + ".zip");

			if (newFile.exists()) {
				newFile.delete();
			}

			try {
				newFile.createNewFile();
				uploadedFile.writeTo(newFile);

				info("saved file: " + newFile.getAbsolutePath()); // uploadedFile.getClientFileName());
			} catch (Exception e) {
				error("error while saving file: "+ e.getMessage());
			}
		}
	}
}
