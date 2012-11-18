package uk.ac.manchester.cs.bhig.owlmetrics.entailments;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.metrics.IntegerValuedMetric;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.InferredEquivalentClassAxiomGenerator;

import uk.ac.manchester.cs.bhig.owlmetrics.AxiomTypeCount;

public class OWLEquivalentClassEntailmentCount extends IntegerValuedMetric implements AxiomTypeCount<Integer>{

	private OWLReasoner reasoner;
	private Set<OWLEquivalentClassesAxiom> entailmentSet = new HashSet<OWLEquivalentClassesAxiom>();
	
	public OWLEquivalentClassEntailmentCount(OWLOntologyManager owlOntologyManager, OWLReasoner reasoner) {
		super(owlOntologyManager);
		this.reasoner = reasoner;
	}

	@Override
	public String getName() {
		return "Number of OWLEquivalentClass entailments";
	}

	@Override
	protected Integer recomputeMetric() {
		
		return getOWLEquivalentClassEntailments().size();
	}
	
	protected Set<OWLEquivalentClassesAxiom> getOWLEquivalentClassEntailments(){
		
		InferredEquivalentClassAxiomGenerator gen = new InferredEquivalentClassAxiomGenerator();
		
		if(entailmentSet.isEmpty())
			entailmentSet = gen.createAxioms(getManager(), reasoner);
		
		
		return entailmentSet;
	}

	@Override
	protected boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		 for (OWLOntologyChange chg : changes) {
	            if (chg.isAxiomChange() && chg.getAxiom() instanceof OWLSubClassOfAxiom) {
	            	entailmentSet.clear();
	                return true;
	            }
	        }
		return false;
	}

	@Override
	protected void disposeMetric() {
		
	}

	@Override
	public Set<? extends OWLAxiom> getAxioms() {
		return entailmentSet;
	}

}
