package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owlclass;

import java.util.List;

import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import uk.ac.manchester.cs.bhig.owlmetrics.DoubleValuedMetric;
import uk.ac.manchester.cs.bhig.owlmetrics.owlclass.OWLClassNumber;

public class AverageNumberOfSuperClassesPerClassCount extends
		DoubleValuedMetric {

	private TotalNumberOfSuperClasses superSum;
	private OWLClassNumber classes;
	
	public AverageNumberOfSuperClassesPerClassCount(
			OWLOntologyManager owlOntologyManager, OWLReasoner reasoner) {
		super(owlOntologyManager);

		superSum = new TotalNumberOfSuperClasses(owlOntologyManager, reasoner);
		classes = new OWLClassNumber(owlOntologyManager);
		
	}

	@Override
	public String getName() {
		return "Average number of superclasses per class";
	}

	@Override
	protected Double recomputeMetric() {
		superSum.setOntology(super.getOntology());
		classes.setOntology(super.getOntology());
		
		setNumerator(superSum.recomputeMetric());
		setDenominator(classes.recomputeMetric());
		
		return super.computeNormalisedMetric();
	}

	@Override
	protected boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		if(superSum.isMetricInvalidated(changes) || classes.isMetricInvalidated(changes))
			return true;
		else
			return false;
	}

	@Override
	protected void disposeMetric() {

	}

}
