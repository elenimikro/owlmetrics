package uk.ac.manchester.cs.bhig.owlmetrics.owlclass;

import java.util.HashSet;
import java.util.List;

import org.semanticweb.owlapi.metrics.IntegerValuedMetric;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class PrimitiveOWLClassCount extends IntegerValuedMetric {

	public PrimitiveOWLClassCount(OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
	}

	@Override
	public String getName() {
		return "Number of primitive OWLClasses";
	}

	@Override
	protected Integer recomputeMetric() {
		return getPrimitiveOWLClasses().size();
	}

	public  HashSet<OWLClass> getPrimitiveOWLClasses(){
		HashSet<OWLClass> primitives = new HashSet<OWLClass>();
		for(OWLClass cl : super.getOntology().getClassesInSignature()){
			if(!cl.isDefined(super.getOntology()))
				primitives.add(cl);
		}
		return primitives;
	}
	
	@Override
	protected boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		for (OWLOntologyChange chg : changes) {
            if (chg.isAxiomChange() && !chg.getAxiom().getClassesInSignature().isEmpty()) {
                return true;
            }
        }
		return false;
	}

	@Override
	protected void disposeMetric() {
		
	}

}
