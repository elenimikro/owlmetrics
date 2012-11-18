package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owldataproperty;

import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;


public class NumberOfLeafDataProperties extends TotalNumberOfSubDataPropertyCount {

	public NumberOfLeafDataProperties(OWLOntologyManager owlOntologyManager,
			OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
		// TODO Auto-generated constructor stub
	}
	
	public String getName(){
		return "Number of leaf data properties";
	}
	
	public Integer recomputeMetric(){
		 super.recomputeMetric();
		 return super.getLeafDataProperties().size();
	}

}
