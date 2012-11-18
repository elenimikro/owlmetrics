package uk.ac.manchester.cs.bhig.owlmetrics;

import java.util.LinkedHashMap;

import org.semanticweb.owlapi.metrics.OWLMetric;


public interface CountableEntity<M> extends OWLMetric<M>{

	public LinkedHashMap<?, Integer> getEntitiesWithValue();

}
