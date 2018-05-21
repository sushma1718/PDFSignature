package com.java.pdf.signature;

import java.io.IOException;
import java.io.FileReader;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import org.jsoup.Jsoup;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.Document;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.pdf.PdfWriter;

public class Html2pdf2 {
	private Html2pdf2() {
	} 
	
	public static void main(String[] args){
	    String inputFile = "C:/Users/Costco/Documents/docHtml.html";
	    String outputFile = "C:/Users/Costco/Documents/docHtml.pdf";
	    try {
	    	String content = "";
	    	BufferedReader in = new BufferedReader(new FileReader(inputFile));
	        String str;
	        while ((str = in.readLine()) != null) {
	            content +=str;
	        }
	        in.close();
	        OutputStream file = new FileOutputStream(new File( outputFile));
	        Document document = new Document();
	        PdfWriter.getInstance(document, file);
	        document.open();
	        HTMLWorker htmlWorker = new HTMLWorker(document);
	        htmlWorker.parse(new StringReader(content));
	        document.close();
	        file.close();
	       
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	     
	    System.out.println("Done!");
	}
}
