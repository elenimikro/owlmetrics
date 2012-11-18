package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owldataproperty;

import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.OWLHierarchyDepthMetrics;

public class OWLDataPropertyWithMaxDepthInHierarchy extends OWLHierarchyDepthMetrics {
	
	private OWLReasoner reasoner;

	public OWLDataPropertyWithMaxDepthInHierarchy(
			OWLOntologyManager owlOntologyManager, OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
		this.reasoner = reasoner;
	}

	public String getName(){
		return "DataProperty with max depth in hierarchy";
	}
	
	public OWLDataProperty recomputeMetric(){
		computeObjectPropertyHierarchyDepthMetrics();
		return (OWLDataProperty) super.getOWLObjectWithMaxDepth();
	}
	
	private void computeObjectPropertyHierarchyDepthMetrics(){
		Node<OWLDataProperty> topNode = reasoner.getTopDataPropertyNode();
		
		super.topDownTraverse(topNode, 0);
	}

	@Override
	protected void disposeMetric() {
		// TODO Auto-generated method stub
		
	}
}
