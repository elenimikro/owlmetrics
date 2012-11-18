package uk.ac.manchester.cs.bhig.owlmetrics.application;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;


public class TextRepositoryMetricGenerator {

	/**
	 * @param classification 
	 * @param inferred 
	 * @param args
	 */
	public static void generateMetricsForListOfOntologies(String filename, String reasName, boolean inferred, boolean classification) {
		
		try {
			DataInputStream in = openFileWithOntologyIRIs(filename);
			
			String reasonerName = reasName;
			
			PrintStream printer = openTextFile("finishedURIs.txt");
			
			
			PrintStream printer2 = openTextFile("ReasonerTimedOutOntologies.txt");
			
			while (in.available() != 0) {
				String line = in.readLine();
				System.out.println("Generating metrics for: \n" + line);
				OWLMetricGenerator gen = new OWLMetricGenerator();
				// AllMetricGenerator gen = new AllMetricGenerator(line, reasonerName);
				 String result = gen.generateMetrics(line, reasonerName, inferred, classification);
				 if(result != null)
					 printer.println(line);
				 else
					 printer2.println(line);
				
			}
			
			in.close();
			printer.close();
			printer2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static List<File> parseFolderForOntologies(String folderURI){
		List<File> fileList = new ArrayList<File>();
		
		File folder = new File("/eclipse-workspace/MetricClassification/cleanedMetricResults2/");
		
		File[] files = folder.listFiles();
		
		for(int i=0; i<files.length; i++){
			if(files[i].isFile() && (files[i].getName().indexOf("xml")!=-1 || files[i].getName().indexOf("owl")!=-1))
				fileList.add(files[i]);
		}
		
		return fileList;
	}
	
	private static DataInputStream openFileWithOntologyIRIs(String filename) {
		DataInputStream dis = null;
		try {
			File file = new File(filename);
			FileInputStream fis = new FileInputStream(file);  
			BufferedInputStream bis = new BufferedInputStream(fis);  
			dis = new DataInputStream(bis);
			
			
		} catch (FileNotFoundException e) {
			System.out.println("File was not found");
			e.printStackTrace();
		}  
		
		return dis;
		
	}
	

	  
	static private PrintStream openTextFile(String filename) {
		FileOutputStream out; // declare a file output object
		PrintStream p = null; // declare a print stream object

		try {
			// Create a new file output stream
			out = new FileOutputStream(filename, true);

			// Connect print stream to the output stream
			p = new PrintStream(out);

			//p.println("This is written to a file");

			
		} catch (Exception e) {
			System.err.println("Error writing to file");
		}
		
		return p;
	}

	
	

}
