
import uk.ac.manchester.cs.bhig.owlmetrics.application.OWLMetricGenerator;
import uk.ac.manchester.cs.bhig.owlmetrics.utilities.MetricLogger;


public class OWLMetricsStarter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		if(args.length >= 2){
			MetricLogger.create();
			OWLMetricGenerator gen = new OWLMetricGenerator(); 
			gen.generateMetrics(args[0], args[1], true, true);
		}
		else {
			System.out.println(String.format(
					"Usage java -cp ... %s <ontology> <reasoner name>",
					OWLMetricGenerator.class.getCanonicalName()));
		}
		
		
		

	}

}
