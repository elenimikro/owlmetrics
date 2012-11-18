package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owloblectproperty;

import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;


public class NumberOfLeafObjectProperties extends TotalNumberOfSubObjectPropertyCount {

	public NumberOfLeafObjectProperties(OWLOntologyManager owlOntologyManager,
			OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
		// TODO Auto-generated constructor stub
	}
	
	public String getName(){
		return "Number of leaf object properties";
	}
	
	public Integer recomputeMetric(){
		 super.recomputeMetric();
		 return super.getLeafObjectProperties().size();
	}

}
