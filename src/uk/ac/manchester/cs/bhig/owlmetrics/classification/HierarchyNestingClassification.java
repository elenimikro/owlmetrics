package uk.ac.manchester.cs.bhig.owlmetrics.classification;

import org.semanticweb.owlapi.metrics.OWLMetric;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class HierarchyNestingClassification extends
		AbstractClassificationMetric {
	
	//class hierarchy nesting 
	private double SMALL_HIERARCHY_NESTING = 0.0;
	private double MEDIUM_HIERARCHY_NESTING = 5.0;
	private double HIGH_HIERARCHY_NESTING = 10.0;

	public HierarchyNestingClassification(
			OWLOntologyManager owlOntologyManager, OWLMetric metric) {
		super(owlOntologyManager, metric);
		
		setClassificationLimits(SMALL_HIERARCHY_NESTING, MEDIUM_HIERARCHY_NESTING, HIGH_HIERARCHY_NESTING);
	}

	@Override
	public String getName() {
		return "Hierarchy nesting";
	}

}
