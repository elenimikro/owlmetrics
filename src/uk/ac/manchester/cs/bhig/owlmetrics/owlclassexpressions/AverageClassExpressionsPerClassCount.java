package uk.ac.manchester.cs.bhig.owlmetrics.owlclassexpressions;

import java.util.List;

import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import uk.ac.manchester.cs.bhig.owlmetrics.DoubleValuedMetric;
import uk.ac.manchester.cs.bhig.owlmetrics.owlclass.OWLClassNumber;

public class AverageClassExpressionsPerClassCount extends DoubleValuedMetric {

	private SumOfClassExpressionUsage usage;
	private OWLClassNumber classes;
	
	public AverageClassExpressionsPerClassCount(
			OWLOntologyManager owlOntologyManager, OWLReasoner reasoner) {
		super(owlOntologyManager);
		usage = new SumOfClassExpressionUsage(owlOntologyManager, reasoner);
		classes = new OWLClassNumber(owlOntologyManager);
	}

	@Override
	public String getName() {
		return "Average OWLClassExpressions per class";
	}

	@Override
	protected Double recomputeMetric() {
		usage.setOntology(getOntology());
		classes.setOntology(getOntology());
		
		super.setNumerator(usage.recomputeMetric());
		super.setDenominator(classes.recomputeMetric());
		
		return computeNormalisedMetric();
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
