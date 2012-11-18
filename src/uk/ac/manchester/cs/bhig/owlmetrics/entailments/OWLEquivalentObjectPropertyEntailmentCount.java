package uk.ac.manchester.cs.bhig.owlmetrics.entailments;

import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.metrics.IntegerValuedMetric;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.InferredEquivalentObjectPropertyAxiomGenerator;

public class OWLEquivalentObjectPropertyEntailmentCount extends IntegerValuedMetric {

	private OWLReasoner reasoner;
	
	public OWLEquivalentObjectPropertyEntailmentCount(OWLOntologyManager owlOntologyManager, OWLReasoner reasoner) {
		super(owlOntologyManager);
		this.reasoner = reasoner;
	}

	@Override
	public String getName() {
		return "Number of OWLEquivalentObjectProperty entailments";
	}

	@Override
	protected Integer recomputeMetric() {
		
		return getOWLEquivalentPropertyEntailments().size();
	}
	
	protected Set<OWLEquivalentObjectPropertiesAxiom> getOWLEquivalentPropertyEntailments(){
		
		InferredEquivalentObjectPropertyAxiomGenerator gen = new InferredEquivalentObjectPropertyAxiomGenerator();
		Set<OWLEquivalentObjectPropertiesAxiom> entailmentSet = gen.createAxioms(getManager(), reasoner);
		
		
		return entailmentSet;
	}

	@Override
	protected boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		 for (OWLOntologyChange chg : changes) {
	            if (chg.isAxiomChange() && chg.getAxiom() instanceof OWLObjectProperty) {
	                return true;
	            }
	        }
		return false;
	}

	@Override
	protected void disposeMetric() {
		
	}

}
