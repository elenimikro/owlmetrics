package uk.ac.manchester.cs.bhig.owlmetrics.owlobjectproperty;

import java.util.List;

import org.semanticweb.owlapi.metrics.IntegerValuedMetric;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class OWLObjectPropertiesCount extends IntegerValuedMetric {

	public OWLObjectPropertiesCount(OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
	}

	@Override
	public String getName() {
		return "Number of OWLObject properties";
	}

	@Override
	public Integer recomputeMetric() {
		int propno=0;
		for (OWLObjectProperty prop : super.getOntology().getObjectPropertiesInSignature()) {
			if(!prop.isOWLBottomObjectProperty() && !prop.isOWLTopObjectProperty()){
				propno++;
			}
		}		

		return propno;
	}

	@Override
	public boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		for (OWLOntologyChange chg : changes) {
            if (chg.isAxiomChange() && !chg.getAxiom().getObjectPropertiesInSignature().isEmpty()) {
                return true;
            }
        }
		return false;
	}

	@Override
	protected void disposeMetric() {

	}

}
