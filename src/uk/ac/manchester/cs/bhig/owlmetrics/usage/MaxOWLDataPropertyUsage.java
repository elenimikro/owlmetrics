package uk.ac.manchester.cs.bhig.owlmetrics.usage;

import org.semanticweb.owlapi.model.OWLOntologyManager;

public class MaxOWLDataPropertyUsage extends OWLDataPropertyUsageCount {

	public MaxOWLDataPropertyUsage(OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
	}

	@Override
	public String getName() {
		return "Max OWLDataProperty usage";
	}

	@Override
	protected Integer recomputeMetric() {
		super.computeOWLOntologyDataPropertyUsage();
		return super.getMaxOWLDataPropertyUsage();
	}
	

}
