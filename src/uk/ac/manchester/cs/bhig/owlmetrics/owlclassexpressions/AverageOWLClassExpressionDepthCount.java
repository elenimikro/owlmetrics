package uk.ac.manchester.cs.bhig.owlmetrics.owlclassexpressions;

import java.util.List;

import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.manchester.cs.bhig.owlmetrics.DoubleValuedMetric;

public class AverageOWLClassExpressionDepthCount extends DoubleValuedMetric {

	SumOfDepthsOfOWLAllValuesFromAxioms allsum;
	SumOfDepthsOfOWLSomeValuesFromAxioms somesum;
	
	public AverageOWLClassExpressionDepthCount(
			OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
		
		allsum = new SumOfDepthsOfOWLAllValuesFromAxioms(owlOntologyManager);
		somesum = new SumOfDepthsOfOWLSomeValuesFromAxioms(owlOntologyManager);
	}

	@Override
	public String getName() {
		return "Average OWLClassExpresssion depth";
	}

	@Override
	protected Double recomputeMetric() {
		allsum.setOntology(getOntology());
		somesum.setOntology(getOntology());
		
		super.setNumerator(allsum.recomputeMetric() + somesum.recomputeMetric());
		super.setDenominator(allsum.getOWLAllValuesFromAxioms().size() + 
				somesum.getOWLSomeValuesFromAxioms().size());
		
		return computeNormalisedMetric();
	}

	@Override
	protected boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		if(allsum.isMetricInvalidated(changes) || somesum.isMetricInvalidated(changes))
			return true;
		else
			return false;
	}

	@Override
	protected void disposeMetric() {

	}

}
