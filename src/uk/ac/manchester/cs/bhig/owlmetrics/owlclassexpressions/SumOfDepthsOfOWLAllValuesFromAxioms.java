package uk.ac.manchester.cs.bhig.owlmetrics.owlclassexpressions;

import java.util.HashSet;

import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class SumOfDepthsOfOWLAllValuesFromAxioms extends OWLClassExpressionDepth {
	

	public SumOfDepthsOfOWLAllValuesFromAxioms(
			OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
	}

	@Override
	public String getName() {
		return "Sum of depths of OWLAllValuesFrom axioms";
	}

	@Override
	protected Integer recomputeMetric() {
		int sum = 0;
		HashSet<OWLObjectAllValuesFrom> restrictions = super.getOWLAllValuesFromAxioms();
		for(OWLObjectAllValuesFrom restriction : restrictions){
			int depth = super.getOWLClassExpressionDepth(restriction);
			sum += depth;
		}
		return sum;
	}	
	

}
