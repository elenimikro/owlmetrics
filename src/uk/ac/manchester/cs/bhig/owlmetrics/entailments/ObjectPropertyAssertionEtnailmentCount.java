package uk.ac.manchester.cs.bhig.owlmetrics.entailments;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.metrics.IntegerValuedMetric;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLPropertyAssertionAxiom;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.InferredPropertyAssertionGenerator;

import uk.ac.manchester.cs.bhig.owlmetrics.AxiomTypeCount;

public class ObjectPropertyAssertionEtnailmentCount extends IntegerValuedMetric
		implements AxiomTypeCount<Integer> {

	private final OWLReasoner reasoner;
	private Set<OWLPropertyAssertionAxiom<?, ?>> entailmentSet = new HashSet<OWLPropertyAssertionAxiom<?, ?>>();

	public ObjectPropertyAssertionEtnailmentCount(
			OWLOntologyManager owlOntologyManager, OWLReasoner reasoner) {
		super(owlOntologyManager);
		this.reasoner = reasoner;
	}

	@Override
	public String getName() {
		return "Object property assertion entailments";
	}

	@Override
	protected Integer recomputeMetric() {
		return getObjectPropertyAssertionEntailments().size();
	}

	protected Set<OWLPropertyAssertionAxiom<?, ?>> getObjectPropertyAssertionEntailments() {
		OWLObjectProperty topProperty = getManager().getOWLDataFactory()
				.getOWLTopObjectProperty();
		OWLObjectProperty bottomProp = getManager().getOWLDataFactory()
				.getOWLBottomObjectProperty();

		InferredPropertyAssertionGenerator gen = new InferredPropertyAssertionGenerator();
		if (entailmentSet.isEmpty()) {
			entailmentSet = gen.createAxioms(getManager(), reasoner);
		}

		for (OWLPropertyAssertionAxiom<?, ?> ax : entailmentSet) {
			if (ax.getObjectPropertiesInSignature().contains(topProperty)
					|| ax.getObjectPropertiesInSignature().contains(bottomProp)) {
				entailmentSet.remove(ax);
			}
		}

		return entailmentSet;
	}

	@Override
	protected boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		for (OWLOntologyChange chg : changes) {
			if (chg.isAxiomChange()
					&& chg.getAxiom() instanceof OWLPropertyAssertionAxiom) {
				entailmentSet.clear();
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
