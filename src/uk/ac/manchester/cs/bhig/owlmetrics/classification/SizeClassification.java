package uk.ac.manchester.cs.bhig.owlmetrics.classification;

import org.semanticweb.owlapi.metrics.OWLMetric;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class SizeClassification extends
		AbstractClassificationMetric {
	
	//size variables 
	private int SMALL_SIZE = 0;
	private int MEDIUM_SIZE = 50;
	private int BIG_SIZE = 1000;

	public SizeClassification(
			OWLOntologyManager owlOntologyManager, OWLMetric metric) {
		super(owlOntologyManager, metric);
		
		setClassificationLimits(SMALL_SIZE, MEDIUM_SIZE, BIG_SIZE);
	}

	@Override
	public String getName() {
		return "Size";
	}

}
