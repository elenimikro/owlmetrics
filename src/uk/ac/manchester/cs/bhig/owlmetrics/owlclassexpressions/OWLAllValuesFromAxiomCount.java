package uk.ac.manchester.cs.bhig.owlmetrics.owlclassexpressions;

import org.semanticweb.owlapi.model.OWLOntologyManager;

public class OWLAllValuesFromAxiomCount extends OWLClassExpressionVisitor{

	public OWLAllValuesFromAxiomCount(OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
	}

	@Override
	public String getName() {
		return "Number of OWLAllValuesFrom axioms";
	}

	@Override
	protected Integer recomputeMetric() {
		return super.getOWLAllValuesFromAxioms().size();
	}


}
