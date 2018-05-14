package com.java.pdf.signature;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceCharacteristicsDictionary;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceDictionary;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceStream;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDSignatureField;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField; 

public class FillablePDF {
	
	PDFont boldFont = PDType1Font.TIMES_BOLD;
	PDFont normalFont = PDType1Font.TIMES_ROMAN;
	PDFont italicFont = PDType1Font.TIMES_ITALIC;

	int titleFontSize = 24;
	int headFontSize = 14;
	int normalFontSize = 12;
	int smallFontSize = 10;

	public static void main(String[] args) throws IOException {
		PDDocument doc = new PDDocument();
		PDPage page = new PDPage( );
		PDPageContentStream contentStream = new PDPageContentStream(doc, page);
		GeneralMethods general = new GeneralMethods();
		general.addLogo(doc, page, contentStream);
		general.addHeader(doc, page, contentStream);
		addFillableTextField(doc, page, contentStream, 400);
		addSignatureField(doc, page, contentStream);
		addpermissions(doc);
		contentStream.close();
		doc.addPage(page);
		doc.save("D:/pd/fillableForm.pdf");
		doc.close(); 
	}
	
	static PDDocument addFillableTextField(PDDocument document, PDPage page, PDPageContentStream contentStream, int marginTop) throws IOException{
	 
		PDAcroForm form = new PDAcroForm(document);
		document.getDocumentCatalog().setAcroForm(form);

        PDFont font = PDType1Font.HELVETICA;
        PDResources resources = new PDResources();
        resources.put(COSName.getPDFName("Helv"), font);
        form.setDefaultResources(resources);

        PDTextField textField = new PDTextField(form);
        textField.setPartialName("Name");

        String defaultAppearance = "/Helv 12 Tf 0 0 0 rg";
        textField.setDefaultAppearance(defaultAppearance);

        form.getFields().add(textField);

        PDAnnotationWidget widget = textField.getWidgets().get(0);
        PDRectangle rect = new PDRectangle(50, 550, 200, 20);
        widget.setRectangle(rect);
        widget.setPage(page);

        PDAppearanceCharacteristicsDictionary fieldAppearance = new PDAppearanceCharacteristicsDictionary(new COSDictionary());
        fieldAppearance.setBorderColour(new PDColor(new float[]{0,0,0}, PDDeviceRGB.INSTANCE));
        fieldAppearance.setBackground(new PDColor(new float[]{1,1,1}, PDDeviceRGB.INSTANCE));
        widget.setAppearanceCharacteristics(fieldAppearance);

        widget.setPrinted(true);

        page.getAnnotations().add(widget); 
        textField.setValue("Sample Field");
		return document;
	}
	 
	
	private static void addSignatureField(PDDocument document, PDPage page, PDPageContentStream contentStream) throws InvalidPasswordException, IOException {

		PDFont font = PDType1Font.HELVETICA;
		PDResources resources = new PDResources();
		resources.put(COSName.getPDFName("Helv"), font);
 
		PDAcroForm acroForm = new PDAcroForm(document);
		acroForm.setDefaultResources(resources);
		document.getDocumentCatalog().setAcroForm(acroForm);

		PDRectangle rect = new PDRectangle(50, 50, 200, 30);

		PDAppearanceDictionary appearanceDictionary = new PDAppearanceDictionary();
		PDAppearanceStream appearanceStream = new PDAppearanceStream(document);
		appearanceStream.setBBox(rect.createRetranslatedRectangle());
		appearanceStream.setResources(resources);
		appearanceDictionary.setNormalAppearance(appearanceStream);
	    contentStream = new PDPageContentStream(document, appearanceStream);
		contentStream.setStrokingColor(Color.BLACK);
		contentStream.setNonStrokingColor(Color.WHITE);
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
		contentStream.setNonStrokingColor(Color.WHITE);
		contentStream.beginText();
		contentStream.setFont(font, rect.getHeight() / 5);
		contentStream.newLineAtOffset(3 * rect.getHeight() / 4,
				-font.getBoundingBox().getLowerLeftY() * rect.getHeight() / 5000);
		contentStream.showText("Customer");
		contentStream.endText();
		contentStream.close();

		PDSignatureField signatureField = new PDSignatureField(acroForm);
		signatureField.setPartialName("SignatureField"); 

		PDAnnotationWidget widget = signatureField.getWidgets().get(0);
		widget.setAppearance(appearanceDictionary);
		widget.setRectangle(rect);
		widget.setPage(page);

		page.getAnnotations().add(widget);
		acroForm.getFields().add(signatureField); 
	}
	
	static PDDocument addpermissions(PDDocument document) throws IOException{
		AccessPermission permission = new AccessPermission();
		permission.setCanPrint(false);
		permission.setCanExtractContent(false);

		StandardProtectionPolicy policy = new StandardProtectionPolicy("", "", permission);
		document.protect(policy);
		return document;
	}
	 
}
