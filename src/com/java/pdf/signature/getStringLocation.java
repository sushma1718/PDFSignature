package com.java.pdf.signature;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

public class getStringLocation {

	public static void main(String[] args) throws InvalidPasswordException, IOException {
		String outputFile = "C:/Users/Costco/Documents/docHtml.pdf";

		PDDocument document = PDDocument.load(new File(outputFile));

		System.out.println("\nVariables.pdf\n-------------\n");
		printSubwords(document, "`{text}`"); 
		System.out.println("Done");

	}

	static void printSubwords(PDDocument document, String searchTerm) throws IOException {
		System.out.printf("* Looking for '%s'\n", searchTerm);
		for (int page = 1; page <= document.getNumberOfPages(); page++) {
			List<TextPositionSequence> hits = findSubwords(document, page, searchTerm);
			for (TextPositionSequence hit : hits) {
				TextPosition lastPosition = hit.textPositionAt(hit.length() - 1);
				System.out.printf("  Page %s at %s, %s with width %s and last letter '%s' at %s, %s\n", page,
						hit.getX(), hit.getY(), hit.getWidth(), lastPosition.getUnicode(), lastPosition.getXDirAdj(),
						lastPosition.getYDirAdj());
			}
		}
	}

	static List<TextPositionSequence> findSubwords(PDDocument document, int page, String searchTerm) throws IOException {
		final List<TextPositionSequence> hits = new ArrayList<TextPositionSequence>();
		PDFTextStripper stripper = new PDFTextStripper() {
			@Override
			protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
				TextPositionSequence word = new TextPositionSequence(textPositions);
				String string = word.toString();

				int fromIndex = 0;
				int index;
				while ((index = string.indexOf(searchTerm, fromIndex)) > -1) {
					hits.add(word.subSequence(index, index + searchTerm.length()));
					fromIndex = index + 1;
				}
				super.writeString(text, textPositions);
			}
		};

		stripper.setSortByPosition(true);
		stripper.setStartPage(page);
		stripper.setEndPage(page);
		stripper.getText(document);
		return hits;
	}
}
