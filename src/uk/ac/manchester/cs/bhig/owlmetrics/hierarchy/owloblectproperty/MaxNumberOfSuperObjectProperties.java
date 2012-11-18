package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owloblectproperty;

import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

public class MaxNumberOfSuperObjectProperties extends OWLSuperObjectPropertyCount {

	public MaxNumberOfSuperObjectProperties(OWLOntologyManager owlOntologyManager,
			OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		return "Max number of ObjectProperties";
	}

	@Override
	protected Integer recomputeMetric() {
		super.computeTotalSuperObjectPropertyMetrics(getOntology());
		return super.getMaxNumberOfSuperObjectProperties();
	}

	@Override
	protected void disposeMetric() {
		// TODO Auto-generated method stub

	}

}
