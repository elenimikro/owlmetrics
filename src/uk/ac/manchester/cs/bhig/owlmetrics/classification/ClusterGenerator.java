package uk.ac.manchester.cs.bhig.owlmetrics.classification;

public class ClusterGenerator {
	
	private double SMALL_INFERENTIAL_RICHNESS = 0.0;
	private double MEDIUM_INFERENTIAL_RICHNESS = 0.5;
	private double HIGH_INFERENTIAL_RICHNESS = 1.0;
	
	//size variables 
	private int SMALL_SIZE = 0;
	private int MEDIUM_SIZE = 50;
	private int BIG_SIZE = 1000;
	
	//information richness variables
	private double SMALL_INFORMATION_RICHNESS = 0.0;
	private double MEDIUM_INFORMATION_RICHNESS = 1.0;
	private double HIGH_INFORMATION_RICHNESS = 2.0;
	
	//class hierarchy complexity 
	private double SMALL_HIERARCHY_COMPLEXITY = 0.0;
	private double MEDIUM_HIERARCHY_COMPLEXITY = 1.0;
	private double HIGH_HIERARCHY_COMPLEXITY = 3.0;
	
	//class hierarchy wideness
	private double SMALL_HIERARCHY_WIDENESS = 0.0;
	private double MEDIUM_HIERARCHY_WIDENESS = 4.0;
	private double HIGH_HIERARCHY_WIDENESS = 10.0;
	
	//class hierarchy nesting 
	private double SMALL_HIERARCHY_NESTING = 0.0;
	private double MEDIUM_HIERARCHY_NESTING = 5.0;
	private double HIGH_HIERARCHY_NESTING = 10.0;
	
	//class expression 
	private double SMALL_CLASS_EXPRESSION_COMPLEXITY = 0.0;
	private double MEDIUM_CLASS_EXPRESSION_COMPLEXITY = 0.05;
	private double HIGH_CLASS_EXPRESSION_COMPLEXITY = 0.1;
	
	private double SMALL_INFERENTIAL_POTENTIAL = 0.0;
	private double MEDIUM_INFERENTIAL_POTENTIAL = 0.5;
	private double HIGH_INFERENTIAL_POTENTIAL = 2.0;
	
	private double SMALL_JUSTIFICATORY_RICHNESS = 0.0;
	private double MEDIUM_JUSTIFICATORY_RICHNESS = 3.0;
	private double HIGH_JUSTIFICATORY_RICHNESS = 6.0;
	
	
	public ClusterGenerator() {
		// TODO Auto-generated constructor stub
		
	}
	
	public String getInferentialRichnessClassification(double inferentialRich){
		String inferentialRichness = classifyMetric(inferentialRich, 
				HIGH_INFERENTIAL_RICHNESS, MEDIUM_INFERENTIAL_RICHNESS, SMALL_INFERENTIAL_RICHNESS);
		return inferentialRichness;
	}
	
	
	public String getInferentialPotentialClassification(double definedToPrimitiveRatio){
		String inferentialPotential = classifyMetric(definedToPrimitiveRatio,
				HIGH_INFERENTIAL_POTENTIAL, MEDIUM_INFERENTIAL_POTENTIAL, SMALL_INFERENTIAL_POTENTIAL);
		
		return inferentialPotential;
	}
	
	public String getSizeClassification(int classNumber){
		
		String size = classifyMetric(classNumber, BIG_SIZE, MEDIUM_SIZE, SMALL_SIZE);
		return size;
	}
	
	public String getInformationRichnessClassification(double avgSubclasses){
		String informationRichness = classifyMetric(avgSubclasses, HIGH_INFORMATION_RICHNESS, 
				MEDIUM_INFORMATION_RICHNESS, SMALL_INFORMATION_RICHNESS);
		
		return informationRichness;
	}
	
	public String getJustificationRichnessClassification(double avgjust){
		
		String justificationRichness = classifyMetric(avgjust, HIGH_JUSTIFICATORY_RICHNESS, 
				MEDIUM_JUSTIFICATORY_RICHNESS, SMALL_JUSTIFICATORY_RICHNESS);
		
		return justificationRichness;
	}
	
	public String getTaxonomyComplexity(double avgSuperclasses){
		String taxonomyComplexity = classifyMetric(avgSuperclasses, 
				HIGH_HIERARCHY_COMPLEXITY, MEDIUM_HIERARCHY_COMPLEXITY, SMALL_HIERARCHY_COMPLEXITY);
		
		return taxonomyComplexity;
	}
	
	public String getTaxonomyWideness(double metric){
		String taxonomyWideness = classifyMetric(metric, 
				HIGH_HIERARCHY_WIDENESS, MEDIUM_HIERARCHY_WIDENESS, SMALL_HIERARCHY_WIDENESS);
		
		return taxonomyWideness; 
	}
	
	public String getTaxonomyNesting(double metric){
		String taxonomyNesting = classifyMetric(metric, 
				HIGH_HIERARCHY_NESTING, MEDIUM_HIERARCHY_NESTING, SMALL_HIERARCHY_NESTING);
		
		return taxonomyNesting; 
	}
	
		
	public String getClassExpressionComplexityClassification(double avgClassExprNesting){
		String classExpressionComplexity = classifyMetric(avgClassExprNesting,
				HIGH_CLASS_EXPRESSION_COMPLEXITY, MEDIUM_CLASS_EXPRESSION_COMPLEXITY, 
				SMALL_CLASS_EXPRESSION_COMPLEXITY);
		
		return classExpressionComplexity;
	}
	
	private String classifyMetric(double metric, double high, double medium, double small){
		String classification = "n/a";
		if(metric >= high){
			classification = "High";
		}
		else if(metric >= medium){
			classification = "Medium";
		}
		else if (metric >= small){
			classification = "Small";
		}
		else{
			System.out.println("Invalid metric " 
					+ metric);
		}
		
		return classification;
	}

}
