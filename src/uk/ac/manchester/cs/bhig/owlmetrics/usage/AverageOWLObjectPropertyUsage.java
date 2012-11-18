package uk.ac.manchester.cs.bhig.owlmetrics.usage;

import java.util.List;

import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.manchester.cs.bhig.owlmetrics.DoubleValuedMetric;
import uk.ac.manchester.cs.bhig.owlmetrics.owlclass.OWLClassNumber;
import uk.ac.manchester.cs.bhig.owlmetrics.owlobjectproperty.OWLObjectPropertiesCount;

public class AverageOWLObjectPropertyUsage extends DoubleValuedMetric {

	private SumOfOWLObjectPropertyUsage usage;
	private OWLObjectPropertiesCount properties;
	
	public AverageOWLObjectPropertyUsage(OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
		
		usage = new SumOfOWLObjectPropertyUsage(owlOntologyManager);
		properties = new OWLObjectPropertiesCount(owlOntologyManager);
	}

	@Override
	public String getName() {
		return "Average OWLObjectProperty usage";
	}

	@Override
	protected Double recomputeMetric() {
		usage.setOntology(getOntology());
		properties.setOntology(getOntology());
		
		super.setNumerator(usage.recomputeMetric());
		super.setDenominator(properties.recomputeMetric());
		
		return super.computeNormalisedMetric();
	}

	@Override
	protected boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		if(usage.isMetricInvalidated(changes) || properties.isMetricInvalidated(changes))
			return true;
		else
			return false;
	}

	@Override
	protected void disposeMetric() {

	}

}
