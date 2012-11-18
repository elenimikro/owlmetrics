package uk.ac.manchester.cs.bhig.owlmetrics.usage;

import java.util.List;

import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;


public class SumOfOWLIndividualUsage extends OWLIndividualUsageCount {

	public SumOfOWLIndividualUsage(OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		return "Sum of OWLIndividual usage";
	}

	@Override
	protected Integer recomputeMetric() {
		int sum = 0;
		for(OWLOntology ont : super.getOntologies()){
			for(OWLNamedIndividual indi : ont.getIndividualsInSignature()){
				sum += super.getOWLIndividualUsage(indi);
			}
		}
		return sum;
	}

	@Override
	protected boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		return super.isMetricInvalidated(changes);
	}

	@Override
	protected void disposeMetric() {
		
	}

}
