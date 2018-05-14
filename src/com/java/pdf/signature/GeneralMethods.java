package com.java.pdf.signature;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class GeneralMethods {

	PDFont boldFont = PDType1Font.TIMES_BOLD;
	PDFont normalFont = PDType1Font.TIMES_ROMAN;
	PDFont italicFont = PDType1Font.TIMES_ITALIC;

	int titleFontSize = 24;
	int headFontSize = 14;
	int normalFontSize = 12;
	int smallFontSize = 10;

	/**********************************************************************
	 ************************** Org Logo ******************************
	 **********************************************************************/
	@SuppressWarnings("deprecation")
	public PDDocument addLogo(PDDocument document, PDPage page, PDPageContentStream contentStream) throws IOException {

		PDImageXObject pdImage = PDImageXObject.createFromFile("C:/Users/Costco/Desktop/Misc/idcard/swarm1.png",
				document);
		if (pdImage.getWidth() - pdImage.getHeight() > 200f)
			contentStream.drawImage(pdImage, 20, 670, 250, 50);
		else
			contentStream.drawImage(pdImage, 20, 670, 120, 110);

		contentStream.setStrokingColor(192, 192, 192);
		contentStream.drawLine(10, 668, 600, 669);

		return document;

	}

	/**********************************************************************
	 ************************** Org Header ******************************
	 **********************************************************************/
	@SuppressWarnings("deprecation")
	public PDDocument addHeader(PDDocument document, PDPage page, PDPageContentStream contentStream)
			throws IOException {

		int marginTop = 3;

		String orgName = "SwarmHr";
		String addr1 = "1444 N Farnsworth Ave";
		String addr2 = "Aurora IL US 60505";
		String phone1 = "Phone1: 254-458-8695 ";
		String phone2 = "Phone2:  151-858-9969";
		String website = "www.swarmhr.com";
		String title = "Offer Letter";

		float titleWidth = (boldFont.getStringWidth(title) / 1000 * titleFontSize) + 10;
		float titleHeight = boldFont.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * titleFontSize;

		float nameWidth = (boldFont.getStringWidth(orgName) / 1000 * normalFontSize) + 10;
		float nameHeight = boldFont.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * normalFontSize;

		float addr1Width = (boldFont.getStringWidth(addr1) / 1000 * smallFontSize) + 10;
		float addr2Width = (boldFont.getStringWidth(addr2) / 1000 * smallFontSize) + 10;
		float phone1Width = (boldFont.getStringWidth(phone1) / 1000 * smallFontSize) + 10;
		float phone2Width = (boldFont.getStringWidth(phone2) / 1000 * smallFontSize) + 10;
		float websiteWidth = (boldFont.getStringWidth(website) / 1000 * smallFontSize) + 10;

		contentStream.beginText();

		// Header
		contentStream.setFont(boldFont, titleFontSize);
		contentStream.moveTextPositionByAmount((page.getMediaBox().getWidth() - titleWidth),
				page.getMediaBox().getHeight() - marginTop - titleHeight);
		contentStream.drawString(title);
		contentStream.endText();

		// Organization/Department Name
		contentStream.beginText();
		contentStream.setFont(boldFont, normalFontSize);
		contentStream.moveTextPositionByAmount((page.getMediaBox().getWidth() - nameWidth),
				page.getMediaBox().getHeight() - (marginTop + titleHeight + nameHeight));
		contentStream.drawString(orgName);
		contentStream.endText();

		// Address 1
		contentStream.beginText();
		contentStream.setFont(normalFont, smallFontSize);
		contentStream.moveTextPositionByAmount((page.getMediaBox().getWidth() - addr1Width),
				page.getMediaBox().getHeight() - (marginTop + titleHeight + (2 * nameHeight)));
		contentStream.drawString(addr1);
		contentStream.endText();

		// Address 2
		contentStream.beginText();
		contentStream.setFont(normalFont, smallFontSize);
		contentStream.moveTextPositionByAmount((page.getMediaBox().getWidth() - addr2Width),
				page.getMediaBox().getHeight() - (marginTop + titleHeight + (3 * nameHeight)));
		contentStream.drawString(addr2);
		contentStream.endText();

		// Phone 1
		contentStream.beginText();
		contentStream.setFont(normalFont, smallFontSize);
		contentStream.moveTextPositionByAmount((page.getMediaBox().getWidth() - phone1Width),
				page.getMediaBox().getHeight() - (marginTop + titleHeight + (4 * nameHeight)));
		contentStream.drawString(phone1);
		contentStream.endText();

		// Phone 2
		contentStream.beginText();
		contentStream.setFont(normalFont, smallFontSize);
		contentStream.moveTextPositionByAmount((page.getMediaBox().getWidth() - phone2Width),
				page.getMediaBox().getHeight() - (marginTop + titleHeight + (5 * nameHeight)));
		contentStream.drawString(phone2);
		contentStream.endText();

		// website
		contentStream.beginText();
		contentStream.setFont(normalFont, smallFontSize);
		contentStream.moveTextPositionByAmount((page.getMediaBox().getWidth() - websiteWidth),
				page.getMediaBox().getHeight() - (marginTop + titleHeight + (6 * nameHeight)));
		contentStream.drawString(website);
		contentStream.endText();

		return document;

	}

}
