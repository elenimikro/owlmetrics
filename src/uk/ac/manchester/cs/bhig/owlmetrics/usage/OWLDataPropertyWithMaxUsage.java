package uk.ac.manchester.cs.bhig.owlmetrics.usage;

import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class OWLDataPropertyWithMaxUsage extends OWLDataPropertyUsageCount {

	public OWLDataPropertyWithMaxUsage(OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		return "OWLDataProperty with max usage";
	}

	@Override
	protected OWLDataProperty recomputeMetric() {
		super.computeOWLOntologyDataPropertyUsage();
		return super.getDataPropertyWithMaxUsage();
	}

	@Override
	protected void disposeMetric() {
		// TODO Auto-generated method stub

	}

}
