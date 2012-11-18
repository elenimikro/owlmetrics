package uk.ac.manchester.cs.bhig.owlmetrics.owlindividual;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.metrics.IntegerValuedMetric;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

public class OWLDataPropertyAssertionsCount extends IntegerValuedMetric {

	private OWLReasoner reasoner;
	
	public OWLDataPropertyAssertionsCount(OWLOntologyManager owlOntologyManager, OWLReasoner reasoner) {
		super(owlOntologyManager);
		this.reasoner = reasoner;
	}

	@Override
	public String getName() {
		return "Number of OWLDataPropertyAssertions";
	}

	@Override
	protected Integer recomputeMetric() {
		int sum = 0;
		for(OWLNamedIndividual indi : getOntology().getIndividualsInSignature()){
			sum += getDataPropertyAssertions(indi).size();
		}
		return sum;
	}
	
	public Set<OWLLiteral> getDataPropertyAssertions(OWLNamedIndividual individual){
		Set<OWLLiteral> assertions = new HashSet<OWLLiteral>();
		for(OWLDataProperty prop : getOntology().getDataPropertiesInSignature()){
			assertions.addAll(reasoner.getDataPropertyValues(individual, prop));
		}
		return assertions;
	}
	

	@Override
	protected boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		for (OWLOntologyChange chg : changes) {
            if (chg.isAxiomChange() && !chg.getAxiom().isOfType(AxiomType.DATA_PROPERTY_ASSERTION)) {
                return true;
            }
        }
		return false;
	}

	@Override
	protected void disposeMetric() {
		
	}

}
