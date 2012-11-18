package uk.ac.manchester.cs.bhig.owlmetrics.usage;

import org.semanticweb.owlapi.model.OWLOntologyManager;

public class MaxOWLClassUsage extends OWLClassUsageCount {

	public MaxOWLClassUsage(OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		return "Max OWLClass usage";
	}

	@Override
	protected Integer recomputeMetric() {
		super.computeOWLOntologyClassUsage();
		return super.getMaxClassUsage();
	}

	

}
