package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owloblectproperty;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import uk.ac.manchester.cs.bhig.owlmetrics.ValueComparator;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.OWLHierarchyParser;

public abstract class OWLSubObjectPropertyCount extends OWLHierarchyParser{
	
	private OWLReasoner reasoner;
	private HashSet<OWLObjectProperty> leafSet = new HashSet<OWLObjectProperty>();
	
	private int sum = 0;
	private int max = 0;
	private OWLObjectProperty maxEntity;
	
	//keeps the entities with values
	HashMap<OWLObjectProperty, Integer> axiomMap = new HashMap<OWLObjectProperty, Integer>();

	public OWLSubObjectPropertyCount(OWLOntologyManager owlOntologyManager, OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
		this.reasoner = reasoner;
	}

	public String getMetricNameForEntity(OWLObjectProperty parent) {
		return "Number of SubObjectProperties of " + parent;
	}
	
	
	public Integer getNumberOfSubObjectProperties(OWLObjectProperty parent, boolean directOf){
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
	
	protected void computeTotalSubObjectPropertyMetrics(Set<OWLOntology> ontologies){
		//medianSet.clear();
		sum=0;
		max = 0;
		maxEntity = null;
		
		for(OWLOntology ont : ontologies){
			for(OWLObjectProperty parent : ont.getObjectPropertiesInSignature()){
				int noOfEntities = getNumberOfSubObjectProperties(parent, true);
					
				//medianSet.add(noOfEntities);
				sum += noOfEntities;
				
				axiomMap.put(parent, noOfEntities);
			
				if(max<noOfEntities){
					max = noOfEntities;
					maxEntity = parent;
				}
			}
		}
	}
	
	protected HashSet<OWLObjectProperty> getLeafObjectProperties(){
		return leafSet;
	}

	protected Integer getSumOfSubObjectProperties(){
		return sum;
	}
	
	protected Integer getMaxNumberOfSubObjectProperties(){
		return max;
	}
	
	protected OWLObjectProperty getOWLObjectPropertyWithMaxNumberOfSubObjectProperties(){
		return maxEntity;
	}

	@Override
	public LinkedHashMap<OWLObject, Integer> getEntitiesWithValue() {
		return ValueComparator.sortHashMapByValues(axiomMap);
	}

}
