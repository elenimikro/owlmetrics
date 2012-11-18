package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owloblectproperty;

import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

public class NumberOfOWLObjectPropertyPaths extends SumOfDepthsInOWLObjectPropertyHierarchy {

	public NumberOfOWLObjectPropertyPaths(OWLOntologyManager owlOntologyManager,
			OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
	}
	
	public String getName(){
		return "Number of object property paths";
	}
	
	public Integer recomputeMetric(){
		super.recomputeMetric();
		return super.getNumberOfPaths();
	}

}
