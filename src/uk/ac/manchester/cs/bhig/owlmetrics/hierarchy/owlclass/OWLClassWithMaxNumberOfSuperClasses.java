package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owlclass;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

public class OWLClassWithMaxNumberOfSuperClasses extends OWLSuperClassCount {

	public OWLClassWithMaxNumberOfSuperClasses(
			OWLOntologyManager owlOntologyManager, OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		return "OWLClass with max number of SuperClasses";
	}

	@Override
	protected OWLClass recomputeMetric() {
		super.computeTotalSuperClassMetrics(getOntology());
		return super.getOWClassWithMaxNumberOfSuperClasses();
	}

	@Override
	protected void disposeMetric() {
		// TODO Auto-generated method stub

	}

}
