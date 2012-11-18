package uk.ac.manchester.cs.bhig.owlmetrics.entailments;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.metrics.IntegerValuedMetric;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.InferredSubClassAxiomGenerator;

import uk.ac.manchester.cs.bhig.owlmetrics.AxiomTypeCount;

public class OWLSubClassEntailmentCount extends IntegerValuedMetric implements AxiomTypeCount<Integer>{

	private OWLReasoner reasoner;
	private HashSet<OWLSubClassOfAxiom> entailmentSet = new HashSet<OWLSubClassOfAxiom>();
	
	public OWLSubClassEntailmentCount(OWLOntologyManager owlOntologyManager, OWLReasoner reasoner) {
		super(owlOntologyManager);
		this.reasoner = reasoner;
	}

	@Override
	public String getName() {
		return "Number of SubClass entailments";
	}

	@Override
	protected Integer recomputeMetric() {
		//entailmentSet.clear();
		return getOWLSubClassOfEntailments().size();
	}
	
	public HashSet<OWLSubClassOfAxiom> getOWLSubClassOfEntailments(){
		OWLClass thing = getManager().getOWLDataFactory().getOWLThing();
		OWLClass nothing = getManager().getOWLDataFactory().getOWLNothing();
		
		InferredSubClassAxiomGenerator gen = new InferredSubClassAxiomGenerator();
		
		if(entailmentSet.isEmpty())
			entailmentSet = (HashSet<OWLSubClassOfAxiom>) gen.createAxioms(getManager(), reasoner);
		
		for(OWLSubClassOfAxiom ax : entailmentSet){
			if(ax.getSuperClass().equals(thing) && ax.getSuperClass().equals(nothing)){
				entailmentSet.remove(ax);
			}
		}
		
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
	
	public HashSet<OWLSubClassOfAxiom> getLeftComplexOWLSubClassOfEntailments(){
		HashSet<OWLSubClassOfAxiom> leftComplexExpressionSet = new HashSet<OWLSubClassOfAxiom>();
		
		for(OWLSubClassOfAxiom ax : entailmentSet){
			if(ax.getSubClass().isAnonymous())
				leftComplexExpressionSet.add(ax);
		}
		
		return leftComplexExpressionSet;
	}
	
	public HashSet<OWLSubClassOfAxiom> getComplexOWLSubClassOfEntailments(){
		HashSet<OWLSubClassOfAxiom> complexExpressionSet = new HashSet<OWLSubClassOfAxiom>();
		
		for(OWLSubClassOfAxiom ax : entailmentSet){
			if(ax.getSubClass().isAnonymous() || ax.getSuperClass().isAnonymous())
				complexExpressionSet.add(ax);
			else
				System.out.println("Simple one: " + ax);	
		}
		
		return complexExpressionSet;
	}

	@Override
	public Set<? extends OWLAxiom> getAxioms() {
		return entailmentSet;
	}

}
