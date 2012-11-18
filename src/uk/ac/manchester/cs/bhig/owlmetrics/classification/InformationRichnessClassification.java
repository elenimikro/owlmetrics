package uk.ac.manchester.cs.bhig.owlmetrics.classification;

import org.semanticweb.owlapi.metrics.OWLMetric;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class InformationRichnessClassification extends
		AbstractClassificationMetric {
	
	//information richness variables
	private double SMALL_INFORMATION_RICHNESS = 0.0;
	private double MEDIUM_INFORMATION_RICHNESS = 1.0;
	private double HIGH_INFORMATION_RICHNESS = 2.0;

	public InformationRichnessClassification(
			OWLOntologyManager owlOntologyManager, OWLMetric metric) {
		super(owlOntologyManager, metric);
		
		setClassificationLimits(SMALL_INFORMATION_RICHNESS, MEDIUM_INFORMATION_RICHNESS, HIGH_INFORMATION_RICHNESS);
	}

	@Override
	public String getName() {
		return "Information Richness";
	}

}
