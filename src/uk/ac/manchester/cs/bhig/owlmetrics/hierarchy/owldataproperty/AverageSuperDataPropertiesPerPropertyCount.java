package uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owldataproperty;

import java.util.List;

import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import uk.ac.manchester.cs.bhig.owlmetrics.DoubleValuedMetric;
import uk.ac.manchester.cs.bhig.owlmetrics.owldataproperty.OWLDataPropertiesCount;

public class AverageSuperDataPropertiesPerPropertyCount extends
		DoubleValuedMetric {

	private TotalNumberOfSuperDataProperties supers;
	private OWLDataPropertiesCount props;
	
	public AverageSuperDataPropertiesPerPropertyCount(
			OWLOntologyManager owlOntologyManager, OWLReasoner reasoner) {
		super(owlOntologyManager);
		
		supers = new TotalNumberOfSuperDataProperties(owlOntologyManager, reasoner);
		props = new OWLDataPropertiesCount(owlOntologyManager);
	}

	@Override
	public String getName() {
		return "Average number of superData properties per property";
	}

	@Override
	protected Double recomputeMetric() {
		supers.setOntology(getOntology());
		props.setOntology(getOntology());
		
		
		setNumerator(supers.recomputeMetric());
		setDenominator(props.recomputeMetric());
		
		return computeNormalisedMetric();
	}

	@Override
	protected boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		if(supers.isMetricInvalidated(changes) || props.isMetricInvalidated(changes))
			return true;
		else
			return false;
	}

	@Override
	protected void disposeMetric() {
	
	}

}
