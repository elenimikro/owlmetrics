package uk.ac.manchester.cs.bhig.owlmetrics.utilities;

import java.io.File;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyIRIMapper;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.AutoIRIMapper;
import org.semanticweb.owlapi.util.SimpleIRIMapper;

//test

public class LocalIRIMapper {
	
	private static OWLOntology ontology;
		
	
	public OWLOntology addGeneralImports(OWLOntologyManager manager, String ontologyName){
		
		OWLOntologyManager tempMan = OWLManager.createOWLOntologyManager();
		String nameWithNoSpaces;

		try {
			// if the ontology is loaded locally
			System.out.println(ontologyName);
			if (ontologyName.startsWith("file")) {
				
				//substitute the spaces with %20
				//nameWithNoSpaces = ontologyName.replaceAll(" ", "%20");
				
//				String localFolder = ontologyName.substring(ontologyName
//						.indexOf("/"), ontologyName.lastIndexOf("/"));
//				//String localFolder = ontologyName.substring(0, ontologyName.lastIndexOf("/"));
//				System.out.println(localFolder);
//				OWLOntologyIRIMapper autoIRIMapper = new AutoIRIMapper(
//						new File(localFolder), false);
//				manager.addIRIMapper(autoIRIMapper);
				
				OWLOntology tempOnt = tempMan.loadOntology(IRI
						.create(ontologyName));
				
				OWLOntologyIRIMapper iriMapper = new SimpleIRIMapper(tempOnt
						.getOntologyID().getOntologyIRI(), IRI
						.create(ontologyName));

				manager.addIRIMapper(iriMapper);
				
				String fileName = ontologyName.substring(ontologyName.indexOf("/"));
				ontology = manager.loadOntologyFromOntologyDocument(new File(fileName));
				
			} else {
				OWLOntology tempOnt = tempMan.loadOntology(IRI
						.create(ontologyName));

				OWLOntologyIRIMapper iriMapper = new SimpleIRIMapper(tempOnt
						.getOntologyID().getOntologyIRI(), IRI
						.create(ontologyName));

				manager.addIRIMapper(iriMapper);
				
				ontology = manager.loadOntology(IRI.create(ontologyName));
			}
			
			
			//System.out.println("IRI of the ontology " + nameWithNoSpaces);
			

			printOntologyAndImports(manager, ontology);

		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}

		return ontology;		
	}
	
	
	private static void printOntologyAndImports(OWLOntologyManager manager,
			OWLOntology ontology) {
		System.out.println("Loaded ontology:");
		
		printOntology(manager, ontology);

		// List the imported ontologies
		for (OWLOntology importedOntology : ontology.getImports()) {
			System.out.println("Imports:");
			printOntology(manager, importedOntology);
		}
	}

	
	/**
	 * Prints the IRI of an ontology and its document IRI.
	 * 
	 * @param manager
	 *            The manager that manages the ontology
	 * @param ontology
	 *            The ontology
	 */
	private static void printOntology(OWLOntologyManager manager,
			OWLOntology ontology) {
		IRI ontologyIRI = ontology.getOntologyID().getOntologyIRI();
		IRI documentIRI = manager.getOntologyDocumentIRI(ontology);
		System.out.println("    from " + documentIRI.toQuotedString());
	}

}
