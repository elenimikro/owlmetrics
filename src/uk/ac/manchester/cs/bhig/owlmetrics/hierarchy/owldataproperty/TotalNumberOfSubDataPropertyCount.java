package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owldataproperty;

import java.util.List;

import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;


public class TotalNumberOfSubDataPropertyCount extends OWLSubDataPropertyCount {

	
	
	public TotalNumberOfSubDataPropertyCount(OWLOntologyManager owlOntologyManager,
			OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
	}
	
	public String getName(){
		return "Total number of SubDataProperties";
	}
	
	

	protected Integer recomputeMetric(){
		 super.computeTotalSubDataPropertyMetrics(super.getOntologies());
		 return super.getSumOfSubDataProperties();
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
