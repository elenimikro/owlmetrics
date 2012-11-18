package uk.ac.manchester.cs.bhig.owlmetrics.classification;

import org.semanticweb.owlapi.metrics.OWLMetric;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class HierarchyWidenessClassification extends
		AbstractClassificationMetric {
	
	//class hierarchy wideness
	private double SMALL_HIERARCHY_WIDENESS = 0.0;
	private double MEDIUM_HIERARCHY_WIDENESS = 4.0;
	private double HIGH_HIERARCHY_WIDENESS = 10.0;

	public HierarchyWidenessClassification(
			OWLOntologyManager owlOntologyManager, OWLMetric metric) {
		super(owlOntologyManager, metric);
		
		setClassificationLimits(SMALL_HIERARCHY_WIDENESS, MEDIUM_HIERARCHY_WIDENESS, HIGH_HIERARCHY_WIDENESS);
	}

	@Override
	public String getName() {
		return "Hierarchy wideness";
	}

}
