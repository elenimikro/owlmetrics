package uk.ac.manchester.cs.bhig.owlmetrics.owlindividual;

import java.util.List;

import org.semanticweb.owlapi.metrics.IntegerValuedMetric;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class OWLIndividualCount extends IntegerValuedMetric {

	public OWLIndividualCount(OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
	}

	@Override
	public String getName() {
		return "Number of OWLIndividuals";
	}

	@Override
	public Integer recomputeMetric() {
		int indiNo = 0;
		for (OWLIndividual indi : super.getOntology().getIndividualsInSignature()) {
			if(!indi.isTopEntity() && !indi.isBottomEntity()){
				indiNo++;
			}
		}		

		return indiNo;
	}

	@Override
	public boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		for (OWLOntologyChange chg : changes) {
            if (chg.isAxiomChange() && !chg.getAxiom().getIndividualsInSignature().isEmpty()) {
                return true;
            }
        }
		return false;
	}

	@Override
	protected void disposeMetric() {

	}

}
