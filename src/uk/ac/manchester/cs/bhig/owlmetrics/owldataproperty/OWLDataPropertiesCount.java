package uk.ac.manchester.cs.bhig.owlmetrics.owldataproperty;

import java.util.List;

import org.semanticweb.owlapi.metrics.IntegerValuedMetric;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class OWLDataPropertiesCount extends IntegerValuedMetric {

	public OWLDataPropertiesCount(OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
	}

	@Override
	public String getName() {
		return "Number of OWLData properties";
	}

	@Override
	public Integer recomputeMetric() {
		int propno=0;
		for (OWLDataProperty prop : super.getOntology().getDataPropertiesInSignature()) {
			if(!prop.isOWLBottomDataProperty() && !prop.isOWLTopDataProperty()){
				propno++;
			}
		}		

		return propno;
	}

	@Override
	public boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		for (OWLOntologyChange chg : changes) {
            if (chg.isAxiomChange() && !chg.getAxiom().getDataPropertiesInSignature().isEmpty()) {
                return true;
            }
        }
		return false;
	}

	@Override
	protected void disposeMetric() {

	}

}
