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

public class OWLObjectPropertyRangeAxiomsCount extends IntegerValuedMetric {

	private OWLReasoner reasoner;
	
	public OWLObjectPropertyRangeAxiomsCount(
			OWLOntologyManager owlOntologyManager, OWLReasoner reasoner) {
		super(owlOntologyManager);
		this.reasoner = reasoner;
	}

	@Override
	public String getName() {
		return "Number of OWLObjectPropertyRange axioms";
	}

	@Override
	protected Integer recomputeMetric() {
		int rangeNo = 0;
		for(OWLObjectProperty prop : getOntology().getObjectPropertiesInSignature(true)){
			rangeNo += getOWLObjectPropertyRanges(prop).size();
		}
		return rangeNo;
	}

	
	public HashSet<OWLClass> getOWLObjectPropertyRanges(OWLObjectProperty property){
		HashSet<OWLClass> ranges = new HashSet<OWLClass>();
		
		NodeSet<OWLClass> rangeSet = reasoner.getObjectPropertyRanges(property, true);
		for(Node<OWLClass> clas : rangeSet){
			if(!clas.isTopNode())
				ranges.addAll(clas.getEntities());
		}
			
		//add missing ranges 
		for(OWLClassExpression clex : property.getRanges(getOntology())){
			if(!clex.isAnonymous()){
				if(!rangeSet.containsEntity((OWLClass) clex)){
					ranges.add((OWLClass) clex);
				}
			}
		}
		
		return ranges;
	}
	
	@Override
	protected boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		for (OWLOntologyChange chg : changes) {
            if (chg.isAxiomChange() && !chg.getAxiom().isOfType(AxiomType.OBJECT_PROPERTY_RANGE)) {
                return true;
            }
        }
		return false;
	}

	@Override
	protected void disposeMetric() {

	}

}
