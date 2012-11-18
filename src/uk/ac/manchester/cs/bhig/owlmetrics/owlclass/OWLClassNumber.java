package uk.ac.manchester.cs.bhig.owlmetrics.owlclass;

import java.util.List;

import org.semanticweb.owlapi.metrics.IntegerValuedMetric;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class OWLClassNumber extends IntegerValuedMetric {

	public OWLClassNumber(OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
	}

	@Override
	public String getName() {
		return "Number of OWLClasses";
	}

	@Override
	public Integer recomputeMetric() {
		int numberOfClasses = 0; 

		for (OWLClass cls : super.getOntology().getClassesInSignature(true)) {
			//exclude OWLNothing and OWLThing from counting 
			if(!cls.isOWLNothing() && !cls.isOWLThing()){
				numberOfClasses++;
			}
		}		

		return numberOfClasses;
	}

	@Override
	public boolean isMetricInvalidated(
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
