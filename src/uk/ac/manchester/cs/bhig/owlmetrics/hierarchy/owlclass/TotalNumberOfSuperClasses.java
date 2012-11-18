package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owlclass;

import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

public class TotalNumberOfSuperClasses extends OWLSuperClassCount {

	public TotalNumberOfSuperClasses(OWLOntologyManager owlOntologyManager,
			OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		return "Total number of SuperClasses";
	}

	@Override
	protected Integer recomputeMetric() {
		super.computeTotalSuperClassMetrics(getOntology());
		return super.getSumOfSuperClasses();
	}

	@Override
	protected void disposeMetric() {
		// TODO Auto-generated method stub

	}


}
