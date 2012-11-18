package uk.ac.manchester.cs.bhig.owlmetrics.owlclassexpressions;

import org.semanticweb.owlapi.model.OWLOntologyManager;

public class OWLSomeValuesFromAxiomCount extends OWLClassExpressionVisitor{

	public OWLSomeValuesFromAxiomCount(OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
	}

	@Override
	public String getName() {
		return "Number of OWLSomeValuesFrom axioms";
	}

	@Override
	protected Integer recomputeMetric() {
		return super.getOWLSomeValuesFromAxioms().size();
	}


}
