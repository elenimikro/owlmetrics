package uk.ac.manchester.cs.bhig.owlmetrics.tests;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.semanticweb.owlapi.metrics.OWLMetric;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

import uk.ac.manchester.cs.bhig.owlmetrics.entailments.OWLSubClassEntailmentCount;
import uk.ac.manchester.cs.bhig.owlmetrics.utilities.ManchesterSyntaxRenderer;
import uk.ac.manchester.cs.bhig.owlmetrics.utilities.OWLExtendedMetricManager;

public class EntailmentsTest {
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		OWLOntologyLoader l = new OWLOntologyLoader(args[0]);
		l.setReasoner("Hermit");

		l.getReasoner().precomputeInferences();
		List<OWLMetric<?>> metrics = new ArrayList<OWLMetric<?>>();
		
		OWLSubClassEntailmentCount subs = new OWLSubClassEntailmentCount(l.getManager(), l.getReasoner());
		HashSet<OWLSubClassOfAxiom> entSet = subs.getOWLSubClassOfEntailments();
		ManchesterSyntaxRenderer render = new ManchesterSyntaxRenderer();
		
		int count=1;
		for(OWLSubClassOfAxiom ax : entSet){
			System.out.println(count + ") " + render.render(ax));
			count++;
		}
		
		metrics.add(subs);
		
		OWLExtendedMetricManager manager = new OWLExtendedMetricManager(metrics);
		manager.setOntology(l.getOntology());
		
		System.out.println(manager.toString());
	}

}
