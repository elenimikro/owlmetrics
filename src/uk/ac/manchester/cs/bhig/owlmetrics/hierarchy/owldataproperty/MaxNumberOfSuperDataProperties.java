package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owldataproperty;

import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

public class MaxNumberOfSuperDataProperties extends OWLSuperDataPropertyCount {

	public MaxNumberOfSuperDataProperties(OWLOntologyManager owlOntologyManager,
			OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		return "Max number of Super DataProperties";
	}

	@Override
	protected Integer recomputeMetric() {
		super.computeTotalSuperDataPropertyMetrics(getOntology());
		return super.getMaxNumberOfSuperDataProperties();
	}

	

}
