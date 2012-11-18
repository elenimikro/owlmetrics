package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owlclass;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.OWLHierarchyDepthMetrics;

public class OWLClassWithMaxDepthInHierarchy extends OWLHierarchyDepthMetrics {
	
	private OWLReasoner reasoner;

	public OWLClassWithMaxDepthInHierarchy(
			OWLOntologyManager owlOntologyManager, OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
		this.reasoner = reasoner;
	}

	public String getName(){
		return "Class with max depth in hierarchy";
	}
	
	public OWLClass recomputeMetric(){
		computeClassHierarchyDepthMetrics();
		return (OWLClass) super.getOWLObjectWithMaxDepth();
	}
	
	private void computeClassHierarchyDepthMetrics(){
		Node<OWLClass> topNode = reasoner.getTopClassNode();
		
		super.topDownTraverse(topNode, 0);
	}

	@Override
	protected void disposeMetric() {
		
	}
	
}
