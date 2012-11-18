package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owldataproperty;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import uk.ac.manchester.cs.bhig.owlmetrics.CountableEntity;
import uk.ac.manchester.cs.bhig.owlmetrics.ValueComparator;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.OWLHierarchyParser;

public abstract class OWLSubDataPropertyCount extends OWLHierarchyParser implements CountableEntity<Object>{
	
	private OWLReasoner reasoner;
	private HashSet<OWLDataProperty> leafSet = new HashSet<OWLDataProperty>();
	
	private int sum = 0;
	private int max = 0;
	private OWLDataProperty maxEntity;

	//save entity and its value
	private HashMap<OWLDataProperty, Integer> axiomMap = new HashMap<OWLDataProperty, Integer>();
	
	public OWLSubDataPropertyCount(OWLOntologyManager owlOntologyManager, OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
		this.reasoner = reasoner;
	}

	public String getMetricNameForEntity(OWLDataProperty parent) {
		return "Number of SubDataProperties of " + parent;
	}
	
	
	public Integer getNumberOfSubDataProperties(OWLDataProperty parent, boolean directOf){
		int number = 0;
		
		NodeSet<? extends OWLObject> childrenSet = getSubNodes(parent, directOf);
		if(!childrenSet.getNodes().contains(reasoner.getBottomDataPropertyNode()) 
				&& !childrenSet.isEmpty() && childrenSet != null){
			number = childrenSet.getFlattened().size();
		}
		else{
			leafSet.add(parent);
		}
		
		axiomMap.put(parent, number);
		
		return number;
	}
	
	@Override
	public LinkedHashMap<?, Integer> getEntitiesWithValue() {
		return ValueComparator.sortHashMapByValues(axiomMap);
	}

	protected void computeTotalSubDataPropertyMetrics(Set<OWLOntology> ontologies){
		//medianSet.clear();
		sum=0;
		max = 0;
		maxEntity = null;
		
		for(OWLOntology ont : ontologies){
			for(OWLDataProperty parent : ont.getDataPropertiesInSignature()){
				int noOfEntities = getNumberOfSubDataProperties(parent, true);
					
				//medianSet.add(noOfEntities);
				sum += noOfEntities;
			
				if(max<noOfEntities){
					max = noOfEntities;
					maxEntity = parent;
				}
			}
		}
	}
	
	protected HashSet<OWLDataProperty> getLeafDataProperties(){
		return leafSet;
	}

	protected Integer getSumOfSubDataProperties(){
		return sum;
	}
	
	protected Integer getMaxNumberOfSubDataProperties(){
		return max;
	}
	
	protected OWLDataProperty getOWLDataPropertyWithMaxNumberOfSubDataProperties(){
		return maxEntity;
	}


}
