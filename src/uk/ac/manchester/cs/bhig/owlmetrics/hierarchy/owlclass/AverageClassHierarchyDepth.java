package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owlclass;

import java.util.List;

import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import uk.ac.manchester.cs.bhig.owlmetrics.DoubleValuedMetric;

public class AverageClassHierarchyDepth extends DoubleValuedMetric {
	
	NumberOfOWLClassPaths paths;
	SumOfDepthsInOWLClassHierarchy depthSum;

	public AverageClassHierarchyDepth(OWLOntologyManager owlOntologyManager, OWLReasoner reasoner) {
		super(owlOntologyManager);
		
		paths = new NumberOfOWLClassPaths(owlOntologyManager, reasoner);
		depthSum = new SumOfDepthsInOWLClassHierarchy(owlOntologyManager, reasoner);
	}

	@Override
	public String getName() {
		return "Average class hierarchy depth";
	}

	@Override
	protected Double recomputeMetric() {
		paths.setOntology(getOntology());
		depthSum.setOntology(getOntology());
		
		setNumerator(depthSum.recomputeMetric());
		setDenominator(paths.recomputeMetric());
		
		return computeNormalisedMetric();
	}

	@Override
	protected boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		if(depthSum.isMetricInvalidated(changes) || paths.isMetricInvalidated(changes))
			return true;
		else
			return false;
	}

	@Override
	protected void disposeMetric() {

	}

}
