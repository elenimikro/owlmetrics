package uk.ac.manchester.cs.bhig.owlmetrics.owlclassexpressions;

import java.util.HashSet;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

public class SumOfClassExpressionUsage extends OWLClassExpressionUsageCount {

	public SumOfClassExpressionUsage(OWLOntologyManager owlOntologyManager,
			OWLReasoner reasoner) {
		super(owlOntologyManager, reasoner);
	}

	@Override
	public String getName() {
		return "Sum of class expression usage";
	}

	@Override
	protected Integer recomputeMetric() {
		int sum = 0;
		HashSet<OWLClassExpression> clasex = new HashSet<OWLClassExpression>();
		clasex.addAll(super.getOWLAllValuesFromAxioms());
		clasex.addAll(super.getOWLSomeValuesFromAxioms());
		
		for(OWLClassExpression clex : clasex)
			sum += super.getClassEpressionUsage(clex).size();
		
		return sum;
	}

}
