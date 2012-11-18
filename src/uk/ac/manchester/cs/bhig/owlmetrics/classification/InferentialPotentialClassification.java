package uk.ac.manchester.cs.bhig.owlmetrics.classification;

import org.semanticweb.owlapi.metrics.OWLMetric;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class InferentialPotentialClassification extends
		AbstractClassificationMetric {
	
	private double SMALL_INFERENTIAL_POTENTIAL = 0.0;
	private double MEDIUM_INFERENTIAL_POTENTIAL = 0.5;
	private double HIGH_INFERENTIAL_POTENTIAL = 2.0;

	public InferentialPotentialClassification(
			OWLOntologyManager owlOntologyManager, OWLMetric metric) {
		super(owlOntologyManager, metric);
		
		setClassificationLimits(SMALL_INFERENTIAL_POTENTIAL, 
				MEDIUM_INFERENTIAL_POTENTIAL, HIGH_INFERENTIAL_POTENTIAL);
	}

	@Override
	public String getName() {
		return "Inferential potential classification";
	}

}
