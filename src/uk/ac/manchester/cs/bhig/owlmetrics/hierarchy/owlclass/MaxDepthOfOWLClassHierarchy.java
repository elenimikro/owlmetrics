package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owlclass;

import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

public class MaxDepthOfOWLClassHierarchy extends SumOfDepthsInOWLClassHierarchy {

	public MaxDepthOfOWLClassHierarchy(OWLOntologyManager owlOntologyManager,
			OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		return "Max depth of class hierarchy";
	}

	@Override
	protected Integer recomputeMetric() {
		super.recomputeMetric();
		return super.getMaxDepth();
	}

	@Override
	protected void disposeMetric() {
		// TODO Auto-generated method stub
	}

}
