package uk.ac.manchester.cs.bhig.owlmetrics;

import java.util.Set;

import org.semanticweb.owlapi.metrics.OWLMetric;
import org.semanticweb.owlapi.model.OWLAxiom;

public interface AxiomTypeCount<M> extends OWLMetric<M>{


	public Set<? extends OWLAxiom> getAxioms();
}
