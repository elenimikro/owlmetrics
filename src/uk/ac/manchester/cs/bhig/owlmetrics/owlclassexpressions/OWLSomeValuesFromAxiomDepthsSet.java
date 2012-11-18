package uk.ac.manchester.cs.bhig.owlmetrics.owlclassexpressions;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.HashSet;

import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

import uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer.ManchesterOWLSyntaxObjectRenderer;

public class OWLSomeValuesFromAxiomDepthsSet extends OWLClassExpressionDepth {

	public OWLSomeValuesFromAxiomDepthsSet(OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
	}

	@Override
	public String getName() {
		return "OWLSomeValuesFrom axioms with depth";
	}

	@Override
	protected HashMap<String, Integer> recomputeMetric() {
		HashMap<String, Integer> expSet = new HashMap<String, Integer>();
		HashSet<OWLObjectSomeValuesFrom> restrictions = super.getOWLSomeValuesFromAxioms();
		for(OWLObjectSomeValuesFrom restriction : restrictions){
			int depth = super.getOWLClassExpressionDepth(restriction);
			expSet.put(getManchesterRendering(restriction), depth);
		}
		return expSet;
	}
	
	private String getManchesterRendering(OWLObjectSomeValuesFrom desc) {
		SimpleShortFormProvider fp = new SimpleShortFormProvider();
		StringWriter wr = new StringWriter();
		ManchesterOWLSyntaxObjectRenderer render = new ManchesterOWLSyntaxObjectRenderer(
				wr, fp);
		desc.accept(render);
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
