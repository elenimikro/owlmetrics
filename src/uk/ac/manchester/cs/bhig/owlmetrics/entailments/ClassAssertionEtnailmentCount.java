package uk.ac.manchester.cs.bhig.owlmetrics.entailments;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.metrics.IntegerValuedMetric;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.InferredClassAssertionAxiomGenerator;

import uk.ac.manchester.cs.bhig.owlmetrics.AxiomTypeCount;

public class ClassAssertionEtnailmentCount extends IntegerValuedMetric implements AxiomTypeCount<Integer>{
	
	
	private OWLReasoner reasoner;
	private Set<OWLClassAssertionAxiom> entailmentSet = new HashSet<OWLClassAssertionAxiom>();
	
	public ClassAssertionEtnailmentCount(OWLOntologyManager owlOntologyManager, OWLReasoner reasoner) {
		super(owlOntologyManager);
		this.reasoner = reasoner;
	}

	@Override
	public String getName() {
		return "Class assertion entailments";
	}

	@Override
	protected Integer recomputeMetric() {
		return getClassAssertionEntailments().size();
	}

	protected Set<OWLClassAssertionAxiom> getClassAssertionEntailments(){
		OWLClass thing = getManager().getOWLDataFactory().getOWLThing();
		OWLClass nothing = getManager().getOWLDataFactory().getOWLNothing();
		
		InferredClassAssertionAxiomGenerator gen = new InferredClassAssertionAxiomGenerator();
		
		if(entailmentSet.isEmpty())
			entailmentSet = gen.createAxioms(getManager(), reasoner);
		
		HashSet<OWLClassAssertionAxiom> redundant = new HashSet<OWLClassAssertionAxiom>();
		for(OWLClassAssertionAxiom ax : entailmentSet){
			
			if(ax.getClassesInSignature().contains(nothing) || ax.getClassesInSignature().contains(thing))
				redundant.add(ax);
		}

		for(OWLClassAssertionAxiom ax : redundant){
			entailmentSet.remove(ax);
		}
			
		
		return entailmentSet;
	}
	
	@Override
	protected boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		for (OWLOntologyChange chg : changes) {
            if (chg.isAxiomChange() && chg.getAxiom() instanceof OWLClassAssertionAxiom) {
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
