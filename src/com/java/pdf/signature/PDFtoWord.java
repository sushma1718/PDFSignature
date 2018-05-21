package com.java.pdf.signature;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.lowagie.text.pdf.PdfReader; 

public class PDFtoWord { 
	  
	 
	    public static void main(String[] args) throws IOException {
	 XWPFDocument doc = new XWPFDocument();

	// Open the pdf file
	String pdf = "myfile.pdf";
	PdfReader reader = new PdfReader(pdf);
	PdfReaderContentParser parser = new PdfReaderContentParser(reader);

	// Read the PDF page by page
	for (int i = 1; i <= reader.getNumberOfPages(); i++) {
	    TextExtractionStrategy strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
	    // Extract the text
	    String text=strategy.getResultantText();
	    // Create a new paragraph in the word document, adding the extracted text
	    XWPFParagraph p = doc.createParagraph();
	    XWPFRun run = p.createRun();
	    run.setText(text);
	    // Adding a page break
	    run.addBreak(BreakType.PAGE);
	}
	// Write the word document
	FileOutputStream out = new FileOutputStream("myfile.docx");
	doc.write(out);
	// Close all open files
	out.close();
	reader.close();
	}
}
