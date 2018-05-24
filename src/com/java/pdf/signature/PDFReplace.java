package com.java.pdf.signature;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

public class PDFReplace {

	public static void main(String[] args) throws InvalidPasswordException, IOException {
		String outputFile = "C:/Users/Costco/Documents/docHtml.pdf";

		getStringLocation helper = new getStringLocation();
		FillablePDF field = new FillablePDF();
		try (PDDocument document = PDDocument.load(new File(outputFile))) {
			String  coordinates = helper.printSubwords(document, "`{text}`");
			
			if(!(coordinates.equals(""))){
				String[] coOrd = coordinates.split(";;");
				PDPage page =(PDPage)document.getPages().get(Integer.parseInt(coOrd[0])-1);
				PDPageContentStream contentStream = new PDPageContentStream(document, page,true,true);
				field.addFillableTextField(document, page, contentStream, (Float.valueOf(coOrd[1])) , (835-Float.valueOf(coOrd[2]) )); 
				contentStream.close();
			} 
			document.save("C:/Users/Costco/Documents/docHtm1l.pdf");
			document.close(); 
		}

	}

}
