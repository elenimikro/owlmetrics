package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owlclass;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

public class OWLClassWithMaxNumberOfSubClasses extends OWLSubClassCount {

	public OWLClassWithMaxNumberOfSubClasses(
			OWLOntologyManager owlOntologyManager, OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		return "OWLClass with max number of SubClasses";
	}

	@Override
	protected OWLClass recomputeMetric() {
		super.computeTotalSubClassMetrics(super.getOntologies());
		return super.getOWLClassWithMaxNumberOfSubClasses();
	}

	@Override
	protected void disposeMetric() {
		// TODO Auto-generated method stub
	}

}
