package com.java.pdf.kotlinToJava;

import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.interactive.form.PDSignatureField;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceDictionary;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceStream;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import java.awt.Color; 
import java.io.IOException;
import java.io.File;

public class demo {

	 
	public static void main(String[] args) throws IOException {

		newPdf();
		addSignatureField("D:\\pd\\sign.pdf");
	}

	private static void newPdf() throws IOException {
		PDDocument doc = new PDDocument();
		PDPage page = new PDPage(PDRectangle.A4);
		doc.addPage(page);
		PDType1Font font = PDType1Font.COURIER;
		PDPageContentStream contentStream = new PDPageContentStream(doc, page);
		contentStream.beginText();
		contentStream.setFont(font, 14f);
		contentStream.setLeading(14.5f);
		contentStream.setNonStrokingColor(Color.black);
		contentStream.newLineAtOffset(25f, 700f);
		contentStream.showText("Sushma");
		contentStream.newLine();
		contentStream.endText();
		contentStream.close();
		doc.save("D:\\pd\\sign.pdf");
		doc.close();
	}

	private static void addSignatureField(String filePath) throws InvalidPasswordException, IOException {

		PDFont font = PDType1Font.HELVETICA;
		PDResources resources = new PDResources();
		resources.put(COSName.getPDFName("Helv"), font);

		PDDocument document = PDDocument.load(new File(filePath));
		PDAcroForm acroForm = new PDAcroForm(document);
		acroForm.setDefaultResources(resources);
		document.getDocumentCatalog().setAcroForm(acroForm);

		PDRectangle rect = new PDRectangle(50, 750, 200, 50);

		PDAppearanceDictionary appearanceDictionary = new PDAppearanceDictionary();
		PDAppearanceStream appearanceStream = new PDAppearanceStream(document);
		appearanceStream.setBBox(rect.createRetranslatedRectangle());
		appearanceStream.setResources(resources);
		appearanceDictionary.setNormalAppearance(appearanceStream);
		PDPageContentStream contentStream = new PDPageContentStream(document, appearanceStream);
		contentStream.setStrokingColor(Color.BLACK);
		contentStream.setNonStrokingColor(Color.LIGHT_GRAY);
		contentStream.setLineWidth(2);
		contentStream.addRect(0, 0, rect.getWidth(), rect.getHeight());
		contentStream.fill();
		contentStream.moveTo(1 * rect.getHeight() / 4, 1 * rect.getHeight() / 4);
		contentStream.lineTo(2 * rect.getHeight() / 4, 3 * rect.getHeight() / 4);
		contentStream.moveTo(1 * rect.getHeight() / 4, 3 * rect.getHeight() / 4);
		contentStream.lineTo(2 * rect.getHeight() / 4, 1 * rect.getHeight() / 4);
		contentStream.moveTo(3 * rect.getHeight() / 4, 1 * rect.getHeight() / 4);
		contentStream.lineTo(rect.getWidth() - rect.getHeight() / 4, 1 * rect.getHeight() / 4);
		contentStream.stroke();
		contentStream.setNonStrokingColor(Color.DARK_GRAY);
		contentStream.beginText();
		contentStream.setFont(font, rect.getHeight() / 5);
		contentStream.newLineAtOffset(3 * rect.getHeight() / 4,
				-font.getBoundingBox().getLowerLeftY() * rect.getHeight() / 5000);
		contentStream.showText("Customer");
		contentStream.endText();
		contentStream.close();

		PDSignatureField signatureField = new PDSignatureField(acroForm);
		signatureField.setPartialName("SignatureField");
		PDPage page = document.getPage(0);

		PDAnnotationWidget widget = signatureField.getWidgets().get(0);
		widget.setAppearance(appearanceDictionary);
		widget.setRectangle(rect);
		widget.setPage(page);

		page.getAnnotations().add(widget);
		acroForm.getFields().add(signatureField);

		document.save(filePath);
		document.close();

	}
}
