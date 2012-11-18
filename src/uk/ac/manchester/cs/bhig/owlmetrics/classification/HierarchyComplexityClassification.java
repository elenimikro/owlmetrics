package uk.ac.manchester.cs.bhig.owlmetrics.classification;

import org.semanticweb.owlapi.metrics.OWLMetric;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class HierarchyComplexityClassification extends
		AbstractClassificationMetric {
	
	//class hierarchy complexity 
	private double SMALL_HIERARCHY_COMPLEXITY = 0.0;
	private double MEDIUM_HIERARCHY_COMPLEXITY = 1.0;
	private double HIGH_HIERARCHY_COMPLEXITY = 3.0;

	public HierarchyComplexityClassification(
			OWLOntologyManager owlOntologyManager, OWLMetric metric) {
		super(owlOntologyManager, metric);
		
		setClassificationLimits(SMALL_HIERARCHY_COMPLEXITY, MEDIUM_HIERARCHY_COMPLEXITY, HIGH_HIERARCHY_COMPLEXITY);
	}

	@Override
	public String getName() {
		return "Hierarchy complexity";
	}

}
