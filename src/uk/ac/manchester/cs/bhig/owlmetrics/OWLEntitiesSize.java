package uk.ac.manchester.cs.bhig.owlmetrics;

import java.util.List;

import org.semanticweb.owlapi.metrics.IntegerValuedMetric;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class OWLEntitiesSize extends IntegerValuedMetric {

	public OWLEntitiesSize(OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
		
	}

	@Override
	public String getName() {
		return "OWLEntities size";
	}

	@Override
	protected Integer recomputeMetric() {
		return getOntology().getClassesInSignature().size() + 
			   getOntology().getObjectPropertiesInSignature().size() +
			   getOntology().getDataPropertiesInSignature().size() + 
			   getOntology().getIndividualsInSignature().size();
	}

	@Override
	protected boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		for (OWLOntologyChange chg : changes) {
            if (chg.isAxiomChange() && (!chg.getAxiom().getClassesInSignature().isEmpty() || 
            		!chg.getAxiom().getObjectPropertiesInSignature().isEmpty() ||
            		!chg.getAxiom().getDataPropertiesInSignature().isEmpty() ||
            		!chg.getAxiom().getIndividualsInSignature().isEmpty())) {
                return true;
            }
        }
		return false;
	}

	@Override
	protected void disposeMetric() {

	}

}
