package uk.ac.manchester.cs.bhig.owlmetrics.entailments;

import java.util.List;

import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;

import uk.ac.manchester.cs.bhig.owlmetrics.DoubleValuedMetric;

public class PercentageOfEquivalentClassEntailments extends DoubleValuedMetric {

	public PercentageOfEquivalentClassEntailments(
			OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
	}

	@Override
	public String getName() {
		return "Percentage of Equivalent class entailments";
	}

	@Override
	protected Double recomputeMetric() {
		
		return computeNormalisedMetric();
	}

	@Override
	protected boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		for (OWLOntologyChange chg : changes) {
            if (chg.isAxiomChange() && (chg.getAxiom() instanceof OWLSubClassOfAxiom || 
            		chg.getAxiom() instanceof OWLEquivalentClassesAxiom || 
            		chg.getAxiom() instanceof OWLSubObjectPropertyOfAxiom ||
            		chg.getAxiom() instanceof OWLPropertyAssertionAxiom ||
            		chg.getAxiom() instanceof OWLEquivalentObjectPropertiesAxiom ||
            		chg.getAxiom() instanceof OWLClassAssertionAxiom)) {
                return true;
            }
        }
		return false;
	}

	@Override
	protected void disposeMetric() {

	}
	
	protected void setNumerator(OWLEquivalentClassEntailmentCount equivClassEnt){
		super.setNumerator(equivClassEnt.recomputeMetric());
	}
	
	public void setDenominator(Integer sumOfEntailments){
		super.setDenominator(sumOfEntailments);
	}

}
