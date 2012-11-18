package uk.ac.manchester.cs.bhig.owlmetrics.owlclassexpressions;

import java.util.ArrayList;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

public abstract class OWLClassExpressionUsageCount extends OWLClassExpressionVisitor {

	private OWLReasoner reasoner;
	public OWLClassExpressionUsageCount(OWLOntologyManager owlOntologyManager, OWLReasoner reasoner) {
		super(owlOntologyManager);
		
		this.reasoner = reasoner;
	}


	
	private ArrayList<OWLClass> getAssertedClassExpressionUsage(OWLClassExpression clex) {
		ArrayList<OWLClass> usageSet = new ArrayList<OWLClass>();
		
		for (OWLClass cls : getOntology().getClassesInSignature()) {
			for (OWLClassExpression exp : cls.getSuperClasses( getOntology())) {
				if (exp.equals(clex)) {
					usageSet.add(cls);
				}

			}
		}

		return usageSet;
	}
	
	protected ArrayList<OWLClass> getClassEpressionUsage(OWLClassExpression clex){
		ArrayList<OWLClass> usageSet = new ArrayList<OWLClass>();
		
		if (reasoner.getReasonerName().equals("Structural Reasoner"))
			usageSet = getAssertedClassExpressionUsage(clex);
		else{
			for (Node<OWLClass> child : reasoner.getSubClasses(clex, true)) {
				if (!child.isBottomNode()) {
					usageSet.addAll(child.getEntities());
				}
			}
		}
		
		return usageSet;
		
	}
	
	@Override
	protected void disposeMetric() {

	}

}
