package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owlclass;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.OWLHierarchyParser;

public abstract class OWLClassDepthCount extends OWLHierarchyParser {
	

	private OWLReasoner reasoner;
	
	public OWLClassDepthCount(OWLOntologyManager owlOntologyManager,
			OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
		
		this.reasoner = reasoner;
	}
	
	protected int getDepthOfOWLClass(OWLClass givenCls){
		Node<OWLClass> topNode = reasoner.getTopClassNode();
		int result = topDownTraverse(topNode, 0, givenCls);
		
		return result;
	}

	/**
	 * It performs a top down traverse of the hierarchy
	 * Used for computing the depth of the hierarchy
	 * 
	 * @param parent
	 * @param depth
	 */
	private int topDownTraverse(Node<? extends OWLObject> parent, int depth, OWLClass givenCls){

		if(parent.getEntities().contains(givenCls))
			return depth;
		else
			for(Node<?> child : getSubNodes(parent.getRepresentativeElement(), true)){
				topDownTraverse(child, depth + 1, givenCls);
			}
		
		return depth;
	}

}
