package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy;

import java.util.ArrayList;
import java.util.HashSet;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

public class OWLClassHierarchyMetrics<M> extends OWLHierarchyMetrics{
	
	private ArrayList<Integer> medianSet = new ArrayList<Integer>();
	private HierarchyMetricGenerator subMetrics;
	private HierarchyMetricGenerator superMetrics;
	private HashSet<OWLObject> leafSet = new HashSet<OWLObject>();
	
	private OWLReasoner reasoner;
	
	public OWLClassHierarchyMetrics(OWLReasoner reasoner, OWLOntology ontology) {
		super(reasoner);
		this.reasoner = reasoner;
		
		subMetrics = computeTotalSubClassMetrics(ontology);
		superMetrics = computeTotalSuperClassMetrics(ontology);
		computeClassHierarchyDepthMetrics();
	}
	
	private void computeClassHierarchyDepthMetrics(){
		Node<OWLClass> topNode = reasoner.getTopClassNode();
		
		topDownTraverse(topNode, 0);
	}
	
	public int getTotalNumberOfSubClassesInOntology(){
		return subMetrics.getSum();
	}
	
	public int getMaximumlNumberOfSubClasses(){
		return subMetrics.getMaxValue();
	}
	
	public OWLClass getClassWithMaxNumberOfSubClasses(){
		return (OWLClass) subMetrics.getEntityWithMaxNo();
	}
	
	public int getTotalNumberOfSuperClassesInOntology(){
		return superMetrics.getSum();
	}
	
	public int getMaximumlNumberOfSuperClasses(){
		return superMetrics.getMaxValue();
	}
	
	public OWLClass getClassWithMaxNumberOfSuperClasses(){
		return (OWLClass) superMetrics.getEntityWithMaxNo();
	}
	
	public HashSet<OWLObject> getLeafEntities(){
		return leafSet;
	}
	
	public int getClassHierarchyMaxDepth(){
		return getMaxDepth();
	}
	
	public int getNumberOfPathsInClassHierarchy(){
		return getNumberOfPaths();
	}
	
	public int getSumOfDepthsInClassHierarchy(){
		return getSumOfDephts();
	}

	private HierarchyMetricGenerator computeTotalSubClassMetrics(OWLOntology ont){
		medianSet.clear();
		int sum=0;
		HierarchyMetricGenerator gen = new HierarchyMetricGenerator();
		for(OWLClass parent : ont.getClassesInSignature()){
			int noOfEntities = getNumberOfSubClasses(parent, true);
				
			medianSet.add(noOfEntities);
			sum += noOfEntities;
			gen.compareToMaxValue(noOfEntities, parent);
		}
		
		gen.setSum(sum);
		return gen;
		
	}
	
	private HierarchyMetricGenerator computeTotalSuperClassMetrics(OWLOntology ont){
		int sum = 0;
		HierarchyMetricGenerator gen = new HierarchyMetricGenerator();
		
		for(OWLClass child : ont.getClassesInSignature()){
			int noOfEntities = getNumberOfSuperClasses(child, true);
			
			sum += noOfEntities;
			gen.compareToMaxValue(noOfEntities, child);

		}
		gen.setSum(sum);
		
		return gen;
	}
	
	public int getNumberOfSubClasses(OWLClass parent, boolean directOf){
		int number = 0;
		
		NodeSet<? extends OWLObject> childrenSet = getSubNodes(parent, directOf);
		if(!childrenSet.getNodes().contains(reasoner.getBottomClassNode()) 
				&& !childrenSet.isEmpty() && childrenSet != null){
			number = childrenSet.getFlattened().size();
		}
		else{
			leafSet.add(parent);
		}
		
		return number;
	}
	
	
	public int getNumberOfSuperClasses(OWLClass child, boolean directOf){
		int number = 0;
		
		NodeSet<? extends OWLObject> parentSet = getSuperNodes(child, directOf);
		if(!parentSet.getNodes().contains(reasoner.getBottomClassNode()) 
				&& !parentSet.isEmpty()){
			number = parentSet.getFlattened().size();
		}
		
		return number;
	}
	

	
/*****************************************************************************/	
	
	private class HierarchyMetricGenerator{
		private int max;
		private int sum;
		private OWLObject entityWithMaxNo;
		
		
		protected int compareToMaxValue(int noOfElements, OWLObject currentEntity){
			if (max < noOfElements) {
				max = noOfElements;
				entityWithMaxNo = currentEntity;
			}
			return noOfElements;
		}

		public OWLObject getEntityWithMaxNo() {
			return entityWithMaxNo;
		}
		
		public int getMaxValue(){
			return max;
		}

		public void setSum(int sum) {
			this.sum = sum;
		}

		public int getSum() {
			return sum;
		}

	}
	
	

}
