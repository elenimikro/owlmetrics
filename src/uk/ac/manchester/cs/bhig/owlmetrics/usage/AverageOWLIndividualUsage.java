package uk.ac.manchester.cs.bhig.owlmetrics.usage;

import java.util.List;

import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.manchester.cs.bhig.owlmetrics.DoubleValuedMetric;
import uk.ac.manchester.cs.bhig.owlmetrics.owlindividual.OWLIndividualCount;

public class AverageOWLIndividualUsage extends DoubleValuedMetric {

	private SumOfOWLIndividualUsage usage;
	private OWLIndividualCount indis;
	
	public AverageOWLIndividualUsage(OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
		
		usage = new SumOfOWLIndividualUsage(owlOntologyManager);
		indis = new OWLIndividualCount(owlOntologyManager);
	}

	@Override
	public String getName() {
		return "Average OWLIndividual usage";
	}

	@Override
	protected Double recomputeMetric() {
		usage.setOntology(getOntology());
		indis.setOntology(getOntology());
		
		super.setNumerator(usage.recomputeMetric());
		super.setDenominator(indis.recomputeMetric());
		
		return super.computeNormalisedMetric();
	}

	@Override
	protected boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		if(usage.isMetricInvalidated(changes) || indis.isMetricInvalidated(changes))
			return true;
		else
			return false;
	}

	@Override
	protected void disposeMetric() {

	}

}
