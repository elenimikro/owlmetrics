package uk.ac.manchester.cs.bhig.owlmetrics;

import java.text.DecimalFormat;

import org.semanticweb.owlapi.model.OWLOntologyManager;

public abstract class DoubleValuedMetric extends
		org.semanticweb.owlapi.metrics.DoubleValuedMetric {

	private Integer numerator;
	private Integer denominator;
	
	public DoubleValuedMetric(OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
	}

	protected Double computeNormalisedMetric(){
		DecimalFormat formatter = new DecimalFormat("#.##");
		if(denominator != 0){
			double result = (double) numerator / denominator ;
			return Double.valueOf(formatter.format(result));
		}
		else
			return 0.00; 
	}
	
	protected Integer getNumerator(){
		return numerator;
	}
	
	protected Integer getDenominator(){
		return denominator;
	}
	
	protected void setNumerator(Integer num){
		numerator = num;
	}

	protected void setDenominator(Integer den){
		denominator = den;
	}
	
}
