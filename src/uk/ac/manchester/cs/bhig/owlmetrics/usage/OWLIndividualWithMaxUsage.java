package uk.ac.manchester.cs.bhig.owlmetrics.usage;

import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class OWLIndividualWithMaxUsage extends OWLIndividualUsageCount {

	public OWLIndividualWithMaxUsage(OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		return "OWLIndividual with max usage";
	}

	@Override
	protected OWLIndividual recomputeMetric() {
		super.computeOWLOntologyIndividualUsage();
		return super.getIndividualWithMaxUsage();
	}

	@Override
	protected void disposeMetric() {
		// TODO Auto-generated method stub

	}

}
