package uk.ac.manchester.cs.bhig.owlmetrics.entailments;

import java.util.List;

import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.manchester.cs.bhig.owlmetrics.DoubleValuedMetric;

public class RatioOfEntailmentsToNumberOfAxioms extends DoubleValuedMetric {

	public RatioOfEntailmentsToNumberOfAxioms(
			OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
	}

	@Override
	public String getName() {
		return "Ratio of Entailments to total number of axioms";
	}

	@Override
	protected Double recomputeMetric() {
		setDenominator(getOntology().getAxioms().size());
		return computeNormalisedMetric();
	}

	@Override
	protected boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		for (OWLOntologyChange chg : changes) {
            if (chg.isAxiomChange()) {
                return true;
            }
        }
		return false;
	}

	@Override
	protected void disposeMetric() {

	}
	
	public void setNumerator(Integer sumOfEntailments){
		super.setNumerator(sumOfEntailments);
	}
	

}
