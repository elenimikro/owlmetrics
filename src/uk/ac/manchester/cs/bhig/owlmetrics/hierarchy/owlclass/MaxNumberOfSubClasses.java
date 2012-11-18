package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owlclass;

import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;


public class MaxNumberOfSubClasses extends TotalNumberOfSubClassesCount {

	public MaxNumberOfSubClasses(OWLOntologyManager owlOntologyManager,
			OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
	}
	
	public String getName(){
		return "Max number of SubClasses";
	}
	
	public Integer recomputeMetric(){
		 super.recomputeMetric();
		 return super.getMaxNumberOfSubClasses();
	}
	
}
