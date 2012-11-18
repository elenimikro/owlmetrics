package uk.ac.manchester.cs.bhig.owlmetrics.usage;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class OWLClassWithMaxUsage extends OWLClassUsageCount {

	public OWLClassWithMaxUsage(OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		return "OWLClass with max usage";
	}

	@Override
	protected OWLClass recomputeMetric() {
		super.computeOWLOntologyClassUsage();
		return super.getClassWithMaxUsage();
	}

	

}
