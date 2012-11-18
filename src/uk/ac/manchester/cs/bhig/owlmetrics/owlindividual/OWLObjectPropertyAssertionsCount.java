package uk.ac.manchester.cs.bhig.owlmetrics.owlindividual;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.metrics.IntegerValuedMetric;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

public class OWLObjectPropertyAssertionsCount extends IntegerValuedMetric {

	private OWLReasoner reasoner;
	
	public OWLObjectPropertyAssertionsCount(OWLOntologyManager owlOntologyManager, OWLReasoner reasoner) {
		super(owlOntologyManager);
		this.reasoner = reasoner;
	}

	@Override
	public String getName() {
		return "Number of OWLObjectPropertyAssertions";
	}

	@Override
	protected Integer recomputeMetric() {
		int sum = 0;
		for(OWLNamedIndividual indi : getOntology().getIndividualsInSignature()){
			sum += getObjectPropertyAssertions(indi).size();
		}
		return sum;
	}
	
	public Set<OWLNamedIndividual> getObjectPropertyAssertions(OWLNamedIndividual individual){
		Set<OWLNamedIndividual> assertions = new HashSet<OWLNamedIndividual>();
		for(OWLObjectProperty prop : getOntology().getObjectPropertiesInSignature()){
			if(!prop.isTopEntity() && !reasoner.getObjectPropertyValues(individual, prop).isEmpty())
				assertions.addAll(reasoner.getObjectPropertyValues(individual, prop).getFlattened());
		}
		return assertions;
	}
	
	
	

	@Override
	protected boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		for (OWLOntologyChange chg : changes) {
            if (chg.isAxiomChange() && !chg.getAxiom().isOfType(AxiomType.OBJECT_PROPERTY_ASSERTION)) {
                return true;
            }
        }
		return false;
	}

	@Override
	protected void disposeMetric() {
		
	}

}
