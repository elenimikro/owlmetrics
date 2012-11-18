package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owlclass;

import java.util.List;

import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;


public class TotalNumberOfSubClassesCount extends OWLSubClassCount {

	
	
	public TotalNumberOfSubClassesCount(OWLOntologyManager owlOntologyManager,
			OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
		//computeTotalSubClassMetrics(super.getOntologies());
	}
	
	public String getName(){
		return "Total number of Subclasses";
	}
	
	

	protected Integer recomputeMetric(){
		 super.computeTotalSubClassMetrics(super.getOntologies());
		 return super.getSumOfSubClasses();
	}
	
	

	@Override
	public boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		return super.isMetricInvalidated(changes);
	}

	@Override
	protected void disposeMetric() {
		super.dispose();
	}

}
