package uk.ac.manchester.cs.bhig.owlmetrics.owlclassexpressions;

import java.util.HashSet;

import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class OWLAllValuesFromAxiomWithMaxDepth extends OWLClassExpressionDepth {

	public OWLAllValuesFromAxiomWithMaxDepth(
			OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected OWLObjectAllValuesFrom recomputeMetric() {
		int max = 0;
		OWLObjectAllValuesFrom maxExp = null;
		HashSet<OWLObjectAllValuesFrom> restrictions = super.getOWLAllValuesFromAxioms();
		for(OWLObjectAllValuesFrom restriction : restrictions){
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
		return "OWLAllValuesFrom with max depth";
	}


}
