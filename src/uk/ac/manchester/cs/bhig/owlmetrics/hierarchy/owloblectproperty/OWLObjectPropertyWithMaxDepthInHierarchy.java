package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owloblectproperty;

import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.OWLHierarchyDepthMetrics;

public class OWLObjectPropertyWithMaxDepthInHierarchy extends OWLHierarchyDepthMetrics {
	
	private OWLReasoner reasoner;

	public OWLObjectPropertyWithMaxDepthInHierarchy(
			OWLOntologyManager owlOntologyManager, OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
		this.reasoner = reasoner;
	}

	public String getName(){
		return "ObjectProperty with max depth in hierarchy";
	}
	
	public OWLObjectPropertyExpression recomputeMetric(){
		computeObjectPropertyHierarchyDepthMetrics();
		return (OWLObjectPropertyExpression) super.getOWLObjectWithMaxDepth();
	}
	
	private void computeObjectPropertyHierarchyDepthMetrics(){
		Node<OWLObjectPropertyExpression> topNode = reasoner.getTopObjectPropertyNode();
		
		super.topDownTraverse(topNode, 0);
	}

	@Override
	protected void disposeMetric() {
		// TODO Auto-generated method stub
		
	}
}
