package uk.ac.manchester.cs.bhig.owlmetrics.owlclassexpressions;

import java.util.HashSet;

import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class OWLSomeValuesFromAxiomMaxDepth extends OWLClassExpressionDepth {

	public OWLSomeValuesFromAxiomMaxDepth(OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		return "Max depth of OWLSomeValuesFrom axiom";
	}

	@Override
	protected Integer recomputeMetric() {
		int max = 0;
		HashSet<OWLObjectSomeValuesFrom> restrictions = super.getOWLSomeValuesFromAxioms();
		for(OWLObjectSomeValuesFrom restriction : restrictions){
			int depth = super.getOWLClassExpressionDepth(restriction);
			if(max<depth)
				max = depth;
		}
		return max;
	}


}
