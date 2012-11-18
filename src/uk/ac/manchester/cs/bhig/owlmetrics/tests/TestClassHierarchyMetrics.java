package uk.ac.manchester.cs.bhig.owlmetrics.tests;

import java.awt.List;
import java.util.ArrayList;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.metrics.OWLMetricManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.ConsoleProgressMonitor;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;

import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owlclass.MaxNumberOfSubClasses;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owlclass.NumberOfLeafClassses;

import org.semanticweb.owlapi.metrics.OWLMetric;

public class TestClassHierarchyMetrics {

	// variables related with the ontology
	private static String documentIRI;
	private static OWLOntologyManager manager;
	private static OWLDataFactory factory;
	private static OWLOntology ont;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		documentIRI = args[0];

		loadOntology();
		//create a structural reasoner
		ConsoleProgressMonitor progressMonitor = new ConsoleProgressMonitor();
		// configuration of the monitor
		OWLReasonerConfiguration config = new SimpleConfiguration( 
				progressMonitor);
		StructuralReasonerFactory reasfactory = new StructuralReasonerFactory();
		OWLReasoner structuralReasoner = reasfactory.createReasoner(ont, config);

		structuralReasoner.precomputeInferences();
		
		MaxNumberOfSubClasses metric1 = new MaxNumberOfSubClasses(manager, structuralReasoner); 
		metric1.setImportsClosureUsed(false);
		metric1.setOntology(ont);
		metric1.recomputeMetric();
		System.out.println(metric1.getName() + "  " + metric1.getValue());
		
		
		
		NumberOfLeafClassses metric2 = new NumberOfLeafClassses(manager, structuralReasoner);
		metric2.setImportsClosureUsed(false);
		metric2.setOntology(ont);
		metric2.recomputeMetric();
		System.out.println(metric2.getName() + "  " + metric2.getValue());
		
		ArrayList<OWLMetric<?>> metricList = new ArrayList<OWLMetric<?>>();
		metricList.add(metric2);
		  
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
			System.out.println(manager.getOntologies());
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}

	}


	/**
	 * Separates the name of the ontology from the
	 * complete IRI of the ontology
	 * 
	 * @return a string, which is the 
	 * name of the ontology 
	 */
	private static String getOntologyName() {
		String ontoName = documentIRI.substring(documentIRI
				.lastIndexOf("/") + 1);

		return ontoName;

	}//getOntologyName()

}
