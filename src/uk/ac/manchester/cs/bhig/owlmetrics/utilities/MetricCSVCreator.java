package uk.ac.manchester.cs.bhig.owlmetrics.utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;


public class MetricCSVCreator {


	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String filename = args[0].substring(args[0].lastIndexOf("/")+1) + ".csv";
		PrintStream p = openTextFile(filename);

		List<String> fileList = parseFolderForXmlFiles(args[0]);

		System.out.println("processing xml files...");

		ArrayList<String> csvAttributes = new ArrayList<String>();

		for(int i=0; i<fileList.size(); i++){

			XMLInput xml = new XMLInput();
			xml.open(fileList.get(i));
			System.out.println(fileList.get(i) + "....opened");

			//the header of the csv is determined according to the 
			//first xml file
			if(i==0){
				csvAttributes = xml.getFullMetricAttributes();
				csvAttributes.addAll(xml.getClusterCategories());
				//write the attributes in the csv file 
				for(int j=0; j<csvAttributes.size(); j++){
					p.print(csvAttributes.get(j) + ",");
				}
				p.println();
			}

			ArrayList<String> attributes = xml.getFullMetricAttributes();
			attributes.addAll(xml.getClusterCategories());
			//check if the categories of the xml fall into the template 
			//of the csv file
			if(!attributes.equals(csvAttributes)){
				System.out.println("Wrong attributes..");
			}
			//get the metrics
			ArrayList<String> values = xml.getFullMetricValues();
			values.addAll(xml.getClusterValues());
			for(int j=0; j<values.size(); j++){
				p.print(values.get(j) + ",");
			}
			p.println();
		}

		p.close();
	}

	private static List<String> parseFolderForXmlFiles(String folderURI){
		List<String> fileList = new ArrayList<String>();

		File folder = new File(folderURI);

		File[] files = folder.listFiles();

		for(int i=0; i<files.length; i++){
			if(files[i].isFile() && (files[i].getName().endsWith("xml")))
				fileList.add(files[i].getPath());
		}

		return fileList;
	}

	private static PrintStream openTextFile(String filename) {
		FileOutputStream out; // declare a file output object
		PrintStream p = null; // declare a print stream object

		try {
			// Create a new file output stream
			out = new FileOutputStream(filename);

			// Connect print stream to the output stream
			p = new PrintStream(out);

		} catch (Exception e) {
			System.err.println("Error writing to file");
		}

		return p;
	}

}
