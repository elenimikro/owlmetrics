package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owldataproperty;

import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

public class OWLDataPropertyWithMaxNumberOfSuperDataProperties extends OWLSuperDataPropertyCount {

	public OWLDataPropertyWithMaxNumberOfSuperDataProperties(
			OWLOntologyManager owlOntologyManager, OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		return "OWLDataProperty with max number of SuperDataProperties";
	}

	@Override
	protected OWLDataProperty recomputeMetric() {
		super.computeTotalSuperDataPropertyMetrics(getOntology());
		return super.getOWLDataPropertyWithMaxNumberOfSuperDataProperties();
	}

	@Override
	protected void disposeMetric() {
		// TODO Auto-generated method stub

	}

}
