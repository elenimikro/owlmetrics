package uk.ac.manchester.cs.bhig.owlmetrics.owlclass;

import java.util.List;


import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.manchester.cs.bhig.owlmetrics.DoubleValuedMetric;

public class RatioOfDefinedToPrimitiveClassCount extends DoubleValuedMetric {

	public RatioOfDefinedToPrimitiveClassCount(
			OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
	}

	@Override
	public String getName() {
		return "Ratio of defined to primitive classes";
	}

	@Override
	protected Double recomputeMetric() {
		DefinedOWLClassCount defined = new DefinedOWLClassCount(super.getManager());
		PrimitiveOWLClassCount primitives = new PrimitiveOWLClassCount(super.getManager());
		defined.setOntology(super.getOntology());
		primitives.setOntology(super.getOntology());
		
		setNumerator(defined.recomputeMetric());
		setDenominator(primitives.recomputeMetric());
		
		return  super.computeNormalisedMetric();
	}

	@Override
	protected boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		for (OWLOntologyChange chg : changes) {
            if (chg.isAxiomChange() && !chg.getAxiom().getClassesInSignature().isEmpty()) {
                return true;
            }
        }
		return false;
	}

	@Override
	protected void disposeMetric() {

	}

}
