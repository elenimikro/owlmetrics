package uk.ac.manchester.cs.bhig.owlmetrics.owlclass;

import java.util.HashSet;
import java.util.List;

import org.semanticweb.owlapi.metrics.IntegerValuedMetric;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class DefinedOWLClassCount extends IntegerValuedMetric {

	public DefinedOWLClassCount(OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
	}

	@Override
	public String getName() {
		return "Number of defined OWLClasses";
	}

	@Override
	protected Integer recomputeMetric() {
		return getDefinedOWLClasses().size();
	}
	
	public  HashSet<OWLClass> getDefinedOWLClasses(){
		HashSet<OWLClass> defined = new HashSet<OWLClass>();
		for(OWLClass cl : super.getOntology().getClassesInSignature()){
			if(cl.isDefined(super.getOntology()) && !cl.isBottomEntity())
				defined.add(cl);
		}
		return defined;
	}

	@Override
	protected boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		for (OWLOntologyChange chg : changes) {
            if (chg.isAxiomChange() && !chg.getAxiom().isOfType(AxiomType.EQUIVALENT_CLASSES)) {
                return true;
            }
        }
		return false;
	}

	@Override
	protected void disposeMetric() {
	
	}

}
