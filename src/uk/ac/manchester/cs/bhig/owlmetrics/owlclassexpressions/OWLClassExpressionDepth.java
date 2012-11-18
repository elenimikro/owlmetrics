package uk.ac.manchester.cs.bhig.owlmetrics.owlclassexpressions;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.semanticweb.owlapi.model.ClassExpressionType;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.manchester.cs.bhig.owlmetrics.CountableEntity;
import uk.ac.manchester.cs.bhig.owlmetrics.ValueComparator;

public abstract class OWLClassExpressionDepth extends OWLClassExpressionVisitor implements CountableEntity<Object>{
	
	private OWLDataFactory factory;
	
	//map for keeping entities and values
	private HashMap<OWLClassExpression, Integer> axiomMap = new HashMap<OWLClassExpression, Integer>();
	
	public OWLClassExpressionDepth(OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
		this.factory = owlOntologyManager.getOWLDataFactory();
	}

	/**
	 * Computes the depth of class expressions
	 * 
	 * @param clex
	 * @return 
	 */
	public int getOWLClassExpressionDepth(OWLClassExpression clex) {
		int depth=0;
		if (!clex.getClassExpressionType()
				.equals(ClassExpressionType.OWL_CLASS)) {
			if (clex.toString().indexOf("ObjectUnionOf") != -1) {
				OWLObjectUnionOf union = factory.getOWLObjectUnionOf(clex);
				for (OWLClassExpression unex : union.getOperands()) {
					depth = unex.toString().split(
							"ObjectIntersectionOf").length - 1;
				}
			}
			else {
				depth = clex.toString().split(
						"ObjectIntersectionOf").length - 1;

			}
		}
		
		axiomMap.put(clex, depth);
		return depth;
	}
	
	@Override
	public LinkedHashMap<?, Integer> getEntitiesWithValue() {
		return ValueComparator.sortHashMapByValues(axiomMap);
	}
	
//	private String getManchesterRendering(OWLObjectSomeValuesFrom desc) {
//		SimpleShortFormProvider fp = new SimpleShortFormProvider();
//		StringWriter wr = new StringWriter();
//		ManchesterOWLSyntaxObjectRenderer render = new ManchesterOWLSyntaxObjectRenderer(
//				wr, fp);
//		desc.accept(render);
//		String str = wr.getBuffer().toString();
//
//		return str;
//	}
	
	
	
}
