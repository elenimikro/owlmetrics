package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owlclass;

import java.util.List;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.OWLHierarchyDepthMetrics;

public class SumOfDepthsInOWLClassHierarchy extends OWLHierarchyDepthMetrics {
	
	private OWLReasoner reasoner;

	public SumOfDepthsInOWLClassHierarchy(OWLOntologyManager owlOntologyManager,
			OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
		
		this.reasoner = reasoner;
	}

	@Override
	public String getName() {
		return "Sum of class hierarchy depths";
	}

	@Override
	protected Integer recomputeMetric() {
		super.initialise();
		computeClassHierarchyDepthMetrics();
		return super.getSumOfDepths();
	}

	@Override
	public boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		return super.isMetricInvalidated(changes);
	}

	@Override
	protected void disposeMetric() {
		// TODO Auto-generated method stub

	}

	private void computeClassHierarchyDepthMetrics(){
		Node<OWLClass> topNode = reasoner.getTopClassNode();
		
		topDownTraverse(topNode, 0);
	}
}
