package uk.ac.manchester.cs.bhig.owlmetrics.classification;

import org.semanticweb.owlapi.metrics.OWLMetric;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class JustificationRichnessClassification extends AbstractClassificationMetric{
	
	private double SMALL_JUSTIFICATORY_RICHNESS = 0.0;
	private double MEDIUM_JUSTIFICATORY_RICHNESS = 3.0;
	private double HIGH_JUSTIFICATORY_RICHNESS = 6.0;
	
	

	public JustificationRichnessClassification(
			OWLOntologyManager owlOntologyManager, OWLMetric metric) {
		super(owlOntologyManager, metric);
		
		setClassificationLimits(SMALL_JUSTIFICATORY_RICHNESS, MEDIUM_JUSTIFICATORY_RICHNESS, HIGH_JUSTIFICATORY_RICHNESS);
	}

	@Override
	public String getName() {
		return "Justification richness";
	}
	
}
