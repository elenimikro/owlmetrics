package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owlclass;

import java.util.List;

import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import uk.ac.manchester.cs.bhig.owlmetrics.DoubleValuedMetric;
import uk.ac.manchester.cs.bhig.owlmetrics.owlclass.OWLClassNumber;

public class AverageNumberOfSubClassesPerClass extends DoubleValuedMetric {

	TotalNumberOfSubClassesCount subclasses;
	OWLClassNumber classes;
	
	public AverageNumberOfSubClassesPerClass(
			OWLOntologyManager owlOntologyManager, OWLReasoner reasoner) {
		super(owlOntologyManager);
		subclasses = new TotalNumberOfSubClassesCount(owlOntologyManager, reasoner);
		classes = new OWLClassNumber(owlOntologyManager);
	}

	@Override
	public String getName() {
		return "Average number of SubClasses per class";
	}

	@Override
	protected Double recomputeMetric() {
		subclasses.setOntology(getOntology());
		classes.setOntology(getOntology());
	
		setNumerator(subclasses.recomputeMetric());
		setDenominator(classes.recomputeMetric());
		
		return computeNormalisedMetric();
	}

	@Override
	protected boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		if(subclasses.isMetricInvalidated(changes) || classes.isMetricInvalidated(changes))
			return true;
		else
			return false;
	}

	@Override
	protected void disposeMetric() {

	}

}
