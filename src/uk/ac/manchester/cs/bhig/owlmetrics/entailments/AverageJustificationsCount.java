package uk.ac.manchester.cs.bhig.owlmetrics.entailments;

import java.util.HashSet;
import java.util.List;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

import uk.ac.manchester.cs.bhig.owlmetrics.DoubleValuedMetric;

public class AverageJustificationsCount extends DoubleValuedMetric {

	private HashSet<OWLSubClassOfAxiom> entails = new HashSet<OWLSubClassOfAxiom>();
	private JustificationsCount justs;
	
	public AverageJustificationsCount(OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		return "Average justifications per entailment";
	}

	@Override
	protected Double recomputeMetric() {
		setNumerator(justs);
		setDenominator(entails);
		return computeNormalisedMetric();
	}

	@Override
	protected boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		for (OWLOntologyChange chg : changes) {
			for(OWLAxiom entailment : entails ){
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
	
	public void setJustifications(JustificationsCount justifications){
		this.justs = justifications;
		entails = justs.getEntailmentSet();
	}
	
	protected void setNumerator(JustificationsCount justifications){
		super.setNumerator(justifications.recomputeMetric());
	}
	
	protected void setDenominator(HashSet<OWLSubClassOfAxiom> entailments){
		super.setDenominator(entailments.size());
	}

}
