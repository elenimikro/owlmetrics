package uk.ac.manchester.cs.bhig.owlmetrics.usage;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.semanticweb.owlapi.metrics.AbstractOWLMetric;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.manchester.cs.bhig.owlmetrics.CountableEntity;
import uk.ac.manchester.cs.bhig.owlmetrics.ValueComparator;

public abstract class OWLObjectPropertyUsageCount extends AbstractOWLMetric<Object> implements CountableEntity<Object>{
	
	private int max = 0;
	private OWLObjectProperty propertyWithMaxUsage;

	private int sumOfPropertyUsageWithClasses=0;
	
	private HashMap<OWLObjectProperty, Integer> objectPropertyUsageMap = new HashMap<OWLObjectProperty, Integer>();
	
	public OWLObjectPropertyUsageCount(OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
	}
	
	public String getIndividualEntityMetricName(OWLObjectProperty property){
		return "Usage of object property " + property;
	}

	/**
	 * It saves the usage of each class into 
	 * an XML file. Also saves the class with
	 * maximum usage
	 * @return 
	 * 
	 */
	protected void computeOWLOntologyObjectPropertyUsage(){
		for(OWLOntology ont : super.getOntologies()){
			for(OWLObjectProperty prop : ont.getObjectPropertiesInSignature()){
				
				int objectPropUsageInt = getOWLObjectPropertyUsage(prop);
				
				objectPropertyUsageMap.put(prop, objectPropUsageInt);
		
				//find the class with the maximum usage 
				//In these exclude the owl:thing class because
				//this information is redundant 
				if (!prop.isTopEntity()) {
					if(max < objectPropUsageInt){
						max = objectPropUsageInt;
						propertyWithMaxUsage = prop;
					}
				}
			}
		}
	}
	
	@Override
	public LinkedHashMap<?, Integer> getEntitiesWithValue() {
		return ValueComparator.sortHashMapByValues(objectPropertyUsageMap);
	}


	/**
	 * Computes the usage of a single property
	 * @param property
	 * @return
	 */
	public int getOWLObjectPropertyUsage(OWLObjectProperty property){
		int propertyUsage =0;
		int propertyUsageWithClasses=0;
		
		for(OWLAxiom ax : property.getReferencingAxioms(super.getOntology())){
			if(!ax.isOfType(AxiomType.SUB_OBJECT_PROPERTY)){
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

	protected OWLObjectProperty getObjectPropertyWithMaxUsage(){
		return propertyWithMaxUsage;
	}
	
	protected int getMaxOWLObjectPropertyUsage(){
		return max;
	}
	
	@Override
	protected boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		for (OWLOntologyChange chg : changes) {
            if (chg.isAxiomChange() && !chg.getAxiom().getObjectPropertiesInSignature().isEmpty()) {
                return true;
            }
        }
		return false;
	}
	

	@Override
	protected void disposeMetric() {
		
	}

}
