package uk.ac.manchester.cs.bhig.owlmetrics.classification;

import java.util.List;

import org.semanticweb.owlapi.metrics.AbstractOWLMetric;
import org.semanticweb.owlapi.metrics.OWLMetric;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public abstract class AbstractClassificationMetric extends AbstractOWLMetric<Object> implements OWLMetricClassification{
	
	private double low;
	private double middle;
	private double high;

	private OWLMetric metric;
	
	public AbstractClassificationMetric(OWLOntologyManager owlOntologyManager, OWLMetric metric) {
		super(owlOntologyManager);
		setOWLMetric(metric);
	}
	
	protected void setClassificationLimits(double lowLimit, double middleLimit, double upperLimit){
		low = lowLimit;
		middle = middleLimit;
		high = upperLimit;
	}
	
	public String getClassification(){
		metric.setOntology(getOntology());
		double value = Double.parseDouble(metric.getValue().toString());
		String classification = "n/a";
		if(value >= high){
			classification = "High";
		}
		else if(value >= middle){
			classification = "Medium";
		}
		else if (value >= low){
			classification = "Small";
		}
		else{
			System.out.println("Invalid metric " 
					+ value);
		}
		
		return classification;
	}
	

	public void setOWLMetric(OWLMetric metric) {
		this.metric = metric;
	}
	
	public OWLMetric getOWLMetric(){
		return metric;
	}
	
	@Override
	protected void disposeMetric() {
		
	}
	
	@Override
	protected boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		return true;
	}
	
	@Override
	protected Object recomputeMetric() {
		return getOWLMetric().getValue();
	}
	
	public String getOWLMetricName() {
		return getOWLMetric().getName();
	}
	
}
