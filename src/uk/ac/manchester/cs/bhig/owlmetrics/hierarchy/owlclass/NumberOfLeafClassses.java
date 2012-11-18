package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owlclass;

import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;


public class NumberOfLeafClassses extends TotalNumberOfSubClassesCount{


	public NumberOfLeafClassses(OWLOntologyManager owlOntologyManager,
			OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
		// TODO Auto-generated constructor stub
	}
	
	public String getName(){
		return "Number of leaf classes";
	}
	
	public Integer recomputeMetric(){
		 super.recomputeMetric();
		 return super.getLeafClasses().size();
	}
	
	

}
