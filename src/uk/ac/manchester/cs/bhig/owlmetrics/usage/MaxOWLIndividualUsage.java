package uk.ac.manchester.cs.bhig.owlmetrics.usage;

import org.semanticweb.owlapi.model.OWLOntologyManager;

public class MaxOWLIndividualUsage extends OWLIndividualUsageCount {

	public MaxOWLIndividualUsage(OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
	}

	@Override
	public String getName() {
		return "Max OWLIndividual usage";
	}

	@Override
	protected Integer recomputeMetric() {
		super.computeOWLOntologyIndividualUsage();
		return super.getMaxOWLIndividualUsage();
	}


}
