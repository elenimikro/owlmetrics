package uk.ac.manchester.cs.bhig.owlmetrics.owlclass;

import java.util.List;

import org.semanticweb.owlapi.metrics.AxiomTypeMetric;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.manchester.cs.bhig.owlmetrics.DoubleValuedMetric;

public class AverageSubClassAxiomsPerClassCount extends DoubleValuedMetric {

	private AxiomTypeMetric subclasses;
	private OWLClassNumber classes;
	
	public AverageSubClassAxiomsPerClassCount(
			OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
		
		subclasses = new AxiomTypeMetric(owlOntologyManager, AxiomType.SUBCLASS_OF);
		classes = new OWLClassNumber(owlOntologyManager);
	}

	@Override
	public String getName() {
		return "Average SubClassAxioms per class";
	}

	@Override
	protected Double recomputeMetric() {
		subclasses.setOntology(getOntology());
		classes.setOntology(getOntology());
		super.setNumerator(subclasses.getValue());
		super.setDenominator(classes.getValue());
		
		return super.computeNormalisedMetric();
	}

	@Override
	protected boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		if(classes.isMetricInvalidated(changes))
			return true;
		
		else
			return false;
	}

	@Override
	protected void disposeMetric() {

	}

}
