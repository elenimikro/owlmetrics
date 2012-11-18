package uk.ac.manchester.cs.bhig.owlmetrics.utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class EnhancedMetricCSVCreator {


	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String filename = args[0].substring(args[0].lastIndexOf("/")+1) + ".csv";
		

		List<String> fileList = parseFolderForXmlFiles(args[0]);

		System.out.println("processing xml files...");

		ArrayList<String> csvAttributes = new ArrayList<String>();
		
		
		
		ArrayList<Map<String, String>> allmetrics = new ArrayList<Map<String,String>>();

		for(int i=0; i<fileList.size(); i++){

			XMLInput xml = new XMLInput();
			xml.open(fileList.get(i));
			System.out.println(fileList.get(i) + "....opened");

			//the header of the csv is determined according to the 
			//first xml file
			if(i==0){
				csvAttributes = xml.getFullMetricAttributes();
				csvAttributes.addAll(xml.getClusterCategories());
				
				
//				//write the attributes in the csv file 
//				for(int j=0; j<csvAttributes.size(); j++){
//					p.print(csvAttributes.get(j) + ",");
//				}
//				p.println();
			}

//			ArrayList<String> attributes = xml.getFullMetricAttributes();
//			attributes.addAll(xml.getClusterCategories());
//			//check if the categories of the xml fall into the template 
//			//of the csv file
//			if(!attributes.equals(csvAttributes)){
//				System.out.println("Wrong attributes..");
//			}
			//get the metrics
			Map<String, String> metrics = xml.getFullMetrics();
			metrics.putAll(xml.getClusterMetrics());
			
			allmetrics.add(metrics);
//			for(int j=0; j<metrics.size(); j++){
//				p.print(metrics.get(j) + ",");
//			}
//			p.println();
			
			System.out.println("Parsed all xml files");
		}

		Map<String, ArrayList<String>> metricsMap = createFullMetricsMap(allmetrics);
		
		save(metricsMap, filename);
		
		System.out.println("Results were saved in " + filename);
	}

	private static void save(Map<String, ArrayList<String>> metricsMap,
			String filename) {
		PrintStream p = openTextFile(filename);
		Set<Entry<String,  ArrayList<String>>> entries = metricsMap.entrySet();
		
		ArrayList<ArrayList<String>> values = new ArrayList<ArrayList<String>>();
		
		for(Entry<String, ArrayList<String>> entry : entries){
			p.print(entry.getKey() + ",");
			values.add(entry.getValue());
		}
		p.println();
		
		for(int i=0; i<values.get(0).size(); i++){
			for(int j=0; j<values.size(); j++){
				p.print(values.get(j).get(i) + ",");
			}
			p.println();
		}
		
		
		p.close();
	}

	private static Map<String, ArrayList<String>> createFullMetricsMap(
			ArrayList<Map<String, String>> allmetrics) {
		Map<String, ArrayList<String>> metrMap = new  LinkedHashMap<String, ArrayList<String>>();
		
		Map<String, String> base = allmetrics.get(0);
		Set<Entry<String, String>> baseEntries = base.entrySet();
		
		for(Entry<String, String> bentry: baseEntries){
			String name = bentry.getKey();
			ArrayList<String> values = new ArrayList<String>();
			for(Map<String, String> map : allmetrics){
				values.add(map.get(name));
			}
			metrMap.put(name, values);
		}
		
		return metrMap;
		
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
