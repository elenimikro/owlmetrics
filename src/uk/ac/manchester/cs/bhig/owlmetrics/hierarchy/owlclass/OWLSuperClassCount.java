package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owlclass;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import uk.ac.manchester.cs.bhig.owlmetrics.ValueComparator;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.OWLHierarchyParser;

public abstract class OWLSuperClassCount extends OWLHierarchyParser {
	
	private OWLReasoner reasoner;
	private int sum = 0;
	private int max = 0;
	private OWLClass maxEntity;
	
	private HashMap<OWLClass, Integer> axiomMap = new HashMap<OWLClass, Integer>();
	
	public OWLSuperClassCount(OWLOntologyManager owlOntologyManager,
			OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
		
		this.reasoner = reasoner;
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
	
	protected void computeTotalSuperClassMetrics(OWLOntology ont){
		sum = 0;
		max = 0;
		maxEntity = null;
		
		for(OWLClass child : ont.getClassesInSignature()){
			int noOfEntities = getNumberOfSuperClasses(child, true);
			
			sum += noOfEntities;
			//gen.compareToMaxValue(noOfEntities, child);
			
			if(max < noOfEntities){
				max = noOfEntities;
				maxEntity = child;
			}

		}
		//gen.setSum(sum);
	}
	
	
	protected int getSumOfSuperClasses(){
		return sum;
	}
	
	protected int getMaxNumberOfSuperClasses(){
		return max;
	}

	protected OWLClass getOWClassWithMaxNumberOfSuperClasses(){
		return maxEntity;
	}
	
	public Set<OWLAxiom> getAxioms(){
		return null;
		
	}
	
	public LinkedHashMap<? extends OWLObject, Integer> getEntitiesWithValue(){
		return ValueComparator.sortHashMapByValues(axiomMap);
	}

}
