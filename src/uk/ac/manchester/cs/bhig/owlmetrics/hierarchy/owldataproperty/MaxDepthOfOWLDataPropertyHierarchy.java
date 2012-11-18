package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owldataproperty;

import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

public class MaxDepthOfOWLDataPropertyHierarchy extends SumOfDepthsInOWLDataPropertyHierarchy {

	public MaxDepthOfOWLDataPropertyHierarchy(OWLOntologyManager owlOntologyManager,
			OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		return "Max depth of data property hierarchy";
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
