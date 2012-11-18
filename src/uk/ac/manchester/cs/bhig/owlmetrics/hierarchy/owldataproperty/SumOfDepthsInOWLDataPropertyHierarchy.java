package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owldataproperty;

import java.util.List;

import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.OWLHierarchyDepthMetrics;

public class SumOfDepthsInOWLDataPropertyHierarchy extends OWLHierarchyDepthMetrics {
	
	private OWLReasoner reasoner;

	public SumOfDepthsInOWLDataPropertyHierarchy(OWLOntologyManager owlOntologyManager,
			OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
		
		this.reasoner = reasoner;
	}

	@Override
	public String getName() {
		return "Sum of data property hierarchy depths";
	}

	@Override
	protected Integer recomputeMetric() {
		super.initialise();
		computeDataPropertyHierarchyDepthMetrics();
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

	private void computeDataPropertyHierarchyDepthMetrics(){
		Node<OWLDataProperty> topNode = reasoner.getTopDataPropertyNode();
		
		topDownTraverse(topNode, 0);
	}
}
