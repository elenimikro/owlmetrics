package uk.ac.manchester.cs.bhig.owlmetrics.classification;

import org.semanticweb.owlapi.metrics.OWLMetric;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class InferentialRichnessClassification extends AbstractClassificationMetric{
	
	private double SMALL_INFERENTIAL_RICHNESS = 0.0;
	private double MEDIUM_INFERENTIAL_RICHNESS = 0.5;
	private double HIGH_INFERENTIAL_RICHNESS = 1.0;
	
	

	public InferentialRichnessClassification(
			OWLOntologyManager owlOntologyManager, OWLMetric metric) {
		super(owlOntologyManager, metric);
		
		setClassificationLimits(SMALL_INFERENTIAL_RICHNESS, MEDIUM_INFERENTIAL_RICHNESS, HIGH_INFERENTIAL_RICHNESS);
	}

	@Override
	public String getName() {
		return "Inferential richness";
	}
	
}
