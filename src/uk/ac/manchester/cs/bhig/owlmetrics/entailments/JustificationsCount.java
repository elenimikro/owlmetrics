package uk.ac.manchester.cs.bhig.owlmetrics.entailments;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.semanticweb.owl.explanation.api.Explanation;
import org.semanticweb.owl.explanation.api.ExplanationGenerator;
import org.semanticweb.owl.explanation.api.ExplanationGeneratorFactory;
import org.semanticweb.owl.explanation.api.ExplanationManager;
import org.semanticweb.owlapi.metrics.IntegerValuedMetric;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

public class JustificationsCount extends IntegerValuedMetric {
	
	private OWLReasonerFactory rfactory;
	private HashSet<OWLSubClassOfAxiom> entailments = new HashSet<OWLSubClassOfAxiom>();

	public JustificationsCount(OWLOntologyManager owlOntologyManager, 
			OWLReasonerFactory reasonerFactory) {
		super(owlOntologyManager);
		this.rfactory = reasonerFactory;
		//entailments = entailmentSet;
	}

	@Override
	public String getName() {
		return "Number of Justifications ";
	}

	@Override
	protected Integer recomputeMetric() {	
		return computeNumberOfJustifications(10).size();
	}

	protected ArrayList<Explanation<OWLAxiom>> computeNumberOfJustifications(
			int upperLimit) {
		// create the explanation factory
		ExplanationGeneratorFactory<OWLAxiom> expfactory = ExplanationManager
				.createExplanationGeneratorFactory(rfactory);
		ExplanationGenerator<OWLAxiom> gen = expfactory
				.createExplanationGenerator(getOntology());

		ArrayList<Explanation<OWLAxiom>> explanations = new ArrayList<Explanation<OWLAxiom>>();

		if (!entailments.isEmpty()) {
			for (OWLSubClassOfAxiom ax : entailments) {

				if (!ax.getSuperClass().isOWLThing()
						|| !ax.getSuperClass().isTopEntity()) {
					for (Explanation<OWLAxiom> explanation : gen
							.getExplanations(ax, upperLimit)) {
						explanations.add(explanation);
//						System.out.println("Explanation");
//						System.out.println(explanation);
					}
				}
			}

		}
		return explanations;
	}

	public HashSet<OWLSubClassOfAxiom> getEntailmentSet(){
		return entailments;
	}
	
	public void setEntailmentSet(HashSet<OWLSubClassOfAxiom> hashSet){
		entailments = hashSet;
	}

	@Override
	protected boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		for (OWLOntologyChange chg : changes) {
			for(OWLAxiom entailment : entailments ){
				if (chg.isAxiomChange() && chg.getAxiom().isOfType(entailment.getAxiomType())) {
					return true;
				}
			}

		}

		return false;
	}

	@Override
	protected void disposeMetric() {

	}

}
