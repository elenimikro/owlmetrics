package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owloblectproperty;

import java.util.List;

import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import uk.ac.manchester.cs.bhig.owlmetrics.DoubleValuedMetric;

public class AverageObjectPropertyHierarchyDepth extends DoubleValuedMetric {

	SumOfDepthsInOWLObjectPropertyHierarchy sumDepths;
	NumberOfOWLObjectPropertyPaths paths;
	
	public AverageObjectPropertyHierarchyDepth(OWLOntologyManager owlOntologyManager, OWLReasoner reasoner) {
		super(owlOntologyManager);
		sumDepths = new SumOfDepthsInOWLObjectPropertyHierarchy(owlOntologyManager, reasoner);
		paths = new NumberOfOWLObjectPropertyPaths(owlOntologyManager, reasoner);
	}

	@Override
	public String getName() {
		return "Average ObjectProperty hierarchy depth";
	}

	@Override
	protected Double recomputeMetric() {
		sumDepths.setOntology(getOntology());
		paths.setOntology(getOntology());
		
		setNumerator(sumDepths.recomputeMetric());
		setDenominator(paths.recomputeMetric());
		
		return computeNormalisedMetric();
	}

	@Override
	protected boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		if(sumDepths.isMetricInvalidated(changes) || paths.isMetricInvalidated(changes))
			return true;
		else
			return false;
	}

	@Override
	protected void disposeMetric() {
		
	}

}
