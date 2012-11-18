package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owldataproperty;

import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

public class TotalNumberOfSuperDataProperties extends OWLSuperDataPropertyCount {

	public TotalNumberOfSuperDataProperties(OWLOntologyManager owlOntologyManager,
			OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		return "Total number of SuperDataProperties";
	}

	@Override
	protected Integer recomputeMetric() {
		super.computeTotalSuperDataPropertyMetrics(getOntology());
		return super.getSumOfSuperDataProperties();
	}

	@Override
	protected void disposeMetric() {
		// TODO Auto-generated method stub
	}


}
