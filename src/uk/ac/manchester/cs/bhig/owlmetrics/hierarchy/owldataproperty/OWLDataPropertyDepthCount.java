package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owldataproperty;

import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.OWLHierarchyParser;

public abstract class OWLDataPropertyDepthCount extends OWLHierarchyParser {

	OWLReasoner reasoner;
	public OWLDataPropertyDepthCount(OWLOntologyManager owlOntologyManager,
			OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
		
		this.reasoner = reasoner;
	}
	
	protected int getDepthOfOWLDataProperty(OWLDataProperty givenProp){
		Node<OWLObjectPropertyExpression> topNode = reasoner.getTopObjectPropertyNode();
		int result = topDownTraverse(topNode, 0, givenProp);
		
		return result;
	}

	/**
	 * It performs a top down traverse of the hierarchy
	 * Used for computing the depth of the hierarchy
	 * 
	 * @param parent
	 * @param depth
	 */
	private int topDownTraverse(Node<? extends OWLObject> parent, int depth, OWLDataProperty givenProp){

		if(parent.getEntities().contains(givenProp))
			return depth;
		else
			for(Node<?> child : getSubNodes(parent.getRepresentativeElement(), true)){
				topDownTraverse(child, depth + 1, givenProp);
			}
		return depth;
	}

}
