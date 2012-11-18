package uk.ac.manchester.cs.bhig.owlmetrics.owlclassexpressions;

import java.util.HashSet;

import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class OWLSomeValuesFromAxiomWithMaxDepth extends OWLClassExpressionDepth {

	public OWLSomeValuesFromAxiomWithMaxDepth(
			OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
	}
	
	@Override
	protected OWLObjectSomeValuesFrom recomputeMetric() {
		int max = 0;
		OWLObjectSomeValuesFrom maxExp = null;
		HashSet<OWLObjectSomeValuesFrom> restrictions = super.getOWLSomeValuesFromAxioms();
		for(OWLObjectSomeValuesFrom restriction : restrictions){
			int depth = super.getOWLClassExpressionDepth(restriction);
			if(max<depth){
				max = depth;
				maxExp = restriction;
			}	
		}
		return maxExp;
	}

	@Override
	public String getName() {
		return "OWLSomeValuesFrom with max depth";
	}


}
