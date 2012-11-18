package uk.ac.manchester.cs.bhig.owlmetrics.owlindividual;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.semanticweb.owlapi.metrics.IntegerValuedMetric;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

public class OWLIndividualTypesCount extends IntegerValuedMetric {
	
	private OWLReasoner reasoner;

	public OWLIndividualTypesCount(OWLOntologyManager owlOntologyManager, OWLReasoner reasoner) {
		super(owlOntologyManager);
		this.reasoner = reasoner;
	}

	@Override
	public String getName() {
		return "Number of types";
	}

	@Override
	protected Integer recomputeMetric() {
		int sum = 0;

		for(OWLNamedIndividual indi : getOntology().getIndividualsInSignature(true)){
			sum += getTypes(indi).size();
		}
		return sum;
	}

	
	public HashSet<OWLClass> getTypes(OWLNamedIndividual individual){
		int noOfTypes=0;
		
		ArrayList<OWLClass> indiTypeSet = new ArrayList<OWLClass>();
		HashSet<OWLClass> mostDirectTypes = new HashSet<OWLClass>();
		
		for(Node<OWLClass> clsNode : reasoner.getTypes(individual, true)){
			
			if(!clsNode.isTopNode()){
				indiTypeSet.addAll(clsNode.getEntities());
			}
		}
		
		//find the most direct types
		if(indiTypeSet.size()>1){
			
			
			HashSet<OWLClass> superTypes = new HashSet<OWLClass>();
			for(int i=0; i<indiTypeSet.size(); i++){
				for(int iterator = 0; iterator<indiTypeSet.size(); iterator++){
					if(indiTypeSet.get(iterator).getSubClasses(getOntology()).contains(indiTypeSet.get(i))){
						System.out.println("Class " +  indiTypeSet.get(i) + "Subclass of " + indiTypeSet.get(iterator));
						superTypes.add(indiTypeSet.get(iterator));
						
					}
					else if(indiTypeSet.get(i).getSubClasses(getOntology()).contains(indiTypeSet.get(iterator))){
						System.out.println("Class " +  indiTypeSet.get(i) + "Superclass of " + indiTypeSet.get(iterator));
						System.out.println("So " + indiTypeSet.get(i) + " is useless");
						superTypes.add(indiTypeSet.get(i));
					}							
				}
			}
			
			//exclude super types
			for(OWLClass cls : indiTypeSet){
				if(!superTypes.contains(cls)){
					mostDirectTypes.add(cls);
					noOfTypes++;
					System.out.println("This is a most direct types " + cls);
				}
			}
		}
		else{
			mostDirectTypes.addAll(indiTypeSet);
		}
		
		return mostDirectTypes;
	}

	@Override
	protected boolean isMetricInvalidated(
			List<? extends OWLOntologyChange> changes) {
		for (OWLOntologyChange chg : changes) {
            if (chg.isAxiomChange() && !chg.getAxiom().isOfType(AxiomType.CLASS_ASSERTION)) {
                return true;
            }
        }
		return false;
	}

	@Override
	protected void disposeMetric() {
		// TODO Auto-generated method stub

	}

}
