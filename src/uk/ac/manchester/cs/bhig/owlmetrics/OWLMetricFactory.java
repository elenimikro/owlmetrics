package uk.ac.manchester.cs.bhig.owlmetrics;

import java.util.ArrayList;
import java.util.List;

import org.semanticweb.owlapi.metrics.DLExpressivity;
import org.semanticweb.owlapi.metrics.OWLMetric;
import org.semanticweb.owlapi.metrics.UnsatisfiableClassCountMetric;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

import uk.ac.manchester.cs.bhig.owlmetrics.classification.AbstractClassificationMetric;
import uk.ac.manchester.cs.bhig.owlmetrics.classification.ClassExpressionComplexityClassification;
import uk.ac.manchester.cs.bhig.owlmetrics.classification.HierarchyComplexityClassification;
import uk.ac.manchester.cs.bhig.owlmetrics.classification.HierarchyNestingClassification;
import uk.ac.manchester.cs.bhig.owlmetrics.classification.HierarchyWidenessClassification;
import uk.ac.manchester.cs.bhig.owlmetrics.classification.InferentialPotentialClassification;
import uk.ac.manchester.cs.bhig.owlmetrics.classification.InferentialRichnessClassification;
import uk.ac.manchester.cs.bhig.owlmetrics.classification.InformationRichnessClassification;
import uk.ac.manchester.cs.bhig.owlmetrics.classification.JustificationRichnessClassification;
import uk.ac.manchester.cs.bhig.owlmetrics.classification.SizeClassification;
import uk.ac.manchester.cs.bhig.owlmetrics.entailments.AverageJustificationsCount;
import uk.ac.manchester.cs.bhig.owlmetrics.entailments.ClassAssertionEtnailmentCount;
import uk.ac.manchester.cs.bhig.owlmetrics.entailments.JustificationsCount;
import uk.ac.manchester.cs.bhig.owlmetrics.entailments.OWLEquivalentClassEntailmentCount;
import uk.ac.manchester.cs.bhig.owlmetrics.entailments.OWLEquivalentObjectPropertyEntailmentCount;
import uk.ac.manchester.cs.bhig.owlmetrics.entailments.OWLSubClassEntailmentCount;
import uk.ac.manchester.cs.bhig.owlmetrics.entailments.OWLSubObjectPropertyEntailmentCount;
import uk.ac.manchester.cs.bhig.owlmetrics.entailments.ObjectPropertyAssertionEtnailmentCount;
import uk.ac.manchester.cs.bhig.owlmetrics.entailments.PercentageOfClassAssertionEntailments;
import uk.ac.manchester.cs.bhig.owlmetrics.entailments.PercentageOfEquivalentClassEntailments;
import uk.ac.manchester.cs.bhig.owlmetrics.entailments.PercentageOfEquivalentObjectPropertyEntailments;
import uk.ac.manchester.cs.bhig.owlmetrics.entailments.PercentageOfObjectPropertyAssertionEntailments;
import uk.ac.manchester.cs.bhig.owlmetrics.entailments.PercentageOfSubClassEntailments;
import uk.ac.manchester.cs.bhig.owlmetrics.entailments.PercentageOfSubObjectPropertyEntailments;
import uk.ac.manchester.cs.bhig.owlmetrics.entailments.RatioOfEntailmentsToNumberOfAxioms;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owlclass.AverageClassHierarchyDepth;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owlclass.AverageNumberOfSubClassesPerClass;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owlclass.AverageNumberOfSuperClassesPerClassCount;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owlclass.MaxDepthOfOWLClassHierarchy;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owlclass.MaxNumberOfSubClasses;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owlclass.MaxNumberOfSuperClasses;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owlclass.NumberOfLeafClassses;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owlclass.NumberOfOWLClassPaths;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owlclass.OWLClassWithMaxDepthInHierarchy;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owlclass.OWLClassWithMaxNumberOfSubClasses;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owlclass.OWLClassWithMaxNumberOfSuperClasses;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owlclass.TotalNumberOfSubClassesCount;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owlclass.TotalNumberOfSuperClasses;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owldataproperty.AverageDataPropertyHierarchyDepth;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owldataproperty.AverageSubDataPropertiesPerPropertyCount;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owldataproperty.AverageSuperDataPropertiesPerPropertyCount;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owldataproperty.MaxDepthOfOWLDataPropertyHierarchy;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owldataproperty.MaxNumberOfSuperDataProperties;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owldataproperty.MaxSubDataPropertyMetric;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owldataproperty.NumberOfLeafDataProperties;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owldataproperty.NumberOfOWLDataPropertyPaths;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owldataproperty.OWLDataPropertyWithMaxDepthInHierarchy;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owldataproperty.OWLDataPropertyWithMaxNumberOfSubDataProperties;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owldataproperty.OWLDataPropertyWithMaxNumberOfSuperDataProperties;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owldataproperty.TotalNumberOfSubDataPropertyCount;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owldataproperty.TotalNumberOfSuperDataProperties;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owloblectproperty.AverageObjectPropertyHierarchyDepth;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owloblectproperty.AverageSubObjectPropertiesPerPropertyCount;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owloblectproperty.AverageSuperObjectPropertiesPerPropertyCount;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owloblectproperty.MaxDepthOfOWLObjectPropertyHierarchy;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owloblectproperty.MaxNumberOfSuperObjectProperties;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owloblectproperty.MaxSubObjectPropertyMetric;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owloblectproperty.NumberOfLeafObjectProperties;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owloblectproperty.NumberOfOWLObjectPropertyPaths;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owloblectproperty.OWLObjectPropertyWithMaxDepthInHierarchy;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owloblectproperty.OWLObjectPropertyWithMaxNumberOfSubObjectProperties;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owloblectproperty.OWLObjectPropertyWithMaxNumberOfSuperObjectProperties;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owloblectproperty.TotalNumberOfSubObjectPropertyCount;
import uk.ac.manchester.cs.bhig.owlmetrics.hierarchy.owloblectproperty.TotalNumberOfSuperObjectProperties;
import uk.ac.manchester.cs.bhig.owlmetrics.owlclass.AverageSubClassAxiomsPerClassCount;
import uk.ac.manchester.cs.bhig.owlmetrics.owlclass.DefinedOWLClassCount;
import uk.ac.manchester.cs.bhig.owlmetrics.owlclass.OWLClassNumber;
import uk.ac.manchester.cs.bhig.owlmetrics.owlclass.PrimitiveOWLClassCount;
import uk.ac.manchester.cs.bhig.owlmetrics.owlclass.RatioOfDefinedToPrimitiveClassCount;
import uk.ac.manchester.cs.bhig.owlmetrics.owlclassexpressions.AverageClassExpressionsPerClassCount;
import uk.ac.manchester.cs.bhig.owlmetrics.owlclassexpressions.AverageOWLClassExpressionDepthCount;
import uk.ac.manchester.cs.bhig.owlmetrics.owlclassexpressions.OWLAllValuesFromAxiomCount;
import uk.ac.manchester.cs.bhig.owlmetrics.owlclassexpressions.OWLAllValuesFromAxiomMaxDepth;
import uk.ac.manchester.cs.bhig.owlmetrics.owlclassexpressions.OWLAllValuesFromAxiomWithMaxDepth;
import uk.ac.manchester.cs.bhig.owlmetrics.owlclassexpressions.OWLSomeValuesFromAxiomCount;
import uk.ac.manchester.cs.bhig.owlmetrics.owlclassexpressions.OWLSomeValuesFromAxiomMaxDepth;
import uk.ac.manchester.cs.bhig.owlmetrics.owlclassexpressions.OWLSomeValuesFromAxiomWithMaxDepth;
import uk.ac.manchester.cs.bhig.owlmetrics.owldataproperty.OWLDataPropertiesCount;
import uk.ac.manchester.cs.bhig.owlmetrics.owldataproperty.OWLDataPropertyDomainAxiomsCount;
import uk.ac.manchester.cs.bhig.owlmetrics.owlindividual.OWLDataPropertyAssertionsCount;
import uk.ac.manchester.cs.bhig.owlmetrics.owlindividual.OWLIndividualCount;
import uk.ac.manchester.cs.bhig.owlmetrics.owlindividual.OWLIndividualTypesCount;
import uk.ac.manchester.cs.bhig.owlmetrics.owlindividual.OWLObjectPropertyAssertionsCount;
import uk.ac.manchester.cs.bhig.owlmetrics.owlobjectproperty.OWLObjectPropertiesCount;
import uk.ac.manchester.cs.bhig.owlmetrics.owlobjectproperty.OWLObjectPropertyDomainAxiomsCount;
import uk.ac.manchester.cs.bhig.owlmetrics.owlobjectproperty.OWLObjectPropertyRangeAxiomsCount;
import uk.ac.manchester.cs.bhig.owlmetrics.usage.AverageOWLClassUsage;
import uk.ac.manchester.cs.bhig.owlmetrics.usage.AverageOWLDataPropertyUsage;
import uk.ac.manchester.cs.bhig.owlmetrics.usage.AverageOWLIndividualUsage;
import uk.ac.manchester.cs.bhig.owlmetrics.usage.AverageOWLObjectPropertyUsage;
import uk.ac.manchester.cs.bhig.owlmetrics.usage.MaxOWLClassUsage;
import uk.ac.manchester.cs.bhig.owlmetrics.usage.MaxOWLDataPropertyUsage;
import uk.ac.manchester.cs.bhig.owlmetrics.usage.MaxOWLIndividualUsage;
import uk.ac.manchester.cs.bhig.owlmetrics.usage.MaxOWLObjectPropertyUsage;
import uk.ac.manchester.cs.bhig.owlmetrics.usage.OWLClassWithMaxUsage;
import uk.ac.manchester.cs.bhig.owlmetrics.usage.OWLDataPropertyWithMaxUsage;
import uk.ac.manchester.cs.bhig.owlmetrics.usage.OWLIndividualWithMaxUsage;
import uk.ac.manchester.cs.bhig.owlmetrics.usage.OWLObjectPropertyWithMaxUsage;


public class OWLMetricFactory {

	private OWLOntologyManager owlOntologyManager;
	private OWLReasoner reasoner;
	private OWLReasonerFactory rfactory;
	
	//things needed for the clusters
	JustificationsCount justs;
	AverageJustificationsCount avgJusts;
	RatioOfEntailmentsToNumberOfAxioms inferentialRichness;
	
	
	public OWLMetricFactory(OWLOntologyManager manager, OWLReasoner reasoner, OWLReasonerFactory reasonerfactory) {
		this.owlOntologyManager = manager;
		this.reasoner = reasoner;
		this.rfactory = reasonerfactory;
	}
	
	public ArrayList<OWLMetric<?>> getBasicClassMetrics(){
		ArrayList<OWLMetric<?>> cmetrics = new ArrayList<OWLMetric<?>>();
		
		cmetrics.add(new DLExpressivity(owlOntologyManager));
		cmetrics.add(new OWLClassNumber(owlOntologyManager));
		cmetrics.add(new PrimitiveOWLClassCount(owlOntologyManager));
		cmetrics.add(new DefinedOWLClassCount(owlOntologyManager));
		cmetrics.add(new RatioOfDefinedToPrimitiveClassCount(owlOntologyManager));
		cmetrics.add(new AverageSubClassAxiomsPerClassCount(owlOntologyManager));
		
		
		return cmetrics;
	}
	
	public ArrayList<OWLMetric<?>> getSubClassHierarchyMetrics(){
		ArrayList<OWLMetric<?>> metrics = new ArrayList<OWLMetric<?>>();
		
		metrics.add(new TotalNumberOfSubClassesCount(owlOntologyManager, reasoner));
		metrics.add(new MaxNumberOfSubClasses(owlOntologyManager, reasoner));
		metrics.add(new OWLClassWithMaxNumberOfSubClasses(owlOntologyManager, reasoner));
		metrics.add(new AverageNumberOfSubClassesPerClass(owlOntologyManager, reasoner));
		
		return metrics;
	}
	
	public ArrayList<OWLMetric<?>> getSuperClassHierarchyMetrics(){
		ArrayList<OWLMetric<?>> metrics = new ArrayList<OWLMetric<?>>();
		
		metrics.add(new TotalNumberOfSuperClasses(owlOntologyManager, reasoner));
		metrics.add(new MaxNumberOfSuperClasses(owlOntologyManager, reasoner));
		metrics.add(new OWLClassWithMaxNumberOfSuperClasses(owlOntologyManager, reasoner));
		metrics.add(new AverageNumberOfSuperClassesPerClassCount(owlOntologyManager, reasoner));
		
		return metrics;
	}

	public ArrayList<OWLMetric<?>> getClassTreeMetrics(){
		ArrayList<OWLMetric<?>> metrics = new ArrayList<OWLMetric<?>>();
		
		//metrics.add(new SumOfDepthsInOWLClassHierarchy(owlOntologyManager, reasoner));
		metrics.add(new AverageClassHierarchyDepth(owlOntologyManager, reasoner));
		metrics.add(new MaxDepthOfOWLClassHierarchy(owlOntologyManager, reasoner));
		metrics.add(new OWLClassWithMaxDepthInHierarchy(owlOntologyManager, reasoner));
		metrics.add(new NumberOfOWLClassPaths(owlOntologyManager, reasoner));
		metrics.add(new NumberOfLeafClassses(owlOntologyManager, reasoner));
		
		
		return metrics;
	}
	
	public ArrayList<OWLMetric<?>> getClassUsageMetrics(){
		ArrayList<OWLMetric<?>> metrics = new ArrayList<OWLMetric<?>>();
		
		//metrics.add(new SumOfOWLClassUsage(owlOntologyManager));
		metrics.add(new AverageOWLClassUsage(owlOntologyManager));
		metrics.add(new MaxOWLClassUsage(owlOntologyManager));
		metrics.add(new OWLClassWithMaxUsage(owlOntologyManager));
		
		//metrics.add(new OW)
		return metrics;
	}

/**************************************** CLASS EXPRESSIONS *************************************************************/
	
	public ArrayList<OWLMetric<?>> getClassExpressionMetrics(){
		ArrayList<OWLMetric<?>> metrics = new ArrayList<OWLMetric<?>>();
		
		metrics.add(new OWLAllValuesFromAxiomCount(owlOntologyManager));
		metrics.add(new OWLSomeValuesFromAxiomCount(owlOntologyManager));
		metrics.add(new AverageClassExpressionsPerClassCount(owlOntologyManager, reasoner));
		metrics.add(new AverageOWLClassExpressionDepthCount(owlOntologyManager));
		metrics.add(new OWLAllValuesFromAxiomMaxDepth(owlOntologyManager));
		metrics.add(new OWLAllValuesFromAxiomWithMaxDepth(owlOntologyManager));
		metrics.add(new OWLSomeValuesFromAxiomMaxDepth(owlOntologyManager));
		metrics.add(new OWLSomeValuesFromAxiomWithMaxDepth(owlOntologyManager));
		
		return metrics;
	}
	
/****************************************  OBJECT PROPERTIES ***************************************************************/
	
	public ArrayList<OWLMetric<?>> getBasicObjectPropertyMetrics(){
		ArrayList<OWLMetric<?>> cmetrics = new ArrayList<OWLMetric<?>>();
		
		cmetrics.add(new OWLObjectPropertiesCount(owlOntologyManager));
		cmetrics.add(new OWLObjectPropertyDomainAxiomsCount(owlOntologyManager, reasoner));
		cmetrics.add(new OWLObjectPropertyRangeAxiomsCount(owlOntologyManager, reasoner));
		
		return cmetrics;
	} 
	
	public ArrayList<OWLMetric<?>> getSubObjectPropertyHierarchyMetrics(){
		ArrayList<OWLMetric<?>> metrics = new ArrayList<OWLMetric<?>>();
		
		metrics.add(new TotalNumberOfSubObjectPropertyCount(owlOntologyManager, reasoner));
		metrics.add(new MaxSubObjectPropertyMetric(owlOntologyManager, reasoner));
		metrics.add(new OWLObjectPropertyWithMaxNumberOfSubObjectProperties(owlOntologyManager, reasoner));
		metrics.add(new AverageSubObjectPropertiesPerPropertyCount(owlOntologyManager, reasoner));
		
		return metrics;
	}

	public ArrayList<OWLMetric<?>> getSuperObjectPropertyHierarchyMetrics(){
		ArrayList<OWLMetric<?>> metrics = new ArrayList<OWLMetric<?>>();
		
		metrics.add(new TotalNumberOfSuperObjectProperties(owlOntologyManager, reasoner));
		metrics.add(new MaxNumberOfSuperObjectProperties(owlOntologyManager, reasoner));
		metrics.add(new OWLObjectPropertyWithMaxNumberOfSuperObjectProperties(owlOntologyManager, reasoner));
		metrics.add(new AverageSuperObjectPropertiesPerPropertyCount(owlOntologyManager, reasoner));
		
		return metrics;
	}
	
	public ArrayList<OWLMetric<?>> getObjectPropertyTreeMetrics(){
		ArrayList<OWLMetric<?>> metrics = new ArrayList<OWLMetric<?>>();
		
		//metrics.add(new SumOfDepthsInOWLClassHierarchy(owlOntologyManager, reasoner));
		metrics.add(new AverageObjectPropertyHierarchyDepth(owlOntologyManager, reasoner));
		metrics.add(new MaxDepthOfOWLObjectPropertyHierarchy(owlOntologyManager, reasoner));
		metrics.add(new OWLObjectPropertyWithMaxDepthInHierarchy(owlOntologyManager, reasoner));
		metrics.add(new NumberOfOWLObjectPropertyPaths(owlOntologyManager, reasoner));
		metrics.add(new NumberOfLeafObjectProperties(owlOntologyManager, reasoner));
		
		
		return metrics;
	}
	
	public ArrayList<OWLMetric<?>> getOWLObjectPropertUsageMetrics(){
		ArrayList<OWLMetric<?>> metrics = new ArrayList<OWLMetric<?>>();
		
		//metrics.add(new SumOfOWLClassUsage(owlOntologyManager));
		metrics.add(new AverageOWLObjectPropertyUsage(owlOntologyManager));
		metrics.add(new MaxOWLObjectPropertyUsage(owlOntologyManager));
		metrics.add(new OWLObjectPropertyWithMaxUsage(owlOntologyManager));
		
		return metrics;
	}

/****************************************  DATA PROPERTIES ***************************************************************/
	public ArrayList<OWLMetric<?>> getBasicDataPropertyMetrics(){
		ArrayList<OWLMetric<?>> cmetrics = new ArrayList<OWLMetric<?>>();
		
		cmetrics.add(new OWLDataPropertiesCount(owlOntologyManager));
		cmetrics.add(new OWLDataPropertyDomainAxiomsCount(owlOntologyManager, reasoner));
		
		return cmetrics;
	} 
	
	public ArrayList<OWLMetric<?>> getSubDataPropertyHierarchyMetrics(){
		ArrayList<OWLMetric<?>> metrics = new ArrayList<OWLMetric<?>>();
		
		metrics.add(new TotalNumberOfSubDataPropertyCount(owlOntologyManager, reasoner));
		metrics.add(new MaxSubDataPropertyMetric(owlOntologyManager, reasoner));
		metrics.add(new OWLDataPropertyWithMaxNumberOfSubDataProperties(owlOntologyManager, reasoner));
		metrics.add(new AverageSubDataPropertiesPerPropertyCount(owlOntologyManager, reasoner));
		
		return metrics;
	}

	public ArrayList<OWLMetric<?>> getSuperDataPropertyHierarchyMetrics(){
		ArrayList<OWLMetric<?>> metrics = new ArrayList<OWLMetric<?>>();
		
		metrics.add(new TotalNumberOfSuperDataProperties(owlOntologyManager, reasoner));
		metrics.add(new MaxNumberOfSuperDataProperties(owlOntologyManager, reasoner));
		metrics.add(new OWLDataPropertyWithMaxNumberOfSuperDataProperties(owlOntologyManager, reasoner));
		metrics.add(new AverageSuperDataPropertiesPerPropertyCount(owlOntologyManager, reasoner));
		
		return metrics;
	}
	
	public ArrayList<OWLMetric<?>> getDataPropertyTreeMetrics(){
		ArrayList<OWLMetric<?>> metrics = new ArrayList<OWLMetric<?>>();
		
		//metrics.add(new SumOfDepthsInOWLClassHierarchy(owlOntologyManager, reasoner));
		metrics.add(new AverageDataPropertyHierarchyDepth(owlOntologyManager, reasoner));
		metrics.add(new MaxDepthOfOWLDataPropertyHierarchy(owlOntologyManager, reasoner));
		metrics.add(new OWLDataPropertyWithMaxDepthInHierarchy(owlOntologyManager, reasoner));
		metrics.add(new NumberOfOWLDataPropertyPaths(owlOntologyManager, reasoner));
		metrics.add(new NumberOfLeafDataProperties(owlOntologyManager, reasoner));
		
		
		return metrics;
	}
	
	public ArrayList<OWLMetric<?>> getOWLDataPropertUsageMetrics(){
		ArrayList<OWLMetric<?>> metrics = new ArrayList<OWLMetric<?>>();
		
		metrics.add(new AverageOWLDataPropertyUsage(owlOntologyManager));
		metrics.add(new MaxOWLDataPropertyUsage(owlOntologyManager));
		metrics.add(new OWLDataPropertyWithMaxUsage(owlOntologyManager));
		
		return metrics;
	}
	
/****************************************  INDIVIDUALS *******************************************************************/
	
	public ArrayList<OWLMetric<?>> getBasicIndividualMetrics(){
		ArrayList<OWLMetric<?>> metrics = new ArrayList<OWLMetric<?>>();
		
		metrics.add(new OWLIndividualCount(owlOntologyManager));
		metrics.add(new OWLIndividualTypesCount(owlOntologyManager, reasoner));
		metrics.add(new OWLObjectPropertyAssertionsCount(owlOntologyManager, reasoner));
		metrics.add(new OWLDataPropertyAssertionsCount(owlOntologyManager, reasoner));
		
		return metrics;
	}
	
	public ArrayList<OWLMetric<?>> getOWLIndividualUsageMetrics(){
		ArrayList<OWLMetric<?>> metrics = new ArrayList<OWLMetric<?>>();
		
		metrics.add(new AverageOWLIndividualUsage(owlOntologyManager));
		metrics.add(new MaxOWLIndividualUsage(owlOntologyManager));
		metrics.add(new OWLIndividualWithMaxUsage(owlOntologyManager));
		
		return metrics;
	}
	

/******************************************  ENTAILMENTS ******************************************************************/
	
	public ArrayList<OWLMetric<?>> getEntailmentMetrics(OWLOntology ontology){
		ArrayList<OWLMetric<?>> metrics = new ArrayList<OWLMetric<?>>();
		
		metrics.add(new UnsatisfiableClassCountMetric(reasoner, owlOntologyManager));
		
		OWLSubClassEntailmentCount subclass = new OWLSubClassEntailmentCount(owlOntologyManager, reasoner);
		OWLEquivalentClassEntailmentCount equivclas = new OWLEquivalentClassEntailmentCount(owlOntologyManager, reasoner);
		subclass.setOntology(ontology);
		
		justs = new JustificationsCount(owlOntologyManager, rfactory);
		justs.setEntailmentSet(subclass.getOWLSubClassOfEntailments());
		justs.setOntology(ontology);

		avgJusts = new AverageJustificationsCount(owlOntologyManager);
		avgJusts.setOntology(ontology);
		avgJusts.setJustifications(justs);

		
		equivclas.setOntology(ontology);
		
		OWLSubObjectPropertyEntailmentCount subojent = 
			new OWLSubObjectPropertyEntailmentCount(owlOntologyManager, reasoner);
		OWLEquivalentObjectPropertyEntailmentCount eqobjent = 
			new OWLEquivalentObjectPropertyEntailmentCount(owlOntologyManager, reasoner);
		
		ClassAssertionEtnailmentCount typesent = 
			new ClassAssertionEtnailmentCount(owlOntologyManager, reasoner);
		ObjectPropertyAssertionEtnailmentCount propassent = 
			new ObjectPropertyAssertionEtnailmentCount(owlOntologyManager, reasoner);
		
		metrics.add(subclass);
		
		metrics.add(equivclas);
		metrics.add(subojent);
		metrics.add(eqobjent);
		metrics.add(typesent);
		metrics.add(propassent);
		
		PercentageOfSubClassEntailments psubclas =
			new PercentageOfSubClassEntailments(owlOntologyManager);
		PercentageOfEquivalentClassEntailments peqclas = 
			new PercentageOfEquivalentClassEntailments(owlOntologyManager);
		PercentageOfSubObjectPropertyEntailments psubobj = 
			new PercentageOfSubObjectPropertyEntailments(owlOntologyManager);
		PercentageOfEquivalentObjectPropertyEntailments peqobj = 
			new PercentageOfEquivalentObjectPropertyEntailments(owlOntologyManager);
		PercentageOfClassAssertionEntailments ptypes = 
			new PercentageOfClassAssertionEntailments(owlOntologyManager);
		PercentageOfObjectPropertyAssertionEntailments passertions = 
			new PercentageOfObjectPropertyAssertionEntailments(owlOntologyManager);
		
		psubclas.setOntology(ontology);
		peqclas.setOntology(ontology);
		psubobj.setOntology(ontology);
		peqobj.setOntology(ontology);
		ptypes.setOntology(ontology);
		passertions.setOntology(ontology);
		
		
		psubclas.setNumerator(subclass.getValue());
		peqclas.setNumerator(equivclas.getValue());
		psubobj.setNumerator(subojent.getValue());
		peqobj.setNumerator(eqobjent.getValue());
		ptypes.setNumerator(typesent.getValue());
		passertions.setNumerator(propassent.getValue());
		
		int den = subclass.getValue() + equivclas.getValue() + subojent.getValue() +
					eqobjent.getValue() + typesent.getValue() + propassent.getValue();
		
		inferentialRichness = new RatioOfEntailmentsToNumberOfAxioms(owlOntologyManager);
		inferentialRichness.setNumerator(den);
		
		psubclas.setDenominator(den);
		peqclas.setDenominator(den);
		psubobj.setDenominator(den);
		peqobj.setDenominator(den);
		ptypes.setDenominator(den);
		passertions.setDenominator(den);
		
		metrics.add(justs);
//		metrics.add(avgJusts);
		
		metrics.add(psubclas);
		metrics.add(peqclas);
		metrics.add(psubobj);
		metrics.add(peqobj);
		metrics.add(ptypes);
		metrics.add(passertions);
		
		return metrics;
	}

/******************************************  CLASSIFICATION ******************************************************************/
	
	public List<AbstractClassificationMetric> getAssertedMetricClassifications(OWLOntology ontology){
		ArrayList<AbstractClassificationMetric> metrics = new ArrayList<AbstractClassificationMetric>();
		
		OWLEntitiesSize size = new  OWLEntitiesSize(owlOntologyManager); 
		AverageOWLClassExpressionDepthCount classexpcomplexity = 
			new AverageOWLClassExpressionDepthCount(owlOntologyManager);
		AverageSubClassAxiomsPerClassCount inforichness = 
			new AverageSubClassAxiomsPerClassCount(owlOntologyManager);
		
		AverageNumberOfSubClassesPerClass wideness = 
			new AverageNumberOfSubClassesPerClass(owlOntologyManager, reasoner);
		AverageNumberOfSuperClassesPerClassCount clasComplexity = 
			new AverageNumberOfSuperClassesPerClassCount(owlOntologyManager, reasoner);
		AverageClassHierarchyDepth nesting = 
			new AverageClassHierarchyDepth(owlOntologyManager, reasoner);
		RatioOfDefinedToPrimitiveClassCount potential = 
			new RatioOfDefinedToPrimitiveClassCount(owlOntologyManager);
		
		//create the classification
		metrics.add(new SizeClassification(owlOntologyManager, size));
		metrics.add(new ClassExpressionComplexityClassification(owlOntologyManager, classexpcomplexity));
		metrics.add(new InformationRichnessClassification(owlOntologyManager, inforichness));
		metrics.add(new HierarchyComplexityClassification(owlOntologyManager, clasComplexity));
		metrics.add(new HierarchyWidenessClassification(owlOntologyManager, wideness));
		metrics.add(new HierarchyNestingClassification(owlOntologyManager, nesting));
		metrics.add(new InferentialPotentialClassification(owlOntologyManager, potential));
		
		return metrics;
	}
	
	public ArrayList<AbstractClassificationMetric> getInferredMetricClassifications(OWLOntology ontology){
		ArrayList<AbstractClassificationMetric> metrics = new ArrayList<AbstractClassificationMetric>();
		
		metrics.add(new InferentialRichnessClassification(owlOntologyManager, inferentialRichness));
		metrics.add(new JustificationRichnessClassification(owlOntologyManager, justs));
	
		
		return metrics;
	}
	
/******************************************  AXIOM TYPE ******************************************************************/
	public List<OWLMetric> getAxiomTypeMetrics(){
		List<OWLMetric> metrics = new ArrayList<OWLMetric>();
		
		metrics.addAll(AxiomTypeCountMetricFactory.createMetrics(owlOntologyManager));
		
		return metrics;
	}
	
}
