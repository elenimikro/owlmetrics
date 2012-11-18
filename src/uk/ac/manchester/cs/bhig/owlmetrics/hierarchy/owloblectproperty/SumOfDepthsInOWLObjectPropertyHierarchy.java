package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owloblectproperty;

import java.util.List;

import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.OWLHierarchyDepthMetrics;

public class SumOfDepthsInOWLObjectPropertyHierarchy extends OWLHierarchyDepthMetrics {
	
	private OWLReasoner reasoner;

	public SumOfDepthsInOWLObjectPropertyHierarchy(OWLOntologyManager owlOntologyManager,
			OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
		
		this.reasoner = reasoner;
	}

	@Override
	public String getName() {
		return "Sum of object property hierarchy depths";
	}

	@Override
	protected Integer recomputeMetric() {
		super.initialise();
		computeObjectPropertyHierarchyDepthMetrics();
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

	private void computeObjectPropertyHierarchyDepthMetrics(){
		Node<OWLObjectPropertyExpression> topNode = reasoner.getTopObjectPropertyNode();
		
		topDownTraverse(topNode, 0);
	}
}
