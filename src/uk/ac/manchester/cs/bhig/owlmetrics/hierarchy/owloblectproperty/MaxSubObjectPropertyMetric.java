package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owloblectproperty;

import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;


public class MaxSubObjectPropertyMetric extends TotalNumberOfSubObjectPropertyCount {

	public MaxSubObjectPropertyMetric(OWLOntologyManager owlOntologyManager,
			OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
	}
	
	public String getName(){
		return "Max number of SubObjectProperties";
	}
	
	public Integer recomputeMetric(){
		 super.recomputeMetric();
		 return super.getMaxNumberOfSubObjectProperties();
	}
	
}
