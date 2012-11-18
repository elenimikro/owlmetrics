package uk.ac.manchester.cs.bhig.owlmetrics.classification;

import org.semanticweb.owlapi.metrics.OWLMetric;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class ClassExpressionComplexityClassification extends
		AbstractClassificationMetric {
	
	//class expression 
	private double SMALL_CLASS_EXPRESSION_COMPLEXITY = 0.0;
	private double MEDIUM_CLASS_EXPRESSION_COMPLEXITY = 0.05;
	private double HIGH_CLASS_EXPRESSION_COMPLEXITY = 0.1;

	public ClassExpressionComplexityClassification(
			OWLOntologyManager owlOntologyManager, OWLMetric metric) {
		super(owlOntologyManager, metric);
		
		setClassificationLimits(SMALL_CLASS_EXPRESSION_COMPLEXITY, 
				MEDIUM_CLASS_EXPRESSION_COMPLEXITY, HIGH_CLASS_EXPRESSION_COMPLEXITY);
	}

	@Override
	public String getName() {
		return "Class expression complexity";
	}

}
