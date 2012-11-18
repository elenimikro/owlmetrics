package uk.ac.manchester.cs.bhig.owlmetrics.owldataproperty;

import java.util.HashSet;
import java.util.List;

import org.semanticweb.owlapi.metrics.IntegerValuedMetric;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

public class OWLDataPropertyDomainAxiomsCount extends IntegerValuedMetric {

	private OWLReasoner reasoner;
	
	public OWLDataPropertyDomainAxiomsCount(
			OWLOntologyManager owlOntologyManager, OWLReasoner reasoner) {
		super(owlOntologyManager);
		
		this.reasoner = reasoner;
	}

	@Override
	public String getName() {
		return "Number of OWLDataProperty domain axioms";
	}

	@Override
	protected Integer recomputeMetric() {
		int domainNumber = 0;
		for (OWLDataProperty prop : super.getOntology()
				.getDataPropertiesInSignature(true)){
			domainNumber += getOWLDataPropertyDomains(prop).size();
		}
		return domainNumber;
	}
	
	
	public HashSet<OWLClass> getOWLDataPropertyDomains(OWLDataProperty property){
		HashSet<OWLClass> domains = new HashSet<OWLClass>();
		NodeSet<OWLClass> domainSet = reasoner
								.getDataPropertyDomains(property, true);
		for (Node<OWLClass> cl : domainSet) {
			if (!cl.isTopNode()) {
				domains.addAll(cl.getEntities());
			}
		}

		for (OWLClassExpression clex : property.getDomains(super.getOntology())) {
			if(!clex.isAnonymous()){
				if (!domainSet.containsEntity((OWLClass) clex)) {
					domains.add((OWLClass) clex);
				}
			}
		}
		return domains;
	}

	
	@Override
	protected boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		for (OWLOntologyChange chg : changes) {
            if (chg.isAxiomChange() && !chg.getAxiom().isOfType(AxiomType.DATA_PROPERTY_DOMAIN)) {
                return true;
            }
        }
		return false;
	}

	@Override
	protected void disposeMetric() {

	}

}
