package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owlclass;

import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

public class NumberOfOWLClassPaths extends SumOfDepthsInOWLClassHierarchy {

	public NumberOfOWLClassPaths(OWLOntologyManager owlOntologyManager,
			OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
	}
	
	public String getName(){
		return "Number of class paths";
	}
	
	public Integer recomputeMetric(){
		super.recomputeMetric();
		return super.getNumberOfPaths();
	}

}
