package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owldataproperty;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import uk.ac.manchester.cs.bhig.owlmetrics.ValueComparator;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.OWLHierarchyParser;

public abstract class OWLSuperDataPropertyCount extends OWLHierarchyParser {
	
	private OWLReasoner reasoner;
	private int sum = 0;
	private int max = 0;
	private OWLDataProperty maxEntity;
	
	//save entity and its value
	private HashMap<OWLDataProperty, Integer> axiomMap = new HashMap<OWLDataProperty, Integer>();
	
	@Override
	public LinkedHashMap<?, Integer> getEntitiesWithValue() {
		return ValueComparator.sortHashMapByValues(axiomMap);
	}

	public OWLSuperDataPropertyCount(OWLOntologyManager owlOntologyManager,
			OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
		
		this.reasoner = reasoner;
	}
	
	public int getNumberOfSuperDataProperties(OWLDataProperty child, boolean directOf){
		int number = 0;
		
		NodeSet<? extends OWLObject> parentSet = getSuperNodes(child, directOf);
		if(!parentSet.getNodes().contains(reasoner.getBottomObjectPropertyNode()) 
				&& !parentSet.isEmpty()){
			number = parentSet.getFlattened().size();
		}
		
		return number;
	}
	
	protected void computeTotalSuperDataPropertyMetrics(OWLOntology ont){
		sum = 0;
		max = 0;
		maxEntity = null;
		
		for(OWLDataProperty child : ont.getDataPropertiesInSignature()){
			int noOfEntities = getNumberOfSuperDataProperties(child, true);
			
			sum += noOfEntities;
			//gen.compareToMaxValue(noOfEntities, child);
			
			//save the value and entity
			axiomMap.put(child, noOfEntities);
			
			if(max < noOfEntities){
				max = noOfEntities;
				maxEntity = child;
			}
		}
		//gen.setSum(sum);
	}
	
	
	protected int getSumOfSuperDataProperties(){
		return sum;
	}
	
	protected int getMaxNumberOfSuperDataProperties(){
		return max;
	}

	protected OWLDataProperty getOWLDataPropertyWithMaxNumberOfSuperDataProperties(){
		return maxEntity;
	}

	@Override
	protected void disposeMetric() {

	}
}
