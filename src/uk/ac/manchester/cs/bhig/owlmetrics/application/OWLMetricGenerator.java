package uk.ac.manchester.cs.bhig.owlmetrics.application;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
//import java.util.concurrent.TimeoutException;
import org.mindswap.pellet.exceptions.TimeoutException;

import uk.ac.manchester.cs.jfact.JFactFactory;

import org.mindswap.pellet.exceptions.InternalReasonerException;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.elk.owlapi.ElkReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.UnparsableOntologyException;
import org.semanticweb.owlapi.metrics.DLExpressivity;
import org.semanticweb.owlapi.metrics.ImportClosureSize;
import org.semanticweb.owlapi.metrics.OWLMetric;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.UnknownOWLOntologyException;
import org.semanticweb.owlapi.profiles.OWL2ELProfile;
import org.semanticweb.owlapi.reasoner.ConsoleProgressMonitor;
import org.semanticweb.owlapi.reasoner.IllegalConfigurationException;
import org.semanticweb.owlapi.reasoner.InconsistentOntologyException;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.ReasonerInternalException;
import org.semanticweb.owlapi.reasoner.ReasonerInterruptedException;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.reasoner.TimeOutException;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.util.DLExpressivityChecker;

import uk.ac.manchester.cs.bhig.owlmetrics.AxiomTypeCountMetricFactory;
import uk.ac.manchester.cs.bhig.owlmetrics.OWLMetricFactory;
import uk.ac.manchester.cs.bhig.owlmetrics.classification.AbstractClassificationMetric;
import uk.ac.manchester.cs.bhig.owlmetrics.outputRendering.XMLOutputWriter;
import uk.ac.manchester.cs.bhig.owlmetrics.utilities.LocalIRIMapper;
import uk.ac.manchester.cs.bhig.owlmetrics.utilities.MetricLogger;
import uk.ac.manchester.cs.bhig.owlmetrics.utilities.ThreadedReasoner;
import uk.ac.manchester.cs.factplusplus.owlapiv3.FaCTPlusPlusReasonerFactory;

import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;

public class OWLMetricGenerator {

	private String documentIRI;
	private OWLOntologyManager manager;
	private OWLOntology ont;

	private PrintStream timePrinter;

	private OWLReasonerFactory reasonerFactory = null;

	private long reasonerTimeOut = 3000000;

	public String generateMetrics(String docIRI, String reasonerName,
			boolean inferred, boolean classification) {
		documentIRI = docIRI;
		PrintStream unpPrinter = openTextFile("unparsableIRIs.txt");
		PrintStream reasPrinter = openTextFile("notReasonableIRIs.txt");
		timePrinter = openTextFile("ReasonerTimedOutOntologies.txt");

		try {
			loadOntology();
			// create a structural reasoner
			ConsoleProgressMonitor progressMonitor = new ConsoleProgressMonitor();
			// configuration of the monitor
			OWLReasonerConfiguration config = new SimpleConfiguration(
					progressMonitor);
			StructuralReasonerFactory reasfactory = new StructuralReasonerFactory();
			OWLReasoner structuralReasoner = reasfactory.createReasoner(ont,
					config);

			structuralReasoner.precomputeInferences();

			OWLMetricFactory mfactory = new OWLMetricFactory(manager,
					structuralReasoner, reasfactory);

			// asserted
			// class
			List<OWLMetric> clasm = getClassMetrics(mfactory);
			setOntology(clasm);

			// expressions
			List<OWLMetric> expr = getClassExpressionMetrics(mfactory);
			setOntology(expr);
			// object property
			List<OWLMetric> objProp = getObjectPropertyMetrics(mfactory);
			setOntology(objProp);
			// data property
			List<OWLMetric> dataProp = getDataPropertyMetrics(mfactory);
			setOntology(dataProp);

			// indis
			List<OWLMetric> indis = getOWLIndividualMetrics(mfactory);
			setOntology(indis);

			// axiom count
			Set<OWLMetric<?>> axiomTypeMetrics = AxiomTypeCountMetricFactory
					.createMetrics(manager);
			List<OWLMetric> axiomType = new ArrayList<OWLMetric>();
			axiomType.addAll(axiomTypeMetrics);
			setOntology(axiomType);

			// ////////////////////////////////////////////////////
			// inferred
			//
			List<OWLMetric> infpairList = new ArrayList<OWLMetric>();
			List<OWLMetric> entList = new ArrayList<OWLMetric>();

			ArrayList<AbstractClassificationMetric> inferClassificationList = new ArrayList<AbstractClassificationMetric>();

			if (inferred) {
				// boolean classified=false;

				OWLReasoner reasoner = createReasoner(reasonerName);

				// if(reasoner.getReasonerName().equals("JFact")){
				// ThreadedReasoner treas = new ThreadedReasoner(reasoner);
				//
				// classified = classifyWithJFactTimeOut(treas);
				// }
				// else{
				boolean classified = classifyWithReasoner(reasoner);
				// classified =
				// classifyWithReasonerTimeOut(reasoner,timePrinter);
				// }

				if (classified) {
					OWLMetricFactory minffactory = new OWLMetricFactory(
							manager, reasoner, reasonerFactory);

					infpairList = generateInferredPairedMetrics(minffactory);
					setOntology(infpairList);

					entList.addAll(minffactory.getEntailmentMetrics(ont));
					setOntology(entList);

					if (classification) {
						inferClassificationList = minffactory
								.getInferredMetricClassifications(ont);
					}
				} else {
					return documentIRI;
				}
			}

			List<AbstractClassificationMetric> classificationList = new ArrayList<AbstractClassificationMetric>();
			if (classification) {
				// generate the asserted classification first

				List<AbstractClassificationMetric> assertClassificationList = new ArrayList<AbstractClassificationMetric>();

				assertClassificationList = mfactory
						.getAssertedMetricClassifications(ont);

				classificationList.addAll(assertClassificationList);
				classificationList.addAll(inferClassificationList);
			}

			// /////////////////////////////////////////////////////
			// create the xml file
			//
			XMLOutputWriter.initializeXMLOutputWriter(getOntologyName());

			XMLOutputWriter.startTag("Ontology-Metrics");

			// write xml
			writeGeneralInfoInXML(new ImportClosureSize(manager));

			if (inferred) {
				XMLOutputWriter.writeSetOfPairsOfMetrics(clasm, infpairList,
						"Class metrics");

				XMLOutputWriter.writeSetOfPairsOfMetrics(expr, infpairList,
						"Class expression metrics");
				XMLOutputWriter.writeSetOfPairsOfMetrics(objProp, infpairList,
						"Object property metrics");
				XMLOutputWriter.writeSetOfPairsOfMetrics(dataProp, infpairList,
						"Data property metrics");
				XMLOutputWriter.writeSetOfPairsOfMetrics(indis, infpairList,
						"Individual metrics");

				XMLOutputWriter.writeSetOfMetrics(entList,
						"Entailment metrics", true);
			} else {
				XMLOutputWriter
						.writeSetOfMetrics(clasm, "Class metrics", false);
				XMLOutputWriter.writeSetOfMetrics(expr,
						"Class expression metrics", false);
				XMLOutputWriter.writeSetOfMetrics(objProp,
						"Object property metrics", false);
				XMLOutputWriter.writeSetOfMetrics(dataProp,
						"Data property metrics", false);
				XMLOutputWriter.writeSetOfMetrics(indis, "Individual metrics",
						false);
			}

			XMLOutputWriter.writeSetOfMetrics(axiomType, "AxiomTyoe metrics",
					false);

			if (classification) {
				for (AbstractClassificationMetric m : classificationList) {
					m.setOntology(ont);
				}
				XMLOutputWriter
						.writeClassificationSetOfMetrics(classificationList);
			}

			XMLOutputWriter.closeTag("Ontology-Metrics");

			// close xml file
			XMLOutputWriter.saveChangesAndCloseXMLFile();

			// write xls file
			XMLOutputWriter.getMetricValues();

		} catch (IllegalConfigurationException e) {
			MetricLogger.log(e.getStackTrace().toString());
			e.printStackTrace();
			unpPrinter.println(documentIRI);
		} catch (TimeOutException e) {
			MetricLogger.log(e.getStackTrace().toString());
			e.printStackTrace();
			timePrinter.println(documentIRI);
		} catch (InconsistentOntologyException e) {
			MetricLogger.log(e.getStackTrace().toString());
			e.printStackTrace();
			reasPrinter.println(documentIRI);
		} catch (ReasonerInternalException e) {
			MetricLogger.log(e.getStackTrace().toString());
			e.printStackTrace();
			reasPrinter.println(documentIRI);
		} catch (UnparsableOntologyException e) {
			MetricLogger.log(e.getStackTrace().toString());
			unpPrinter.println(documentIRI);
			e.printStackTrace();
		} catch (NullPointerException e) {
			MetricLogger.log(e.getStackTrace().toString());
			unpPrinter.println(documentIRI);
			e.printStackTrace();
		} catch (ReasonerInterruptedException e) {
			MetricLogger.log(e.getStackTrace().toString());
			timePrinter.println(documentIRI);
			e.printStackTrace();
		} catch (UnknownOWLOntologyException e) {
			MetricLogger.log(e.getStackTrace().toString());
			reasPrinter.println(documentIRI);
			e.printStackTrace();
		} catch (TimeoutException e) {
			MetricLogger.log(e.getStackTrace().toString());
			timePrinter.println(documentIRI);
			e.printStackTrace();
		} finally {
			reasPrinter.close();
			timePrinter.close();
			unpPrinter.close();
		}

		return null;
	}

	private boolean classifyWithJFactTimeOut(ThreadedReasoner treas) {
		try {
			MetricLogger.log("\n Classifying with " + treas.getReasonerName()
					+ " reasoner....");
			treas.precomputeInferences(InferenceType.CLASS_HIERARCHY,
					InferenceType.OBJECT_PROPERTY_HIERARCHY,
					InferenceType.CLASS_ASSERTIONS,
					InferenceType.OBJECT_PROPERTY_ASSERTIONS);
		} catch (TimeOutException e) {
			System.out.println("The reasoning task takes too long " + e);
			// e.printStackTrace();
			timePrinter.println(documentIRI);
			return false;
		}

		return true;

	}

	private boolean classifyWithReasoner(final OWLReasoner reasoner) {

		try {
			MetricLogger.log("\n Classifying with "
					+ reasoner.getReasonerName() + " reasoner....");
			System.out.println("\n Classifying with "
					+ reasoner.getReasonerName() + " reasoner....");
			reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY,
					InferenceType.OBJECT_PROPERTY_HIERARCHY,
					InferenceType.CLASS_ASSERTIONS,
					InferenceType.OBJECT_PROPERTY_ASSERTIONS);

		} catch (TimeOutException e) {
			System.out.println("The reasoning task takes too long " + e);
			// e.printStackTrace();
			return false;
		}

		return true;
	}

	private boolean classifyWithReasonerTimeOut(final OWLReasoner reasoner,
			PrintStream timePrinter) {

		try {
			MetricLogger.log("\n Classifying with "
					+ reasoner.getReasonerName() + " reasoner....");
			// System.out.println("\n Classifying with " +
			// reasoner.getReasonerName()
			// + " reasoner....");

			ExecutorService executor = Executors.newCachedThreadPool();
			Callable<Object> task = new Callable<Object>() {
				public Object call() {
					// method to call
					reasoner.precomputeInferences(
							InferenceType.CLASS_HIERARCHY,
							InferenceType.OBJECT_PROPERTY_HIERARCHY,
							InferenceType.CLASS_ASSERTIONS,
							InferenceType.OBJECT_PROPERTY_ASSERTIONS);
					return null;
				}
			};
			Future<Object> future = executor.submit(task);
			try {
				Object result = future.get(3, TimeUnit.MINUTES);
			} catch (TimeoutException ex) {
				System.out.println("Took too long!");
				timePrinter.println(documentIRI);
				return false;
			} catch (InterruptedException e) {
				System.out.println("Interrupted!");
				e.printStackTrace();
				timePrinter.println(documentIRI);
				return false;
			} catch (ExecutionException e) {
				e.printStackTrace();
				return false;
			} catch (java.util.concurrent.TimeoutException e) {
				System.out.println("Took too long!");
				timePrinter.println(documentIRI);
				e.printStackTrace();
				return false;
			} finally {
				// future.cancel(false); // may or may not desire this
				// return false;
			}

		} catch (TimeOutException e) {
			System.out.println("The reasoning task takes too long " + e);
			// e.printStackTrace();
			timePrinter.println(documentIRI);
			return false;
		}

		return true;
	}

	private void setOntology(List<OWLMetric> metrics) {
		for (OWLMetric m : metrics) {
			m.setOntology(ont);
		}

	}

	private void writeGeneralInfoInXML(ImportClosureSize importClosureSize) {
		importClosureSize.setOntology(ont);
		// write the general info of the ontology in the XML file
		XMLOutputWriter.startTag("General-Information");
		XMLOutputWriter.writeMetricInXML("Name", getOntologyName());
		XMLOutputWriter.writeMetricInXML("Physical IRI", documentIRI);
		XMLOutputWriter.writeMetricInXML(importClosureSize.getName(),
				importClosureSize.getValue().toString());
		XMLOutputWriter
				.writeMetricInXML("Imports", ont.getImports().toString());
		XMLOutputWriter.writeMetricInXML("Number of axioms",
				String.valueOf(ont.getAxiomCount()));

		XMLOutputWriter.closeTag("General-Information");
	}

	private List<OWLMetric> generateInferredPairedMetrics(
			OWLMetricFactory mfactory)
			throws org.semanticweb.owlapi.reasoner.TimeOutException {
		List<OWLMetric> metrics = new ArrayList<OWLMetric>();

		metrics.addAll(getClassMetrics(mfactory));
		metrics.addAll(getClassExpressionMetrics(mfactory));
		metrics.addAll(getObjectPropertyMetrics(mfactory));
		metrics.addAll(getDataPropertyMetrics(mfactory));
		metrics.addAll(getOWLIndividualMetrics(mfactory));

		return metrics;

	}

	private List<OWLMetric> getClassMetrics(OWLMetricFactory mfactory) {
		List<OWLMetric> metrics = new ArrayList<OWLMetric>();
		metrics.addAll(mfactory.getBasicClassMetrics());
		metrics.addAll(mfactory.getSubClassHierarchyMetrics());
		metrics.addAll(mfactory.getSuperClassHierarchyMetrics());
		metrics.addAll(mfactory.getClassTreeMetrics());
		metrics.addAll(mfactory.getClassUsageMetrics());
		return metrics;
	}

	private List<OWLMetric> getClassExpressionMetrics(OWLMetricFactory mfactory) {
		List<OWLMetric> metrics = new ArrayList<OWLMetric>();
		metrics.addAll(mfactory.getClassExpressionMetrics());
		return metrics;
	}

	private List<OWLMetric> getObjectPropertyMetrics(OWLMetricFactory mfactory) {
		List<OWLMetric> metrics = new ArrayList<OWLMetric>();
		metrics.addAll(mfactory.getBasicObjectPropertyMetrics());
		metrics.addAll(mfactory.getSubObjectPropertyHierarchyMetrics());
		metrics.addAll(mfactory.getSuperObjectPropertyHierarchyMetrics());
		metrics.addAll(mfactory.getObjectPropertyTreeMetrics());
		metrics.addAll(mfactory.getOWLObjectPropertUsageMetrics());
		return metrics;
	}

	private List<OWLMetric> getDataPropertyMetrics(OWLMetricFactory mfactory) {
		List<OWLMetric> metrics = new ArrayList<OWLMetric>();
		metrics.addAll(mfactory.getBasicDataPropertyMetrics());
		metrics.addAll(mfactory.getSubDataPropertyHierarchyMetrics());
		metrics.addAll(mfactory.getSuperDataPropertyHierarchyMetrics());
		metrics.addAll(mfactory.getDataPropertyTreeMetrics());
		metrics.addAll(mfactory.getOWLDataPropertUsageMetrics());
		return metrics;
	}

	private List<OWLMetric> getOWLIndividualMetrics(OWLMetricFactory mfactory) {
		List<OWLMetric> metrics = new ArrayList<OWLMetric>();

		metrics.addAll(mfactory.getBasicIndividualMetrics());
		metrics.addAll(mfactory.getOWLIndividualUsageMetrics());

		return metrics;
	}

	/**
	 * Creates the manager and load the ontology from the documentIRI
	 */
	// private void loadOntology() {
	// try {
	// manager = OWLManager.createOWLOntologyManager();
	// ont = manager.loadOntologyFromOntologyDocument(IRI.create(documentIRI));
	// System.out.println(documentIRI);
	// System.out.println(manager.getOntologies());
	// } catch (OWLOntologyCreationException e) {
	// e.printStackTrace();
	// }
	// }

	private void loadOntology() throws UnparsableOntologyException {
		LocalIRIMapper iriMapper = new LocalIRIMapper();
		manager = OWLManager.createOWLOntologyManager();

		MetricLogger.log(documentIRI);
		System.out.println(documentIRI);

		ont = iriMapper.addGeneralImports(manager, documentIRI);

		MetricLogger.log(manager.getOntologies().toString());
		System.out.println(manager.getOntologies());
	}

	/**
	 * Separates the name of the ontology from the complete IRI of the ontology
	 * 
	 * @return a string, which is the name of the ontology
	 */
	private String getOntologyName() {
		String ontoName = documentIRI
				.substring(documentIRI.lastIndexOf("/") + 1);

		return ontoName;

	}// getOntologyName()

	public void setReasonerTimeOut(String time) {
		if (time != null) {
			reasonerTimeOut = Long.parseLong(time);
		}

	}

	/**
	 * Creates a Hermit, Factplusplus or Pellet reasoner
	 * 
	 * @param reasonerName
	 * @return
	 */
	private OWLReasoner createReasoner(String reasonerName)
			throws ReasonerInternalException, TimeOutException,
			TimeoutException, InternalReasonerException {

		// Create a console progress monitor
		ConsoleProgressMonitor progressMonitor = new ConsoleProgressMonitor();
		progressMonitor.reasonerTaskStarted(ConsoleProgressMonitor.LOADING);
		progressMonitor.reasonerTaskStopped();
		// configuration of the monitor
		// OWLReasonerConfiguration config = new SimpleConfiguration(
		// progressMonitor);

		// put a timer
		OWLReasonerConfiguration config = new SimpleConfiguration(
				reasonerTimeOut);
		System.out.println(config.getTimeOut());
		
		OWL2ELProfile elProfile = new OWL2ELProfile();
		

		if (elProfile.checkOntology(ont).isInProfile()) {
			reasonerFactory = new ElkReasonerFactory();
		} else if (reasonerName.equals("Hermit")) {
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

	static private PrintStream openTextFile(String filename) {
		FileOutputStream out; // declare a file output object
		PrintStream p = null; // declare a print stream object

		try {
			// Create a new file output stream
			out = new FileOutputStream(filename, true);

			// Connect print stream to the output stream
			p = new PrintStream(out);

			// p.println("This is written to a file");

		} catch (Exception e) {
			System.err.println("Error writing to file");
		}

		return p;
	}

}
