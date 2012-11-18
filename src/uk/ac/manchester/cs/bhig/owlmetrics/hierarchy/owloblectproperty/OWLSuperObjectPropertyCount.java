package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owloblectproperty;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import uk.ac.manchester.cs.bhig.owlmetrics.ValueComparator;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.OWLHierarchyParser;

public abstract class OWLSuperObjectPropertyCount extends OWLHierarchyParser {
	
	private OWLReasoner reasoner;
	private int sum = 0;
	private int max = 0;
	private OWLObjectProperty maxEntity;
	
	//save entity and its value
	private HashMap<OWLObjectProperty, Integer> axiomMap = new HashMap<OWLObjectProperty, Integer>();
	
	public OWLSuperObjectPropertyCount(OWLOntologyManager owlOntologyManager,
			OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
		
		this.reasoner = reasoner;
	}
	
	public int getNumberOfSuperObjectProperties(OWLObjectProperty child, boolean directOf){
		int number = 0;
		
		NodeSet<? extends OWLObject> parentSet = getSuperNodes(child, directOf);
		if(!parentSet.getNodes().contains(reasoner.getBottomObjectPropertyNode()) 
				&& !parentSet.isEmpty()){
			number = parentSet.getFlattened().size();
		}
		
		axiomMap.put(child, number);
		
		return number;
	}
	
	@Override
	public LinkedHashMap<?, Integer> getEntitiesWithValue() {
		return ValueComparator.sortHashMapByValues(axiomMap);
	}

	protected void computeTotalSuperObjectPropertyMetrics(OWLOntology ont){
		sum = 0;
		max = 0;
		maxEntity = null;
		
		for(OWLObjectProperty child : ont.getObjectPropertiesInSignature()){
			int noOfEntities = getNumberOfSuperObjectProperties(child, true);
			
			sum += noOfEntities;
			//gen.compareToMaxValue(noOfEntities, child);
			
			if(max < noOfEntities){
				max = noOfEntities;
				maxEntity = child;
			}
		}
		//gen.setSum(sum);
	}
	
	
	protected int getSumOfSuperObjectProperties(){
		return sum;
	}
	
	protected int getMaxNumberOfSuperObjectProperties(){
		return max;
	}

	protected OWLObjectProperty getOWObjectPropertyWithMaxNumberOfSuperObjectProperties(){
		return maxEntity;
	}

}
