package uk.ac.manchester.cs.bhig.owlmetrics.owlclassexpressions;

import java.util.HashSet;

import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class OWLAllValuesFromAxiomMaxDepth extends OWLClassExpressionDepth {

	public OWLAllValuesFromAxiomMaxDepth(OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		return "Max depth of OWLAllValuesFrom axiom";
	}

	@Override
	protected Integer recomputeMetric() {
		int max = 0;
		HashSet<OWLObjectAllValuesFrom> restrictions = super.getOWLAllValuesFromAxioms();
		for(OWLObjectAllValuesFrom restriction : restrictions){
			int depth = super.getOWLClassExpressionDepth(restriction);
			if(max<depth)
				max = depth;
		}
		return max;
	}


}
