package uk.ac.manchester.cs.bhig.owlmetrics.usage;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.semanticweb.owlapi.metrics.AbstractOWLMetric;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.manchester.cs.bhig.owlmetrics.CountableEntity;
import uk.ac.manchester.cs.bhig.owlmetrics.ValueComparator;

public abstract class OWLIndividualUsageCount extends AbstractOWLMetric<Object> implements CountableEntity<Object>{
	
	private int max = 0;
	private OWLIndividual individualWithMaxUsage;
	
	HashMap<OWLIndividual, Integer> individualUsageMap = new HashMap<OWLIndividual, Integer>();

	public OWLIndividualUsageCount(OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
	}
	
	public String getIndividualEntityMetricName(OWLIndividual indi){
		return "Usage of " + indi; 
	}

	
	protected void computeOWLOntologyIndividualUsage(){
		
		
		for(OWLOntology ont : super.getOntologies()){
			for(OWLNamedIndividual indi : ont.getIndividualsInSignature()){

				int indiUsageInt = getOWLIndividualUsage(indi);

				individualUsageMap.put(indi, indiUsageInt);

				if(max < indiUsageInt){
					max = indiUsageInt;
					individualWithMaxUsage = indi;
				}
			}
		}
	}
	
	@Override
	public LinkedHashMap<?, Integer> getEntitiesWithValue() {
		return ValueComparator.sortHashMapByValues(individualUsageMap);
	}

	public int getOWLIndividualUsage(OWLNamedIndividual indi){
		int individualUsage = 0;
		for(OWLAxiom ax  : indi.getReferencingAxioms(super.getOntology(), true)){
			if(!ax.isOfType(AxiomType.DIFFERENT_INDIVIDUALS)){
				individualUsage++;
			}		
		}
		return individualUsage;
	}
	
	
	protected OWLIndividual getIndividualWithMaxUsage(){
		return individualWithMaxUsage;
	}
	
	protected int getMaxOWLIndividualUsage(){
		return max;
	}
	
	@Override
	protected boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		for (OWLOntologyChange chg : changes) {
            if (chg.isAxiomChange() && !chg.getAxiom().getIndividualsInSignature().isEmpty()) {
                return true;
            }
        }
		return false;
	}
	
	@Override
	protected void disposeMetric() {

	}

}
