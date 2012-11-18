package uk.ac.manchester.cs.bhig.owlmetrics.entailments;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.metrics.IntegerValuedMetric;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.InferredSubObjectPropertyAxiomGenerator;

import uk.ac.manchester.cs.bhig.owlmetrics.AxiomTypeCount;

public class OWLSubObjectPropertyEntailmentCount extends IntegerValuedMetric implements AxiomTypeCount<Integer>{

	private OWLReasoner reasoner;
	private Set<OWLSubObjectPropertyOfAxiom> entailmentSet = new HashSet<OWLSubObjectPropertyOfAxiom>();
	
	public OWLSubObjectPropertyEntailmentCount(OWLOntologyManager owlOntologyManager, OWLReasoner reasoner) {
		super(owlOntologyManager);
		this.reasoner = reasoner;
	}

	@Override
	public String getName() {
		return "Number of SubObject property entailments";
	}

	@Override
	protected Integer recomputeMetric() {
		
		return getOWLSubObjectPropertyEntailments().size();
	}
	
	protected Set<OWLSubObjectPropertyOfAxiom> getOWLSubObjectPropertyEntailments(){
		//OWLObjectProperty topProperty = getManager().getOWLDataFactory().getOWLTopObjectProperty();
		OWLObjectProperty bottomProp = getManager().getOWLDataFactory().getOWLBottomObjectProperty();
		
		InferredSubObjectPropertyAxiomGenerator gen = new InferredSubObjectPropertyAxiomGenerator();
		entailmentSet = gen.createAxioms(getManager(), reasoner);
		
		for(OWLSubObjectPropertyOfAxiom ax : entailmentSet){
			//remove unsatisfiable properties
			if(ax.getSuperProperty().equals(bottomProp)){
				entailmentSet.remove(ax);
			}
		}
		
		return entailmentSet;
	}

	@Override
	protected boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		 for (OWLOntologyChange chg : changes) {
	            if (chg.isAxiomChange() && chg.getAxiom() instanceof OWLSubObjectPropertyOfAxiom) {
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
