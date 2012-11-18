package uk.ac.manchester.cs.bhig.owlmetrics.usage;

import java.util.List;

import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;


public class SumOfOWLObjectPropertyUsage extends OWLObjectPropertyUsageCount {

	public SumOfOWLObjectPropertyUsage(OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		return "Sum of OWLObjectProperty usage";
	}

	@Override
	protected Integer recomputeMetric() {
		int sum = 0;
		for(OWLOntology ont : super.getOntologies()){
			for(OWLObjectProperty objProp : ont.getObjectPropertiesInSignature()){
				sum += super.getOWLObjectPropertyUsage(objProp);
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
