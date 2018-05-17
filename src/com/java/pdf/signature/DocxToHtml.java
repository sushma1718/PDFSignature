package com.java.pdf.signature;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class DocxToHtml {

	 private static final String docName = "Position Description -ravi.docx";
	    private static final String outputlFolderPath = "C:/Users/Costco/Documents/";


	    String htmlNamePath = "docHtml.html";
	    String zipName="_tmp.zip";
	    File docFile = new File(outputlFolderPath+docName);
	    File zipFile = new File(zipName);




	      public void ConvertWordToHtml() {

	          try {

	                // 1) Load DOCX into XWPFDocument
	                InputStream doc = new FileInputStream(new File(outputlFolderPath+docName));
	                System.out.println("InputStream"+doc);
	                XWPFDocument document = new XWPFDocument(doc);

	                // 2) Prepare XHTML options (here we set the IURIResolver to load images from a "word/media" folder)
	                XHTMLOptions options = XHTMLOptions.create(); //.URIResolver(new FileURIResolver(new File("word/media")));;

	                // Extract image
	                String root = "target";
	                File imageFolder = new File( root + "/images/" + doc );
	                options.setExtractor( new FileImageExtractor( imageFolder ) );
	                // URI resolver
	                options.URIResolver( new FileURIResolver( imageFolder ) );


	                OutputStream out = new FileOutputStream(new File(htmlPath()));
	                XHTMLConverter.getInstance().convert(document, out, options);


	                System.out.println("OutputStream "+out.toString());
	            } catch (FileNotFoundException ex) {

	            } catch (IOException ex) {

	            }
	         }

	      public static void main(String[] args) {
	    	  DocxToHtml cwoWord=new DocxToHtml();
	         cwoWord.ConvertWordToHtml();
	         System.out.println();
	    }



	      public String htmlPath(){
	        // d:/docHtml.html
	          return outputlFolderPath+htmlNamePath;
	      }

	      public String zipPath(){
	          // d:/_tmp.zip
	          return outputlFolderPath+zipName;
	      }

	      
	 
}
