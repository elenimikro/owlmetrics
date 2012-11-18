package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owloblectproperty;

import java.util.List;

import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;


public class TotalNumberOfSubObjectPropertyCount extends OWLSubObjectPropertyCount {

	
	
	public TotalNumberOfSubObjectPropertyCount(OWLOntologyManager owlOntologyManager,
			OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
	}
	
	public String getName(){
		return "Total number of SubObjectProperties";
	}
	
	

	protected Integer recomputeMetric(){
		 super.computeTotalSubObjectPropertyMetrics(super.getOntologies());
		 return super.getSumOfSubObjectProperties();
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
