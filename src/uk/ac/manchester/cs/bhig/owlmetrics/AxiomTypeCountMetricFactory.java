package uk.ac.manchester.cs.bhig.owlmetrics;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.metrics.AxiomTypeMetric;
import org.semanticweb.owlapi.metrics.OWLMetric;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class AxiomTypeCountMetricFactory {
	
	
	public static Set<OWLMetric<?>> createMetrics(OWLOntologyManager manager) {
		Set<OWLMetric<?>> metrics = new HashSet<OWLMetric<?>>();
		for(AxiomType<?> axiomType : AxiomType.AXIOM_TYPES) {
			if(!axiomType.equals(AxiomType.OBJECT_PROPERTY_DOMAIN) 
					&& !axiomType.equals(AxiomType.OBJECT_PROPERTY_RANGE) 
					&& !axiomType.equals(AxiomType.OBJECT_PROPERTY_ASSERTION)
					&& !axiomType.equals(AxiomType.EQUIVALENT_CLASSES)
					&& !axiomType.equals(AxiomType.OBJECT_PROPERTY_ASSERTION)
					&& !axiomType.equals(AxiomType.CLASS_ASSERTION))
				
					metrics.add(new AxiomTypeMetric(manager, axiomType));
		}
		return metrics;
	}
	


}
