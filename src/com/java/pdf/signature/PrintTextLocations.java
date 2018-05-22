package com.java.pdf.signature;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
 
	 

	/**
	 * This is an example on how to get some x/y coordinates of text.
	 *
	 * @author Ben Litchfield
	 */
	public class PrintTextLocations extends PDFTextStripper
	{
	    /**
	     * Instantiate a new PDFTextStripper object.
	     *
	     * @throws IOException If there is an error loading the properties.
	     */
	    public PrintTextLocations() throws IOException
	    {
	    }

	    /**
	     * This will print the documents data.
	     *
	     * @param args The command line arguments.
	     *
	     * @throws IOException If there is an error parsing the document.
	     */
	    public static void main( String[] args ) throws IOException
	    {String outputFile = "C:/Users/Costco/Documents/hi.pdf";
	       
	            try (PDDocument document = PDDocument.load(new File(outputFile)))
	            {
	                PDFTextStripper stripper = new PrintTextLocations();
	                stripper.setSortByPosition( true );
	                stripper.setStartPage( 0 );
	                stripper.setEndPage( document.getNumberOfPages() );

	                Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
	                stripper.writeText(document, dummy);
	            }
	        }
	    

	    /**
	     * Override the default functionality of PDFTextStripper.
	     */
	    @Override
	    protected void writeString(String string, List<TextPosition> textPositions) throws IOException
	    {
	        for (TextPosition text : textPositions)
	        {
	        	 
	            System.out.println( "String[" + text.getXDirAdj() + "," +
	                    text.getYDirAdj() + " fs=" + text.getFontSize() + " xscale=" +
	                    text.getXScale() + " height=" + text.getHeightDir() + " space=" +
	                    text.getWidthOfSpace() + " width=" +
	                    text.getWidthDirAdj() + "]" + text.getUnicode() );}
	       
	    }
	    
	    /**
	     * This will print the usage for this document.
	     */
	    private static void usage()
	    {
	        System.err.println( "Usage: java " + PrintTextLocations.class.getName() + " <input-pdf>" );
	    }
	} 