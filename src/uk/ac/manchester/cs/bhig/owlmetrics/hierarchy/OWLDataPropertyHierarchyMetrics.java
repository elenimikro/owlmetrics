package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy;

import java.util.ArrayList;
import java.util.HashSet;

import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

public class OWLDataPropertyHierarchyMetrics extends OWLHierarchyMetrics{
	
	private ArrayList<Integer> medianSet = new ArrayList<Integer>();
	private HierarchyMetricGenerator subMetrics;
	private HierarchyMetricGenerator superMetrics;
	private HashSet<OWLObject> leafSet = new HashSet<OWLObject>();
	
	private OWLReasoner reasoner;
	
	public OWLDataPropertyHierarchyMetrics(OWLReasoner reasoner, OWLOntology ontology) {
		super(reasoner);
		this.reasoner = reasoner;
		
		subMetrics = computeTotalSubPropertyMetrics(ontology);
		superMetrics = computeTotalSuperPropertyMetrics(ontology);
		computeDataPropertyHierarchyDepthMetrics();
	}
	
	private void computeDataPropertyHierarchyDepthMetrics(){
		Node<OWLDataProperty> topNode = reasoner.getTopDataPropertyNode();
		
		topDownTraverse(topNode, 0);
	}
	
	public int getTotalNumberOfDataPropertiesInOntology(){
		return subMetrics.getSum();
	}
	
	public int getMaximumlNumberOfSubDataProperties(){
		return subMetrics.getMaxValue();
	}
	
	public OWLDataProperty getDataPropertyWithMaxNumberOfSubDataProperties(){
		return (OWLDataProperty) subMetrics.getEntityWithMaxNo();
	}
	
	public int getTotalNumberOfSuperDataPropertiesInOntology(){
		return superMetrics.getSum();
	}
	
	public int getMaximumlNumberOfDataProperties(){
		return superMetrics.getMaxValue();
	}
	
	public OWLDataProperty getDataPropertyWithMaxNumberOfSuperDataProperties(){
		return (OWLDataProperty) superMetrics.getEntityWithMaxNo();
	}

	private HierarchyMetricGenerator computeTotalSubPropertyMetrics(OWLOntology ont){
		medianSet.clear();
		int sum=0;
		HierarchyMetricGenerator gen = new HierarchyMetricGenerator();
		for(OWLDataProperty parent : ont.getDataPropertiesInSignature()){
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
		
		for(OWLDataProperty child : ont.getDataPropertiesInSignature()){
			int noOfEntities = getNumberOfSuperProperties(child, true);
			
			sum += noOfEntities;
			gen.compareToMaxValue(noOfEntities, child);

		}
		gen.setSum(sum);
		
		return gen;
	}
	
	public int getNumberOfSubProperties(OWLDataProperty parent, boolean directOf){
		int number = 0;
		
		NodeSet<? extends OWLObject> childrenSet = getSubNodes(parent, directOf);
		if(!childrenSet.getNodes().contains(reasoner.getBottomDataPropertyNode()) 
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
	
	public int getNumberOfSuperProperties(OWLDataProperty child, boolean directOf){
		int number = 0;
		
		NodeSet<? extends OWLObject> parentSet = getSuperNodes(child, directOf);
		if(!parentSet.getNodes().contains(reasoner.getBottomDataPropertyNode()) 
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
