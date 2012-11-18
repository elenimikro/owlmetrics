///**
// * 
// */
//package uk.ac.manchester.cs.bhig.owlmetrics.outputRendering;
//
///**
// * @author elenimikroyannidi
// * 
// * controls XML and XLS output
// * 
// */
//public class OutputWriter {
//
//	private XLSRenderer xlsRender;
//	int column;
//	int row;
//	
//	public void InitializeOutputWriter(String ontologyName) {
//		XMLOutputWriter.initializeXMLOutputWriter(ontologyName);
//		xlsRender = new XLSRenderer();
//		String filename =  "MetricResults/"  + ontologyName; 
//		xlsRender.createExcelFile(filename + ".xls");
//	}
//	
//	public void writeMetricCaptionInOutput(String tag){
//		xlsRender.createMetricCaption(tag);
//	}
//	
//	public void createColumnCaptionInExcel(int column, String caption){
//		xlsRender.createExcelColumnCaption(column, caption);
//	}
//
//	/**
//	 * Writes the metric in several outputs; XML, Excel. 
//	 * @param description
//	 * @param value
//	 */
//	public void writeMetricInOutput(String description, String value){
//		XMLOutputWriter.writeMetricInXML(description, value);
//		xlsRender.writeMetricInExcel(description, value);
//	}
//	
//	/**
//	 * Writes the multimetric in the output
//	 * @param entity
//	 * @param value
//	 */
//	public void writeMultiMetricInOutput(String entity, String value){
//		//TODO write the body of the method
//	}
//	
//	public void setColumn(int colNumber){
//		xlsRender.column = colNumber;
//	}
//	
//	
//	public void closeOutputFiles(){
//		XMLOutputWriter.saveChangesAndCloseXMLFile();
//		xlsRender.closeXlsFile();
//	}
//	
////	public void createCaption(String caption){
////		xlsRender.createExcelCaption(column, row, label, sheet)
////	}
//	
//	
//}
