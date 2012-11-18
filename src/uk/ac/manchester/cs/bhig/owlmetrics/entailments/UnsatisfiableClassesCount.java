package uk.ac.manchester.cs.bhig.owlmetrics.entailments;

import java.util.ArrayList;
import java.util.List;

import org.semanticweb.owlapi.metrics.IntegerValuedMetric;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

public class UnsatisfiableClassesCount extends IntegerValuedMetric {
	
	OWLReasoner reasoner;

	public UnsatisfiableClassesCount(OWLOntologyManager owlOntologyManager, OWLReasoner reasoner) {
		super(owlOntologyManager);
		this.reasoner = reasoner;
	}

	@Override
	public String getName() {
		return "Number of unsatisfiable classes";
	}

	@Override
	protected Integer recomputeMetric() {
		ArrayList<OWLClass> un = new ArrayList<OWLClass>();
		for(OWLClass cls : getOntology().getClassesInSignature()){
			if(!cls.isBottomEntity())
				if(!reasoner.isSatisfiable(cls))
					un.add(cls);
		}
		
		return un.size();
	}

	@Override
	protected boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		
		return true;
	}

	@Override
	protected void disposeMetric() {
		
	}

}
