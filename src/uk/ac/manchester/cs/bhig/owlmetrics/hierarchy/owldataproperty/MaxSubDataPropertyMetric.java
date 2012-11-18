package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owldataproperty;

import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;


public class MaxSubDataPropertyMetric extends TotalNumberOfSubDataPropertyCount {

	public MaxSubDataPropertyMetric(OWLOntologyManager owlOntologyManager,
			OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
	}
	
	public String getName(){
		return "Max number of SubDataProperties";
	}
	
	public Integer recomputeMetric(){
		 super.recomputeMetric();
		 return super.getMaxNumberOfSubDataProperties();
	}
	
}
