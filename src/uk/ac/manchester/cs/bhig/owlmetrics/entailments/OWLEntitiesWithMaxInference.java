package uk.ac.manchester.cs.bhig.owlmetrics.entailments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import uk.ac.manchester.cs.bhig.owlmetrics.ValueComparator;

public class OWLEntitiesWithMaxInference {

	
	public static LinkedHashMap<OWLObject, Integer> getClassesWithMaximumInference(HashSet<OWLAxiom> entailments){
		HashSet<OWLClass> exprClasses = new HashSet<OWLClass>();
		ArrayList<OWLClass> clset = new ArrayList<OWLClass>();
		
		HashMap<OWLObject, Integer> classEntailments = new HashMap<OWLObject, Integer>();
		
		//add all the classes
		for(OWLAxiom ax : entailments){
			exprClasses.addAll(ax.getClassesInSignature());
			clset.addAll(ax.getClassesInSignature());
		}
		
		//process the class set and find classes with maximum inference
		for(OWLClass cls : exprClasses){
			int usage=0;
			//found how many times participates in the entailments
			for(int i=0; i<clset.size(); i++){
				if(clset.get(i).equals(cls))
					usage++;
			}
			
			classEntailments.put(cls, usage);
		}
		
		LinkedHashMap<OWLObject, Integer> sortedClsEntailments = ValueComparator.sortHashMapByValues(classEntailments);
		
		return sortedClsEntailments;
	}
	
	public static LinkedHashMap<OWLObject, Integer> getObjectPropertiesWithMaximumInference(HashSet<OWLAxiom> entailments){
		HashSet<OWLObjectProperty> expEntities = new HashSet<OWLObjectProperty>();
		ArrayList<OWLObjectProperty> entityset = new ArrayList<OWLObjectProperty>();
		
		HashMap<OWLObject, Integer> entityEntailments = new HashMap<OWLObject, Integer>();
		
		//add all the classes
		for(OWLAxiom ax : entailments){
			expEntities.addAll(ax.getObjectPropertiesInSignature());
			entityset.addAll(ax.getObjectPropertiesInSignature());
		}
		
		//process the class set and find classes with maximum inference
		for(OWLObjectProperty cls : expEntities){
			int usage=0;
			//found how many times participates in the entailments
			for(int i=0; i<entityset.size(); i++){
				if(entityset.get(i).equals(cls))
					usage++;
			}
			
			entityEntailments.put(cls, usage);
		}
		
		LinkedHashMap<OWLObject, Integer> sortedPropEntailments = ValueComparator.sortHashMapByValues(entityEntailments);
		
		return sortedPropEntailments;
	}
	
	
	public static LinkedHashMap<OWLObject, Integer> getIndividualsWithMaximumInference(HashSet<OWLAxiom> entailments){
		HashSet<OWLIndividual> expEntities = new HashSet<OWLIndividual>();
		ArrayList<OWLIndividual> entityset = new ArrayList<OWLIndividual>();
		
		HashMap<OWLIndividual, Integer> entityEntailments = new HashMap<OWLIndividual, Integer>();
		
		//add all the classes
		for(OWLAxiom ax : entailments){
			expEntities.addAll(ax.getIndividualsInSignature());
			entityset.addAll(ax.getIndividualsInSignature());
		}
		
		
		//process the class set and find classes with maximum inference
		for(OWLIndividual cls : expEntities){
			int usage=0;
			//found how many times participates in the entailments
			for(int i=0; i<entityset.size(); i++){
				if(entityset.get(i).equals(cls))
					usage++;
			}
			
			entityEntailments.put(cls, usage);
		}
		
		LinkedHashMap<OWLObject, Integer> sortedEntityEntailments = ValueComparator.sortHashMapByValues(entityEntailments);
		
		return sortedEntityEntailments;
	}
	
	

}
