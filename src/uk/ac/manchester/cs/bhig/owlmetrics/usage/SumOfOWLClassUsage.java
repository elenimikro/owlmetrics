package uk.ac.manchester.cs.bhig.owlmetrics.usage;

import java.util.List;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;


public class SumOfOWLClassUsage extends OWLClassUsageCount {

	public SumOfOWLClassUsage(OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
	}

	@Override
	public String getName() {
		return "Sum of OWLClass usage";
	}

	@Override
	protected Integer recomputeMetric() {
		int sum = 0;
		for(OWLOntology ont : super.getOntologies()){
			for(OWLClass cls : ont.getClassesInSignature()){
				sum += getOWLClassUsage(cls);
			}
		}
		return sum;
	}

	@Override
	protected boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		return super.isMetricInvalidated(changes);
	}



}
