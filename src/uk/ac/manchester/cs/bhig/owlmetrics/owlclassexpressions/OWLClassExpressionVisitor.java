package uk.ac.manchester.cs.bhig.owlmetrics.owlclassexpressions;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.metrics.AbstractOWLMetric;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.OWLOntologyWalker;
import org.semanticweb.owlapi.util.OWLOntologyWalkerVisitor;

import uk.ac.manchester.cs.bhig.owlmetrics.AxiomTypeCount;

public abstract class OWLClassExpressionVisitor extends AbstractOWLMetric<Object> implements AxiomTypeCount<Object>{
	

	private Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();

	public OWLClassExpressionVisitor(OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
	}

	public HashSet<OWLObjectSomeValuesFrom> getOWLSomeValuesFromAxioms() {
		
        final HashSet<OWLObjectSomeValuesFrom> clasex = new HashSet<OWLObjectSomeValuesFrom>();

		OWLOntologyWalker walker = new OWLOntologyWalker(Collections
				.singleton(super.getOntology()));
		OWLOntologyWalkerVisitor<OWLObjectSomeValuesFrom> visitor = 
			new OWLOntologyWalkerVisitor<OWLObjectSomeValuesFrom>(
				walker) {

			public OWLObjectSomeValuesFrom visit(OWLObjectSomeValuesFrom desc) {
				
				axioms.add(getCurrentAxiom());
				
				clasex.add(desc);
				return desc;
			}
		};

		walker.walkStructure(visitor);	
		return clasex;
	}
	
	public HashSet<OWLObjectAllValuesFrom> getOWLAllValuesFromAxioms() {
		// SPANAKOPITA!   SPANAKOPITA!   SPANAKOPITA!   SPANAKOPITA!   SPANAKOPITA!   SPANAKOPITA!
		final HashSet<OWLObjectAllValuesFrom> clasex = new HashSet<OWLObjectAllValuesFrom>();
		
		OWLOntologyWalker walker = new OWLOntologyWalker(Collections
				.singleton(super.getOntology()));
		OWLOntologyWalkerVisitor<OWLObjectAllValuesFrom> visitor = 
			new OWLOntologyWalkerVisitor<OWLObjectAllValuesFrom>(
					walker) {

			public OWLObjectAllValuesFrom visit(OWLObjectAllValuesFrom desc) {
				
				axioms.add(getCurrentAxiom());
			
				clasex.add(desc);
				return desc;
			}
		};

		walker.walkStructure(visitor);	
		return clasex;
	}
	
	@Override
	public Set<? extends OWLAxiom> getAxioms() {
		return axioms;
	}
	
	@Override
	public boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		 for (OWLOntologyChange chg : changes) {
	            if (chg.isAxiomChange() && chg.getAxiom() instanceof OWLClassExpression) {
	                return true;
	            }
	        }
	        return false;
	}
	
	@Override
	protected void disposeMetric() {

	}
	
}
