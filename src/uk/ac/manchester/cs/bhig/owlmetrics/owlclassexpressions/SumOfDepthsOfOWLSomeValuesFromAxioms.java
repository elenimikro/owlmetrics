package uk.ac.manchester.cs.bhig.owlmetrics.owlclassexpressions;

import java.util.HashSet;

import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class SumOfDepthsOfOWLSomeValuesFromAxioms extends OWLClassExpressionDepth {
	

	public SumOfDepthsOfOWLSomeValuesFromAxioms(
			OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
	}

	@Override
	public String getName() {
		return "Sum of depths of OWLSomeValuesFrom axioms";
	}

	@Override
	protected Integer recomputeMetric() {
		int sum = 0;
		HashSet<OWLObjectSomeValuesFrom> restrictions = super.getOWLSomeValuesFromAxioms();
		for(OWLObjectSomeValuesFrom restriction : restrictions){
			int depth = super.getOWLClassExpressionDepth(restriction);
			sum += depth;
		}
		return sum;
	}	
	

}
