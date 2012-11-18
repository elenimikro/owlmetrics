package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy;

import java.util.ArrayList;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

public class OWLHierarchyMetrics {
	
	private int sum;
	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	private int pathNo;
	private int max;
	private ArrayList<Integer> medianDepthSet = new ArrayList<Integer>();
	//private HashSet<OWLObject> leafNodeSet = new HashSet<OWLObject>();
	
	private OWLObject maxDepthObject;
	
	private OWLReasoner reasoner;

	public OWLHierarchyMetrics(OWLReasoner reasoner) {
		this.reasoner = reasoner;
	}
	
	/**
	 * It performs a top down traverse of the hierarchy
	 * Used for computing the depth of the hierarchy
	 * 
	 * @param parent
	 * @param depth
	 */
	public void topDownTraverse(Node<? extends OWLObject> parent, int depth){
		if(depth==0)
			medianDepthSet.clear();
		
		if(parent.isBottomNode()){
			pathNo++;
			//add the depth in the median set to use it later
			medianDepthSet.add(depth-1);
			sum += depth-1;
			return;
		}
		else if(getSubNodes(parent.getRepresentativeElement(), true) == null ){
			pathNo++;
			//add the depth in the median set for later
			medianDepthSet.add(depth);
			sum += depth;
			return;
		}
		
		//check for greater value
		compareToMaxValue(depth, parent.getRepresentativeElement());
		
		for(Node<?> child : getSubNodes(parent.getRepresentativeElement(), true)){
			topDownTraverse(child, depth + 1);
		}
		
	}
	
	public NodeSet<?> getSubNodes(OWLObject parent, boolean directOf){
		if(parent!=null){
			if(!parent.getClassesInSignature().isEmpty()){
				return (NodeSet<?>) reasoner.getSubClasses((OWLClassExpression) parent, directOf);
			}
			else if(!parent.getObjectPropertiesInSignature().isEmpty())
				
				return (NodeSet<?>) reasoner.getSubObjectProperties((OWLObjectPropertyExpression) parent, directOf);
			else if(!parent.getDataPropertiesInSignature().isEmpty())
				return (NodeSet<?>) reasoner.getSubDataProperties((OWLDataProperty) parent, directOf);
			else	
				return null;
		}
		return null;
	}
	
	public NodeSet<?> getSuperNodes(OWLObject parent, boolean directOf){
		if(parent!=null){
			if(!parent.getClassesInSignature().isEmpty()){
				return (NodeSet<?>) reasoner.getSuperClasses((OWLClassExpression) parent, directOf);
			}
			else if(!parent.getObjectPropertiesInSignature().isEmpty())
				
				return (NodeSet<?>) reasoner.getSuperObjectProperties((OWLObjectPropertyExpression) parent, directOf);
			else if(!parent.getDataPropertiesInSignature().isEmpty())
				return (NodeSet<?>) reasoner.getSuperDataProperties((OWLDataProperty) parent, directOf);
			else	
				return null;
		}
		return null;
	}
	
	private int compareToMaxValue(int noOfElements, OWLObject currentEntity){
		if (max < noOfElements) {
			max = noOfElements;
			setOWLObjectWithMaxDepth(currentEntity);
		}
		return noOfElements;
	}

	private void setOWLObjectWithMaxDepth(OWLObject maxDepthObject) {
		this.maxDepthObject = maxDepthObject;
	}

	protected OWLObject getOWLObjectWithMaxDepth() {
		return maxDepthObject;
	}
	
	protected int getSumOfDephts(){
		return sum;
	}
	
	protected int getNumberOfPaths(){
		return pathNo;
	}
	
	protected int getMaxDepth(){
		return max;
	}
	
	
	public void setReasoner(OWLReasoner reasoner) {
		this.reasoner = reasoner;
	}

}
