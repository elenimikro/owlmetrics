package uk.ac.manchester.cs.bhig.owlmetrics.usage;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.semanticweb.owlapi.metrics.AbstractOWLMetric;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.manchester.cs.bhig.owlmetrics.CountableEntity;
import uk.ac.manchester.cs.bhig.owlmetrics.ValueComparator;

public abstract class OWLDataPropertyUsageCount extends AbstractOWLMetric<Object> implements CountableEntity<Object>{
	
	private int max = 0;
	private OWLDataProperty propertyWithMaxUsage;

	private int sumOfPropertyUsageWithClasses=0;
	
	private HashMap<OWLDataProperty, Integer> dataPropertyUsageMap = new HashMap<OWLDataProperty, Integer>();
	
	public OWLDataPropertyUsageCount(OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
		// TODO Auto-generated constructor stub
	}
	
	public String getIndividualEntityMetricName(OWLDataProperty property){
		return "Usage of data property " + property;
	}


	protected void computeOWLOntologyDataPropertyUsage(){
		
		
		for(OWLOntology ont : super.getOntologies()){
			for(OWLDataProperty prop : ont.getDataPropertiesInSignature()){
				
				int dataPropUsageInt = getOWLDataPropertyUsage(prop);
				
				dataPropertyUsageMap.put(prop, dataPropUsageInt);
		
				//find the class with the maximum usage 
				//In these exclude the owl:thing class because
				//this information is redundant 
				if (!prop.isTopEntity()) {
					if(max < dataPropUsageInt){
						max = dataPropUsageInt;
						propertyWithMaxUsage = prop;
					}
				}
			}
		}
	}
	
	/**
	 * Computes the usage of a single property
	 * @param prop
	 * @return
	 */
	public int getOWLDataPropertyUsage(OWLDataProperty prop){
		int propertyUsage =0;
		int propertyUsageWithClasses=0;
		
		for(OWLAxiom ax : prop.getReferencingAxioms(super.getOntology())){
			if(!ax.isOfType(AxiomType.SUB_DATA_PROPERTY)){
				propertyUsage++;
			}			
			
			//count the property usage for classes only 
			if(!ax.getClassesInSignature().isEmpty()){
				propertyUsageWithClasses++;
			}
		}
		
		sumOfPropertyUsageWithClasses = 
			sumOfPropertyUsageWithClasses + propertyUsageWithClasses;
		
		return propertyUsage;
	}
	
	

	protected OWLDataProperty getDataPropertyWithMaxUsage(){
		return propertyWithMaxUsage;
	}
	
	protected int getMaxOWLDataPropertyUsage(){
		return max;
	}
	
	@Override
	protected boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		for (OWLOntologyChange chg : changes) {
            if (chg.isAxiomChange() && !chg.getAxiom().getDataPropertiesInSignature().isEmpty()) {
                return true;
            }
        }
		return false;
	}
	
	@Override
	protected void disposeMetric() {
		
	}

	@Override
	public LinkedHashMap<?, Integer> getEntitiesWithValue() {
		return ValueComparator.sortHashMapByValues(dataPropertyUsageMap);
	}


}
