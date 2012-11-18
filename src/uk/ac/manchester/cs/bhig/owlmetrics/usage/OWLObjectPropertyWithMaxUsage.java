package uk.ac.manchester.cs.bhig.owlmetrics.usage;

import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class OWLObjectPropertyWithMaxUsage extends OWLObjectPropertyUsageCount {

	public OWLObjectPropertyWithMaxUsage(OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		return "OWLObjectProperty with max usage";
	}

	@Override
	protected OWLObjectProperty recomputeMetric() {
		super.computeOWLOntologyObjectPropertyUsage();
		return super.getObjectPropertyWithMaxUsage();
	}

	@Override
	protected void disposeMetric() {
		// TODO Auto-generated method stub

	}

}
