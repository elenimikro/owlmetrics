package uk.ac.manchester.cs.bhig.owlmetrics.owlobjectproperty;

import java.util.HashSet;
import java.util.List;

import org.semanticweb.owlapi.metrics.IntegerValuedMetric;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

public class OWLObjectPropertyDomainAxiomsCount extends IntegerValuedMetric {

	private OWLReasoner reasoner;
	
	public OWLObjectPropertyDomainAxiomsCount(
			OWLOntologyManager owlOntologyManager, OWLReasoner reasoner) {
		super(owlOntologyManager);
		
		this.reasoner = reasoner;
	}

	@Override
	public String getName() {
		return "Number of OWLObjectProperty domain axioms";
	}

	@Override
	protected Integer recomputeMetric() {
		int domainNumber = 0;
		for (OWLObjectProperty objProp : super.getOntology()
				.getObjectPropertiesInSignature(true)){
			domainNumber += getOWLObjectPropertyDomains(objProp).size();
		}
		return domainNumber;
	}
	
	
	public HashSet<OWLClass> getOWLObjectPropertyDomains(OWLObjectProperty property){
		HashSet<OWLClass> domains = new HashSet<OWLClass>();
		NodeSet<OWLClass> domainSet = reasoner
								.getObjectPropertyDomains(property, true);
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
            if (chg.isAxiomChange() && !chg.getAxiom().isOfType(AxiomType.OBJECT_PROPERTY_DOMAIN)) {
                return true;
            }
        }
		return false;
	}

	@Override
	protected void disposeMetric() {

	}

}
