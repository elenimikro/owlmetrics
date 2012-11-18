package uk.ac.manchester.cs.bhig.owlmetrics.tests;

import org.mindswap.pellet.exceptions.InternalReasonerException;
import org.mindswap.pellet.exceptions.TimeoutException;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.ConsoleProgressMonitor;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.ReasonerInternalException;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.reasoner.TimeOutException;

import uk.ac.manchester.cs.factplusplus.owlapiv3.FaCTPlusPlusReasonerFactory;
import uk.ac.manchester.cs.jfact.JFactFactory;

import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;



public class OWLOntologyLoader {
	
	//variables related with the ontology
	private static String documentIRI;
	private static OWLOntologyManager manager;
	private static OWLDataFactory factory;
	private static OWLOntology ont;
	private OWLReasoner reasoner;
	
	public OWLOntologyLoader(String ontoIRI){
		documentIRI = ontoIRI;
		loadOntology();
	}
	
	public OWLOntologyManager getManager(){
		return manager;
	}
	
	public OWLOntology getOntology(){
		return ont;
	}
	
	public OWLReasoner getReasoner(){
		return reasoner;
	}
	
	/**
	 * Creates a Hermit, Factplusplus or Pellet reasoner
	 * 
	 * @param reasonerName
	 * @return
	 */
	public void setReasoner(String reasonerName) throws ReasonerInternalException,TimeOutException,
		TimeoutException,InternalReasonerException{

		OWLReasonerFactory reasonerFactory = null;
		
		// Create a console progress monitor
		ConsoleProgressMonitor progressMonitor = new ConsoleProgressMonitor();
		progressMonitor.reasonerTaskStarted(ConsoleProgressMonitor.LOADING);
		progressMonitor.reasonerTaskStopped();
		// configuration of the monitor
		//OWLReasonerConfiguration config = new SimpleConfiguration(
		//		progressMonitor);
		
		//put a timer
		OWLReasonerConfiguration config = new SimpleConfiguration();
		System.out.println(config.getTimeOut());
		

		if (reasonerName.equals("Hermit")) {
			reasonerFactory = new Reasoner.ReasonerFactory();
		} else if (reasonerName.equals("Fact++")) {
			reasonerFactory = new FaCTPlusPlusReasonerFactory();
		} else if (reasonerName.equals("Pellet")) {
			reasonerFactory = new PelletReasonerFactory();
		} else if (reasonerName.equals("JFact")) {
			reasonerFactory = new JFactFactory();
		}
		OWLReasoner reasoner = reasonerFactory.createReasoner(ont, config);

		System.out.println(reasonerFactory.getReasonerName());
		this.reasoner = reasoner;
	}
	
	/**
	 * Creates the manager and load the 
	 * ontology from the documentIRI 
	 */
	private static void loadOntology() {
		try {
			manager = OWLManager.createOWLOntologyManager();
			ont = manager.loadOntologyFromOntologyDocument(IRI.create(documentIRI));
			System.out.println(documentIRI);
			System.out.println("Loaded ontology:");
			System.out.println(manager.getOntologies());
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}

	}

}
