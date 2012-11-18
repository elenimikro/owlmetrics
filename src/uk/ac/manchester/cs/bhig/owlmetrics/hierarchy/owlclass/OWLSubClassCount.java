package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owlclass;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import uk.ac.manchester.cs.bhig.owlmetrics.CountableEntity;
import uk.ac.manchester.cs.bhig.owlmetrics.ValueComparator;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.OWLHierarchyParser;

public abstract class OWLSubClassCount extends OWLHierarchyParser implements CountableEntity<Object>{
	
	private OWLReasoner reasoner;
	private HashSet<OWLClass> leafSet = new HashSet<OWLClass>();
	
	private int sum = 0;
	private int max = 0;
	private OWLClass maxEntity;
	
	//map for keeping entities and values
	private HashMap<OWLClass, Integer> axiomMap = new HashMap<OWLClass, Integer>();
	
	public OWLSubClassCount(OWLOntologyManager owlOntologyManager, OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
		this.reasoner = reasoner;
	}

	public String getMetricNameForEntity(OWLClass parent) {
		return "Number of SubClasses of " + parent;
	}
	
	
	public Integer getNumberOfSubClasses(OWLClass parent, boolean directOf){
		int number = 0;
		
		NodeSet<? extends OWLObject> childrenSet = getSubNodes(parent, directOf);
		if(!childrenSet.getNodes().contains(reasoner.getBottomClassNode()) 
				&& !childrenSet.isEmpty() && childrenSet != null){
			number = childrenSet.getFlattened().size();
		}
		else{
			leafSet.add(parent);
		}
		
		axiomMap.put(parent, number);
		
		return number;
	}
	
	protected void computeTotalSubClassMetrics(Set<OWLOntology> ontologies){
		//medianSet.clear();
		sum=0;
		max = 0;
		maxEntity = null;
		
		for(OWLOntology ont : ontologies){
			for(OWLClass parent : ont.getClassesInSignature()){
				int noOfEntities = getNumberOfSubClasses(parent, true);
					
				//medianSet.add(noOfEntities);
				sum += noOfEntities;
			
				if(max<noOfEntities){
					max = noOfEntities;
					maxEntity = parent;
				}
			}
		}
	}
	
	protected HashSet<OWLClass> getLeafClasses(){
		return leafSet;
	}

	protected Integer getSumOfSubClasses(){
		return sum;
	}
	
	protected Integer getMaxNumberOfSubClasses(){
		return max;
	}
	
	protected OWLClass getOWLClassWithMaxNumberOfSubClasses(){
		return maxEntity;
	}


	@Override
	public LinkedHashMap<?, Integer> getEntitiesWithValue() {
		return ValueComparator.sortHashMapByValues(axiomMap);
	}

}
