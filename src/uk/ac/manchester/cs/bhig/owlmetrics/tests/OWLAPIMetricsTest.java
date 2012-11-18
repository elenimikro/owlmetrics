package uk.ac.manchester.cs.bhig.owlmetrics.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.metrics.AxiomTypeCountMetricFactory;
import org.semanticweb.owlapi.metrics.DLExpressivity;
import org.semanticweb.owlapi.metrics.GCICount;
import org.semanticweb.owlapi.metrics.HiddenGCICount;
import org.semanticweb.owlapi.metrics.ImportClosureSize;
import org.semanticweb.owlapi.metrics.LogicalAxiomCount;
import org.semanticweb.owlapi.metrics.MaximumNumberOfNamedSuperclasses;
import org.semanticweb.owlapi.metrics.NumberOfClassesWithMultipleInheritance;
import org.semanticweb.owlapi.metrics.OWLMetric;

import uk.ac.manchester.cs.bhig.owlmetrics.utilities.OWLExtendedMetricManager;

public class OWLAPIMetricsTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		OWLOntologyLoader l = new OWLOntologyLoader(args[0]);

		List<OWLMetric<?>> metrics = new ArrayList<OWLMetric<?>>();

		metrics.add(new DLExpressivity(l.getManager()));
		metrics.add(new GCICount(l.getManager()));
		metrics.add(new ImportClosureSize(l.getManager()));
		metrics.add(new HiddenGCICount(l.getManager()));
		metrics.add(new LogicalAxiomCount(l.getManager()));
		metrics.add(new MaximumNumberOfNamedSuperclasses(l.getManager()));
		metrics.add(new NumberOfClassesWithMultipleInheritance(l.getManager()));

		Set<OWLMetric<?>> axiomTypeMetrics = AxiomTypeCountMetricFactory
				.createMetrics(l.getManager());
		metrics.addAll(axiomTypeMetrics);

		OWLExtendedMetricManager manager = new OWLExtendedMetricManager(metrics);
		manager.setOntology(l.getOntology());

		System.out.println(manager.toString());

	}

}
