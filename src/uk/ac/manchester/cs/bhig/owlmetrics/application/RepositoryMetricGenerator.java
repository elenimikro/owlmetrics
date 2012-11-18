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
import java.util.logging.Level;

import uk.ac.manchester.cs.bhig.owlmetrics.utilities.MetricLogger;


public class RepositoryMetricGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			MetricLogger.create();
			DataInputStream in = openFileWithOntologyIRIs(args[0]);
			
			String reasonerName = args[1];
			
			String timeout=null;
			if(args.length==3)
				timeout = args[2];
			
			PrintStream printer = openTextFile("finishedURIs.txt");
			
		
			
			while (in.available() != 0) {
				String line = in.readLine();
				MetricLogger.log("Generating metrics for: \n" + line);
				System.out.println("Generating metrics for: \n" + line);
				OWLMetricGenerator gen = new OWLMetricGenerator();
				gen.setReasonerTimeOut(timeout);
				// AllMetricGenerator gen = new AllMetricGenerator(line, reasonerName);
				 String result = gen.generateMetrics(line, reasonerName, true, false);
				 if(result == null)
					 printer.println(line);
//				 else
//					 printer2.println(line);
			}
			
			in.close();
			printer.close();
			//printer2.close();
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
			MetricLogger.getLogger().log(Level.SEVERE, "File was not found");
			MetricLogger.getLogger().log(Level.SEVERE,  e.getStackTrace().toString());
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
