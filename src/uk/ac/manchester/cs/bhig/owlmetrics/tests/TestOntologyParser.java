package uk.ac.manchester.cs.bhig.owlmetrics.tests;

import java.io.FileOutputStream;
import java.io.PrintStream;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.io.UnparsableOntologyException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.manchester.cs.bhig.owlmetrics.utilities.LocalIRIMapper;

public class TestOntologyParser {
	
	private static String documentIRI;
	private static OWLOntologyManager manager;
	private static OWLOntology ont;

	//private static OWLReasonerFactory reasonerFactory = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PrintStream printer = openTextFile("unparsableIRIs.txt");
		try {
			documentIRI = args[0];
			
			//loadOntologyWithoutImports();
			
			loadOntology(); 
			//throw new UnparsableOntologyException(IRI.create(documentIRI), null);
		} catch (NullPointerException e) {
			System.out.println("The ontology could not be parsed and threw a NullPointerException");
			//e.printStackTrace();
		} catch (UnparsableOntologyException e) {
			System.out.println("Unparsable ontology. Sorry we've tried everything.");
			
			
		} finally{
			System.out.println("Sorry we've tried everything. Ontologies are no good anyway!");
			printer.println(documentIRI);
			printer.close();
		}
	}
	
	private static void loadOntology() throws UnparsableOntologyException{
		LocalIRIMapper iriMapper = new LocalIRIMapper();
		manager = OWLManager.createOWLOntologyManager();
		//MetricLogger.log(documentIRI);
		System.out.println(documentIRI);
		ont = iriMapper.addGeneralImports(manager, documentIRI);
		//MetricLogger.log(manager.getOntologies().toString());
		System.out.println(manager.getOntologies());
	}
	
	/**
	 * Creates the manager and load the 
	 * ontology from the documentIRI 
	 */
	private static void loadOntologyWithoutImports() {
		try {
			manager = OWLManager.createOWLOntologyManager();
			ont = manager.loadOntologyFromOntologyDocument(IRI.create(documentIRI));
			System.out.println(documentIRI);
			System.out.println(manager.getOntologies());
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}
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
