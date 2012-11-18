package uk.ac.manchester.cs.bhig.owlmetrics.utilities;

import java.util.List;

import org.semanticweb.owlapi.metrics.OWLMetric;
import org.semanticweb.owlapi.metrics.OWLMetricManager;

public class OWLExtendedMetricManager extends OWLMetricManager {
	
	private List<OWLMetric<?>> metrics;

	public OWLExtendedMetricManager(List<OWLMetric<?>> metrics) {
		super(metrics);
		this.metrics = metrics;
	}
	
	public String toString() {
        StringBuilder sb = new StringBuilder();
        int counter=1;
        for (OWLMetric m : metrics) {
        	sb.append(counter + ") ");
            sb.append(m);
            sb.append("\n");
            counter++;
        }
        return sb.toString();
    }

}
