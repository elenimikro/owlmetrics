package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owloblectproperty;

import java.util.List;

import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import uk.ac.manchester.cs.bhig.owlmetrics.DoubleValuedMetric;
import uk.ac.manchester.cs.bhig.owlmetrics.owlobjectproperty.OWLObjectPropertiesCount;

public class AverageSubObjectPropertiesPerPropertyCount extends
		DoubleValuedMetric {

	private TotalNumberOfSubObjectPropertyCount subs;
	private OWLObjectPropertiesCount props;
	
	public AverageSubObjectPropertiesPerPropertyCount(
			OWLOntologyManager owlOntologyManager, OWLReasoner reasoner) {
		super(owlOntologyManager);
		
		subs = new TotalNumberOfSubObjectPropertyCount(owlOntologyManager, reasoner);
		props = new OWLObjectPropertiesCount(owlOntologyManager);
	}

	@Override
	public String getName() {
		return "Average number of subObject properties per property";
	}

	@Override
	protected Double recomputeMetric() {
		subs.setOntology(getOntology());
		props.setOntology(getOntology());
		
		setNumerator(subs.recomputeMetric());
		setDenominator(props.recomputeMetric());
		
		return computeNormalisedMetric();
	}

	@Override
	protected boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		if(subs.isMetricInvalidated(changes) || props.isMetricInvalidated(changes))
			return true;
		else
			return false;
	}

	@Override
	protected void disposeMetric() {
	
	}

}
