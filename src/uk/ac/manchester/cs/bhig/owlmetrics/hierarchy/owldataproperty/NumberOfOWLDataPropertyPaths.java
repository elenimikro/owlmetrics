package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owldataproperty;

import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

public class NumberOfOWLDataPropertyPaths extends SumOfDepthsInOWLDataPropertyHierarchy {

	public NumberOfOWLDataPropertyPaths(OWLOntologyManager owlOntologyManager,
			OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
	}
	
	public String getName(){
		return "Number of data property paths";
	}
	
	public Integer recomputeMetric(){
		super.recomputeMetric();
		return super.getNumberOfPaths();
	}

}
