package uk.ac.manchester.cs.bhig.owlmetrics.owlclassexpressions;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.HashSet;

import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

import uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer.ManchesterOWLSyntaxObjectRenderer;

public class OWLAllValuesFromAxiomDepthsSet extends OWLClassExpressionDepth {

	public OWLAllValuesFromAxiomDepthsSet(OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
	}

	@Override
	public String getName() {
		return "OWLAllValuesFrom axioms with depth";
	}

	@Override
	protected HashMap<String, Integer> recomputeMetric() {
		HashMap<String, Integer> expSet = new HashMap<String, Integer>();
		HashSet<OWLObjectAllValuesFrom> restrictions = super.getOWLAllValuesFromAxioms();
		for(OWLObjectAllValuesFrom restriction : restrictions){
			int depth = super.getOWLClassExpressionDepth(restriction);
			expSet.put(getManchesterRendering(restriction), depth);
		}
		return expSet;
	}
	
	private static String getManchesterRendering(OWLObjectAllValuesFrom restriction) {
		SimpleShortFormProvider fp = new SimpleShortFormProvider();
		StringWriter wr = new StringWriter();
		ManchesterOWLSyntaxObjectRenderer render = new ManchesterOWLSyntaxObjectRenderer(
				wr, fp);
		restriction.accept(render);
		String str = wr.getBuffer().toString();

		return str;
	}
	
	
//	private static String getManchesterRenderingOfAxiom(OWLAxiom ax) {
//		SimpleShortFormProvider fp = new SimpleShortFormProvider();
//		StringWriter wr = new StringWriter();
//		ManchesterOWLSyntaxObjectRenderer render = new ManchesterOWLSyntaxObjectRenderer(
//				wr, fp);
//		
//		ax.accept(render);
//
//		String str = wr.getBuffer().toString();
//
//		return str;
//	}

	

}
