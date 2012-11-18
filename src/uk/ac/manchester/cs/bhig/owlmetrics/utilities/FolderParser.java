package uk.ac.manchester.cs.bhig.owlmetrics.utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class FolderParser {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<File> files = parseFolderForOntologies(args[0]);

		PrintStream printer = openTextFile("FolderRepositoryIRIs.txt");
		
		for(int i=0; i<files.size(); i++){
			System.out.println("Parsing " + files.get(i).getName());
			String documentIRI = "file:" + files.get(i).getPath();
			
			printer.println(documentIRI);
			
		}
		
		printer.close();

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
	  
	private static PrintStream openTextFile(String filename) {
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
