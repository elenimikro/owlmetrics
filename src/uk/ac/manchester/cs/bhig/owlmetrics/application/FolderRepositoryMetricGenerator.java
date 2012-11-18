package uk.ac.manchester.cs.bhig.owlmetrics.application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class FolderRepositoryMetricGenerator {

	public void generateRepositoryMetrics(String folderURI, String reasonerName, boolean inferred, boolean classification) {
		List<File> files = parseFolderForOntologies(folderURI);
		
		PrintStream printer0 = openTextFile("FolderRepositoryIRIs.txt");
		
		PrintStream printer = openTextFile("finishedURIs.txt");
		
		PrintStream printer2 = openTextFile("ReasonerTimedOutOntologies.txt");
		
		for(int i=0; i<files.size(); i++){
			//System.out.println("Generating metrics for: \n" + files.get(i).getName());
			String documentIRI = "file:" + files.get(i).getPath();
			
			printer0.println(documentIRI);
			
//			OWLMetricGenerator gen = new OWLMetricGenerator();
//			String result = gen.generateMetrics(documentIRI, reasonerName, true, true);
//			
//			if(result != null)
//				 printer.println(documentIRI);
//			 else
//				 printer2.println(documentIRI);
		}
		
		TextRepositoryMetricGenerator.generateMetricsForListOfOntologies("FolderRepositoryIRIs.txt", reasonerName, inferred, classification);
		

	}
	
	private static List<File> parseFolderForOntologies(String folderURI){
		List<File> fileList = new ArrayList<File>();
		
		File folder = new File(folderURI);
		
		File[] files = folder.listFiles();
		
		for(int i=0; i<files.length; i++){
			if(files[i].isFile() && (files[i].getName().indexOf("xml")!=-1 || files[i].getName().indexOf("owl")!=-1))
				fileList.add(files[i]);
		}
		
		return fileList;
	}
	  
	private PrintStream openTextFile(String filename) {
		FileOutputStream out; // declare a file output object
		PrintStream p = null; // declare a print stream object

		try {
			// Create a new file output stream
			out = new FileOutputStream(filename, true);

			// Connect print stream to the output stream
			p = new PrintStream(out);
			
		} catch (Exception e) {
			System.err.println("Error writing to file");
		}
		
		return p;
	}

}
