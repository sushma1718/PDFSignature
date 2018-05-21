package com.java.pdf.signature;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

public class Test {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		 File currentDocument = new File("C:/Users/Costco/Documents/i983.pdf");
		    File newDocument = new File("C:/Users/Costco/Documents/doc_v2_filled.pdf");

		    String fieldName = "New Emergency Contact";
		    String value = "test";
		    PDDocument doc = null;

		    try (FileOutputStream fos = new FileOutputStream(newDocument))
		    {
		        Files.copy(currentDocument.toPath(), fos);

		        doc = PDDocument.load(currentDocument);
		        PDDocumentCatalog catalog = doc.getDocumentCatalog();

		        catalog.getCOSObject().setNeedToBeUpdated(true);
		        catalog.getPages().getCOSObject().setNeedToBeUpdated(true);

		        PDAcroForm form = catalog.getAcroForm();

		        form.getCOSObject().setNeedToBeUpdated(true);
		        form.getDefaultResources().getCOSObject().setNeedToBeUpdated(true);

		        PDField field = form.getField(fieldName);
		        field.setValue(value); // here the exception occurs.

		        // What should happen afterwards:
		        field.getCOSObject().setNeedToBeUpdated(true);
		        field.getAcroForm().getCOSObject().setNeedToBeUpdated(true);

		    ((COSDictionary) ((Object) field).getDictionary().getDictionaryObject("AP")).getDictionaryObject("N").setNeedToBeUpdated(true);

		        try (FileInputStream fis = new FileInputStream(newDocument))
		        {
		            doc.save(newDocument);
		        }
		    }
		    finally
		    {
		        if (null != doc)
		        {
		            doc.close();
		            doc = null;
		        }
		    }

	}

}
