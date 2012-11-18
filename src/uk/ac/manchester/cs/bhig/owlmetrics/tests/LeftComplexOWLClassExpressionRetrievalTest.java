package uk.ac.manchester.cs.bhig.owlmetrics.tests;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import uk.ac.manchester.cs.jfact.JFactFactory;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.metrics.ImportClosureSize;
import org.semanticweb.owlapi.metrics.OWLMetric;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.reasoner.ConsoleProgressMonitor;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;

import uk.ac.manchester.cs.bhig.owlmetrics.OWLMetricFactory;
import uk.ac.manchester.cs.bhig.owlmetrics.entailments.OWLSubClassEntailmentCount;
import uk.ac.manchester.cs.bhig.owlmetrics.outputRendering.XMLOutputWriter;
import uk.ac.manchester.cs.factplusplus.owlapiv3.FaCTPlusPlusReasonerFactory;

import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;

public class LeftComplexOWLClassExpressionRetrievalTest {

	private static String documentIRI;
	private static OWLOntologyManager manager;
	private static OWLOntology ont;

	private static OWLReasonerFactory reasonerFactory = null;
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		documentIRI = args[0];
		boolean inferred =  true; //Boolean.parseBoolean(args[1]);
		

		loadOntology();
		
		//////////////////////////////////////////////////////
		//inferred 
		//
		if(inferred){
			OWLReasoner reasoner = createReasoner("JFact");
			System.out.println("\n Classifying with " + reasoner.getReasonerName()
					+ " reasoner....");
			reasoner.precomputeInferences();
			
			OWLSubClassEntailmentCount entmetric= new OWLSubClassEntailmentCount(manager, reasoner);
			entmetric.setOntology(ont);
			entmetric.getValue();
			
			HashSet<OWLSubClassOfAxiom> complexEntailments = entmetric.getComplexOWLSubClassOfEntailments(); 
				//entmetric.getLeftComplexOWLSubClassOfEntailments();
			
			if(!complexEntailments.isEmpty()){
				for(OWLSubClassOfAxiom sub : complexEntailments){
					System.out.println(sub);
				}
			}
			else
				System.out.println("There aren't any complex entailments");
			
		}
		
		
		///////////////////////////////////////////////////////
		//create the xml file 
		//
//		XMLOutputWriter.initializeXMLOutputWriter(getOntologyName());
//		
//		
//		XMLOutputWriter.startTag("Ontology-Metrics");
//
//		
//		
//		XMLOutputWriter.closeTag("Ontology-Metrics");
//
//		// close xml file
//		XMLOutputWriter.saveChangesAndCloseXMLFile();
//		
//		//write xls file
//		XMLOutputWriter.getMetricValues();
		
		 
	}
	
	private static void setOntology(List<OWLMetric> metrics) {
		for(OWLMetric m : metrics ){
			m.setOntology(ont);
		}
		
	}

	/**
	 * This should go on the XMLOutputWriter class
	 * @param importClosureSize
	 */
	private static void writeGeneralInfoInXML(ImportClosureSize importClosureSize) {
		importClosureSize.setOntology(ont);
		//write the general info of the ontology in the XML file
		XMLOutputWriter.startTag("General-Information");
		XMLOutputWriter.writeMetricInXML("Name", getOntologyName());
		XMLOutputWriter.writeMetricInXML("Physical IRI", documentIRI);
		XMLOutputWriter.writeMetricInXML(importClosureSize.getName(), importClosureSize.getValue().toString());
		XMLOutputWriter.writeMetricInXML("Imports", ont.getImports().toString());
		XMLOutputWriter.writeMetricInXML("Number of axioms", String.valueOf(ont.getAxiomCount()));

		XMLOutputWriter.closeTag("General-Information");
	}
	
	
	
	private static List<OWLMetric> generateInferredPairedMetrics(OWLMetricFactory mfactory){
		List<OWLMetric> metrics = new ArrayList<OWLMetric>();
		
		metrics.addAll(getClassMetrics(mfactory));
		metrics.addAll(getClassExpressionMetrics(mfactory));
		metrics.addAll(getObjectPropertyMetrics(mfactory));
		metrics.addAll(getDataPropertyMetrics(mfactory));
		metrics.addAll(getOWLIndividualMetrics(mfactory));
		
		return metrics;
	
	}
	
	private static List<OWLMetric> getClassMetrics(OWLMetricFactory mfactory){
		List<OWLMetric> metrics = new ArrayList<OWLMetric>();
		metrics.addAll(mfactory.getBasicClassMetrics());
		metrics.addAll(mfactory.getSubClassHierarchyMetrics());
		metrics.addAll(mfactory.getSuperClassHierarchyMetrics());
		metrics.addAll(mfactory.getClassTreeMetrics());
		metrics.addAll(mfactory.getClassUsageMetrics());
		return metrics;
	}
	
	private static List<OWLMetric> getClassExpressionMetrics(OWLMetricFactory mfactory){
		List<OWLMetric> metrics = new ArrayList<OWLMetric>();
		metrics.addAll(mfactory.getClassExpressionMetrics());
		return metrics;
	}
	
	
	private static List<OWLMetric> getObjectPropertyMetrics(OWLMetricFactory mfactory){
		List<OWLMetric> metrics = new ArrayList<OWLMetric>();
		metrics.addAll(mfactory.getBasicObjectPropertyMetrics());
		metrics.addAll(mfactory.getSubObjectPropertyHierarchyMetrics());
		metrics.addAll(mfactory.getSuperObjectPropertyHierarchyMetrics());
		metrics.addAll(mfactory.getObjectPropertyTreeMetrics());
		metrics.addAll(mfactory.getOWLObjectPropertUsageMetrics());
		return metrics;
	}
	
	
	private static List<OWLMetric> getDataPropertyMetrics(OWLMetricFactory mfactory){
		List<OWLMetric> metrics = new ArrayList<OWLMetric>();
		metrics.addAll(mfactory.getBasicDataPropertyMetrics());
		metrics.addAll(mfactory.getSubDataPropertyHierarchyMetrics());
		metrics.addAll(mfactory.getSuperDataPropertyHierarchyMetrics());
		metrics.addAll(mfactory.getDataPropertyTreeMetrics());
		metrics.addAll(mfactory.getOWLDataPropertUsageMetrics());
		return metrics;
	}

	
	private static List<OWLMetric> getOWLIndividualMetrics(OWLMetricFactory mfactory){
		List<OWLMetric> metrics = new ArrayList<OWLMetric>();
		
		metrics.addAll(mfactory.getBasicIndividualMetrics());
		metrics.addAll(mfactory.getOWLIndividualUsageMetrics());
		
		return metrics;
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
	
	/**
	 * Creates a Hermit, Factplusplus or Pellet reasoner
	 * 
	 * @param reasonerName
	 * @return
	 */
	private static OWLReasoner createReasoner(String reasonerName) {

		// Create a console progress monitor
		ConsoleProgressMonitor progressMonitor = new ConsoleProgressMonitor();
		progressMonitor.reasonerTaskStarted(ConsoleProgressMonitor.LOADING);
		progressMonitor.reasonerTaskStopped();
		// configuration of the monitor
		OWLReasonerConfiguration config = new SimpleConfiguration(
				progressMonitor);
		

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

		return reasoner;
	}

}
