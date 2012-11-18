package uk.ac.manchester.cs.bhig.owlmetrics.usage;

import java.util.List;

import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.manchester.cs.bhig.owlmetrics.DoubleValuedMetric;
import uk.ac.manchester.cs.bhig.owlmetrics.owlclass.OWLClassNumber;

public class AverageOWLClassUsage extends DoubleValuedMetric {

	private SumOfOWLClassUsage usage;
	private OWLClassNumber classes;
	
	public AverageOWLClassUsage(OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
		
		usage = new SumOfOWLClassUsage(owlOntologyManager);
		classes = new OWLClassNumber(owlOntologyManager);
	}

	@Override
	public String getName() {
		return "Average OWLClass usage";
	}

	@Override
	protected Double recomputeMetric() {
		usage.setOntology(getOntology());
		classes.setOntology(getOntology());
		
		super.setNumerator(usage.recomputeMetric());
		super.setDenominator(classes.recomputeMetric());
		
		return super.computeNormalisedMetric();
	}

	@Override
	protected boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		if(usage.isMetricInvalidated(changes) || classes.isMetricInvalidated(changes))
			return true;
		else
			return false;
	}

	@Override
	protected void disposeMetric() {

	}

}
