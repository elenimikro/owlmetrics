package uk.ac.manchester.cs.bhig.owlmetrics.usage;

import java.util.List;

import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;


public class SumOfOWLDataPropertyUsage extends OWLDataPropertyUsageCount {

	public SumOfOWLDataPropertyUsage(OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		return "Sum of OWLDataProperty usage";
	}

	@Override
	protected Integer recomputeMetric() {
		int sum = 0;
		for(OWLOntology ont : super.getOntologies()){
			for(OWLDataProperty dataProp : ont.getDataPropertiesInSignature()){
				sum += super.getOWLDataPropertyUsage(dataProp);
			}
		}
		return sum;
	}

	@Override
	protected boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		return super.isMetricInvalidated(changes);
	}

	@Override
	protected void disposeMetric() {
		// TODO Auto-generated method stub

	}

}
