package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy;

import java.util.ArrayList;
import java.util.HashSet;

import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

public class OWLObjectPropertyHierarchyMetrics extends OWLHierarchyMetrics{
	
	private ArrayList<Integer> medianSet = new ArrayList<Integer>();
	private HierarchyMetricGenerator subMetrics;
	private HierarchyMetricGenerator superMetrics;
	private HashSet<OWLObject> leafSet = new HashSet<OWLObject>();
	
	private OWLReasoner reasoner;
	
	public OWLObjectPropertyHierarchyMetrics(OWLReasoner reasoner, OWLOntology ontology) {
		super(reasoner);
		this.reasoner = reasoner;
		
		subMetrics = computeTotalSubPropertyMetrics(ontology);
		superMetrics = computeTotalSuperPropertyMetrics(ontology);
		computeObjectPropertyHierarchyDepthMetrics();
	}
	
	private void computeObjectPropertyHierarchyDepthMetrics(){
		Node<OWLObjectPropertyExpression> topNode = reasoner.getTopObjectPropertyNode();
		
		topDownTraverse(topNode, 0);
	}
	
	public int getTotalNumberOfSubObjectPropertiesInOntology(){
		return subMetrics.getSum();
	}
	
	public int getMaximumlNumberOfSubObjectProperties(){
		return subMetrics.getMaxValue();
	}
	
	public OWLObjectProperty getObjectPropertyWithMaxNumberOfSubObjectProperties(){
		return (OWLObjectProperty) subMetrics.getEntityWithMaxNo();
	}
	
	public int getTotalNumberOfSuperObjectPropertiesInOntology(){
		return superMetrics.getSum();
	}
	
	public int getMaximumlNumberOfSuperObjectProperties(){
		return superMetrics.getMaxValue();
	}
	
	public OWLObjectProperty getObjectPropertyWithMaxNumberOfSuperObjectProperties(){
		return (OWLObjectProperty) superMetrics.getEntityWithMaxNo();
	}

	private HierarchyMetricGenerator computeTotalSubPropertyMetrics(OWLOntology ont){
		medianSet.clear();
		int sum=0;
		HierarchyMetricGenerator gen = new HierarchyMetricGenerator();
		for(OWLObjectProperty parent : ont.getObjectPropertiesInSignature()){
			int noOfEntities = getNumberOfSubProperties(parent, true);
				
			medianSet.add(noOfEntities);
			sum += noOfEntities;
			gen.compareToMaxValue(noOfEntities, parent);
		}
		
		gen.setSum(sum);
		return gen;
		
	}
	
	private HierarchyMetricGenerator computeTotalSuperPropertyMetrics(OWLOntology ont){
		int sum = 0;
		HierarchyMetricGenerator gen = new HierarchyMetricGenerator();
		
		for(OWLObjectProperty child : ont.getObjectPropertiesInSignature()){
			int noOfEntities = getNumberOfSuperProperties(child, true);
			
			sum += noOfEntities;
			gen.compareToMaxValue(noOfEntities, child);

		}
		gen.setSum(sum);
		
		return gen;
	}
	
	public int getNumberOfSubProperties(OWLObjectProperty parent, boolean directOf){
		int number = 0;
		
		NodeSet<? extends OWLObject> childrenSet = getSubNodes(parent, directOf);
		if(!childrenSet.getNodes().contains(reasoner.getBottomObjectPropertyNode()) 
				&& !childrenSet.isEmpty() && childrenSet != null){
			number = childrenSet.getFlattened().size();
		}
		else{
			leafSet.add(parent);
		}
		
		return number;
	}
	
	public HashSet<OWLObject> getLeafEntities(){
		return leafSet;
	}
	
	public int getNumberOfSuperProperties(OWLObjectProperty child, boolean directOf){
		int number = 0;
		
		NodeSet<? extends OWLObject> parentSet = getSuperNodes(child, directOf);
		if(!parentSet.getNodes().contains(reasoner.getBottomObjectPropertyNode()) 
				&& !parentSet.isEmpty()){
			number = parentSet.getFlattened().size();
		}
		
		return number;
	}
	
	public int getObjectPropertyHierarchyMaxDepth(){
		return getMaxDepth();
	}
	
	public int getNumberOfPathsInObjectPropertyHierarchy(){
		return getNumberOfPaths();
	}
	
	public int getSumOfDepthsInObjectPropertyHierarchy(){
		return getSumOfDephts();
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
