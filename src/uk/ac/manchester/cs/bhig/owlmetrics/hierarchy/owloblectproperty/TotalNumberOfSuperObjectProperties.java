package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owloblectproperty;

import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

public class TotalNumberOfSuperObjectProperties extends OWLSuperObjectPropertyCount {

	public TotalNumberOfSuperObjectProperties(OWLOntologyManager owlOntologyManager,
			OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		return "Total number of SuperObjectProperties";
	}

	@Override
	protected Integer recomputeMetric() {
		super.computeTotalSuperObjectPropertyMetrics(getOntology());
		return super.getSumOfSuperObjectProperties();
	}

	@Override
	protected void disposeMetric() {
		// TODO Auto-generated method stub

	}


}
