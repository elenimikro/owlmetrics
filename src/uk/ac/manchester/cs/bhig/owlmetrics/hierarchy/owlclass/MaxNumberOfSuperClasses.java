package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owlclass;

import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

public class MaxNumberOfSuperClasses extends OWLSuperClassCount {

	public MaxNumberOfSuperClasses(OWLOntologyManager owlOntologyManager,
			OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		return "Max number of SuperClasses";
	}

	@Override
	protected Integer recomputeMetric() {
		super.computeTotalSuperClassMetrics(getOntology());
		return super.getMaxNumberOfSuperClasses();
	}

	@Override
	protected void disposeMetric() {

	}

}
