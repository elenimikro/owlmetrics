package uk.ac.manchester.cs.bhig.owlmetrics.usage;

import org.semanticweb.owlapi.model.OWLOntologyManager;

public class MaxOWLObjectPropertyUsage extends OWLObjectPropertyUsageCount {

	public MaxOWLObjectPropertyUsage(OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
	}

	@Override
	public String getName() {
		return "Max OWLObjectProperty usage";
	}

	@Override
	protected Integer recomputeMetric() {
		super.computeOWLOntologyObjectPropertyUsage();
		return super.getMaxOWLObjectPropertyUsage();
	}


}
