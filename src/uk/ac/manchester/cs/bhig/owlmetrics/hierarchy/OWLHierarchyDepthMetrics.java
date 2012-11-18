package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import uk.ac.manchester.cs.bhig.owlmetrics.ValueComparator;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owlclass.OWLClassDepthCount;

public abstract class OWLHierarchyDepthMetrics extends OWLClassDepthCount{
	
	private int pathNo;
	private int max;
	private int sum;
	private ArrayList<Integer> depthValues = new ArrayList<Integer>();
	private OWLObject maxDepthObject;
	
	//structure for keeping the entity and its depth
	HashMap<OWLObject, Integer> axiomsMap = new HashMap<OWLObject, Integer>();

	public OWLHierarchyDepthMetrics(OWLOntologyManager owlOntologyManager,
			OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
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
			depthValues.clear();
		
		if(parent.isBottomNode()){
			pathNo++;
			//add the depth in the median set to use it later
			depthValues.add(depth-1);
			sum += depth-1;
			return;
		}
		else if(getSubNodes(parent.getRepresentativeElement(), true) == null ){
			pathNo++;
			//add the depth in the median set for later
			depthValues.add(depth);
			sum += depth;
			return;
		}
		
		//keep the depth
		axiomsMap.put(parent.getRepresentativeElement(), depth);
		
		//check for greater value
		if (max < depth) {
			max = depth;
			maxDepthObject = parent.getRepresentativeElement();
		}
		
		for(Node<?> child : getSubNodes(parent.getRepresentativeElement(), true)){
			topDownTraverse(child, depth + 1);
		}
	}
	
	protected void initialise(){
		pathNo = 0;
		max = 0;
		sum = 0;
		maxDepthObject = null;
		depthValues.clear();
	}
	
	protected OWLObject getOWLObjectWithMaxDepth(){
		return maxDepthObject;
	}
	
	protected int getMaxDepth(){
		return max;
	}
	
	protected int getSumOfDepths(){
		return sum;
	}
	
	protected int getNumberOfPaths(){
		return pathNo;
	}
	
	protected ArrayList<Integer> getDepthValues(){
		return depthValues;
	}
	
	
	public LinkedHashMap<OWLObject, Integer> getEntitiesWithValue(){
		
		return ValueComparator.sortHashMapByValues(axiomsMap);
	}
	


}
