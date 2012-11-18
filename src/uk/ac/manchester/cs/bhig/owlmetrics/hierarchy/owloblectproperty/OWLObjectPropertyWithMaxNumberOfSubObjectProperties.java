package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owloblectproperty;

import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

public class OWLObjectPropertyWithMaxNumberOfSubObjectProperties extends OWLSubObjectPropertyCount {

	public OWLObjectPropertyWithMaxNumberOfSubObjectProperties(
			OWLOntologyManager owlOntologyManager, OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		return "OWLObjectProperty with max number of SubObjectProperties";
	}

	@Override
	protected OWLObjectProperty recomputeMetric() {
		super.computeTotalSubObjectPropertyMetrics(super.getOntologies());
		return super.getOWLObjectPropertyWithMaxNumberOfSubObjectProperties();
	}

	@Override
	protected void disposeMetric() {
		// TODO Auto-generated method stub
	}

}
