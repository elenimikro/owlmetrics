/**
 * 
 */
package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy;

import java.util.List;

import org.semanticweb.owlapi.metrics.AbstractOWLMetric;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import uk.ac.manchester.cs.bhig.owlmetrics.CountableEntity;

/**
 * @author elenimikroyannidi
 * @param <E>
 * @param <M>
 * 
 */
public abstract class OWLHierarchyParser extends AbstractOWLMetric<Object> implements CountableEntity<Object>{

	private OWLReasoner reasoner;

	public OWLHierarchyParser(OWLOntologyManager owlOntologyManager,OWLReasoner reasoner) {
		super(owlOntologyManager);
		this.reasoner = reasoner;
	}
	
	public NodeSet<?> getSubNodes(OWLObject parent, boolean directOf){
		if(parent!=null){
			if(!parent.getClassesInSignature().isEmpty()){
				return (NodeSet<?>) reasoner.getSubClasses((OWLClassExpression) parent, directOf);
			}
			else if(!parent.getObjectPropertiesInSignature().isEmpty())
				
				return (NodeSet<?>) reasoner.getSubObjectProperties((OWLObjectPropertyExpression) parent, directOf);
			else if(!parent.getDataPropertiesInSignature().isEmpty())
				return (NodeSet<?>) reasoner.getSubDataProperties((OWLDataProperty) parent, directOf);
			else	
				return null;
		}
		return null;
	}
	
	public NodeSet<?> getSuperNodes(OWLObject parent, boolean directOf){
		if(parent!=null){
			if(!parent.getClassesInSignature().isEmpty()){
				return (NodeSet<?>) reasoner.getSuperClasses((OWLClassExpression) parent, directOf);
			}
			else if(!parent.getObjectPropertiesInSignature().isEmpty())
				
				return (NodeSet<?>) reasoner.getSuperObjectProperties((OWLObjectPropertyExpression) parent, directOf);
			else if(!parent.getDataPropertiesInSignature().isEmpty())
				return (NodeSet<?>) reasoner.getSuperDataProperties((OWLDataProperty) parent, directOf);
			else	
				return null;
		}
		return null;
	}

	public void setReasoner(OWLReasoner reasoner) {
		this.reasoner = reasoner;
			
	}
	
	public boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		 for (OWLOntologyChange chg : changes) {
	            if (chg.isAxiomChange() && chg.getAxiom() instanceof OWLSubClassOfAxiom) {
	                return true;
	            }
	        }
	        return false;
	}
	
	@Override
	protected void disposeMetric() {
		
	}
	

}
