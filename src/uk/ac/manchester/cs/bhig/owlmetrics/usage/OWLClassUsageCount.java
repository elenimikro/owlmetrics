package uk.ac.manchester.cs.bhig.owlmetrics.usage;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.metrics.AbstractOWLMetric;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.manchester.cs.bhig.owlmetrics.CountableEntity;
import uk.ac.manchester.cs.bhig.owlmetrics.ValueComparator;

public abstract class OWLClassUsageCount extends AbstractOWLMetric<Object> implements CountableEntity<Object>{

	private int max = 0;
	private OWLClass classWithMaxUsage;
	
	private HashMap<OWLClass, Integer> classUsageMap = new HashMap<OWLClass, Integer>();
	
	public OWLClassUsageCount(OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
		// TODO Auto-generated constructor stub
	}
	
	public String getIndividualEntityMetricName(OWLClass cls){
		return "Usage of " + cls; 
	}

	/**
	 * It saves the usage of each class into 
	 * an XML file. Also saves the class with
	 * maximum usage
	 * @return 
	 * 
	 */
	protected void computeOWLOntologyClassUsage(){
		
		for(OWLOntology ont : super.getOntologies()){
			for(OWLClass cls : ont.getClassesInSignature()){
				
				int classUsageInt = getOWLClassUsage(cls);
				
				classUsageMap.put(cls, classUsageInt);
				
//				classUsageMap.put(cls.toStringID().
//							substring(cls.toStringID().lastIndexOf("#") + 1), classUsageInt);
		
				//find the class with the maximum usage 
				//In these exclude the owl:thing class because
				//this information is redundant 
				if (!cls.isOWLThing()) {
					if(max < classUsageInt){
						max = classUsageInt;
						classWithMaxUsage = cls;
					}
				}
			}
		}
		
	}
	
	public int getOWLClassUsage(OWLClass cls){
		int classUsageInt=0;
		
		
		for(OWLAxiom ax : cls.getReferencingAxioms(super.getOntology())){
			//if it is a sublclass axiom
			if(ax.isOfType(AxiomType.SUBCLASS_OF)){
				Set<OWLObjectProperty> propSet = ax.getObjectPropertiesInSignature();
				if(!propSet.isEmpty()){
					classUsageInt++;
				}
			}
			else{
				classUsageInt++;
			}	
		}
		return classUsageInt;
	}
	
	
	protected Integer getMaxClassUsage(){
		return max;
	}
	
	protected OWLClass getClassWithMaxUsage(){
		return classWithMaxUsage;
	}
	
	protected int getMaxOWLClassUsage(){
		return max;
	}
	
	@Override
	protected boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		for (OWLOntologyChange chg : changes) {
            if (chg.isAxiomChange() && !chg.getAxiom().getClassesInSignature().isEmpty()) {
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
		return ValueComparator.sortHashMapByValues(classUsageMap);
	}


}
