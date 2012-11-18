package uk.ac.manchester.cs.bhig.owlmetrics.outputRendering;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.semanticweb.owlapi.metrics.OWLMetric;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import uk.ac.manchester.cs.bhig.owlmetrics.classification.AbstractClassificationMetric;
import uk.ac.manchester.cs.bhig.owlmetrics.utilities.MetricLogger;

import com.megginson.sax.DataWriter;

/**
 * @author elenimikroyannidi
 * 
 *         Class responsible for building and parsing an XML file
 * 
 */
public class XMLOutputWriter {

	// No generics
	Document dom;
	public static DataWriter writer;
	public static FileWriter out;
	// private static PrintWriter p;

	// variable for writing in the xml file
	public static boolean inferredMetric = false; // if true then it is inferred

	// parsing xml variables
	public static String xmlfilename;
	public static String xlsfilename;
	static Document doc;

	// variables refering to the excel file
	private static int xlrow = 1;
	private static int sheet2row = 0;

	static XlsRenderer xlsfile;

	// variables for the cluster xml - log file
	public static DataWriter log;
	private static FileWriter logOut;

	public static String type;

	public static void initializeXMLOutputWriter(String ontologyName) {
		try {
//			 xmlfilename = "MetricResults/" + ontologyName +
//			 "-OWLMetrics-v1.xml";
			 
			 xmlfilename = "MetricResults/" + ontologyName.replace("?", "-") +
			 "-OWLMetrics-v1.0.2.xml";
			 
			 out = new FileWriter(xmlfilename);

			
			xlrow = 0;
			sheet2row = 0;
			type = null;

			// create the xml writer
			writer = new DataWriter(out);

			writer.startDocument();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static void setValueType(String valueType) {
		type = valueType;
	}

	public static void createXMLOntologyHistoryFile() {
		try {
			//xmlfilename = "Ontology-history-OWLMetrics-v1.xml";
			logOut = new FileWriter(xmlfilename);
			log = new DataWriter(logOut);

			log.startDocument();

			log.startElement("history");

		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		} catch (SAXException e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
	
	/**
	 * For future use for multiple ontologies.
	 * Save the history of the ontologies that were 
	 * opened and save the link of their xml file. 
	 *  
	 * @param ontologyIRI
	 * @param xmlLinkMetrics
	 */
	public static void writeOntologyIRIinXMLHistroyFile(String ontologyIRI,
			String xmlLinkMetrics) {
		try {
			log.startElement("iri");
			log.characters(ontologyIRI);
			log.endElement("iri");
			log.startElement("metrics-file");
			log.characters(xmlLinkMetrics);
			log.endElement("metrics-file");

		} catch (SAXException e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

	public static void startTag(String tag) {
		try {
			writer.startElement(tag);
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}

	public static void startTag(String tag, String value) {
		try {
			writer.startElement(tag);
			writer.characters(value);
			writer.endElement(tag);

		} catch (SAXException e) {
			e.printStackTrace();
		}
	}

	public static void closeTag(String tag) {
		try {
			writer.endElement(tag);
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}

	public static void closeTag(String tag, String value) {
		try {
			writer.endElement(tag, value);
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}

	public static void startAndCloseTag(String tag, String value) {
		try {
			writer.startElement(tag);
			writer.characters(value);
			writer.endElement(tag);
			// writer.dataElement(tag, value);
			// writer.endElement(tag, value);
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}


	/**
	 * It writes the generated metric in an XML file
	 * 
	 * @param description
	 * @param value
	 */
	public static void writeMetricInXML(String description, String value) {
		XMLOutputWriter.startTag("metric");
		XMLOutputWriter.startAndCloseTag("description", description);
		if(type==null)
			XMLOutputWriter.startAndCloseTag("value", value);
		else
			XMLOutputWriter.startAndCloseTag(type + "-value", value);
		XMLOutputWriter.closeTag("metric");
	}
	
	/**
	 * It writes the generated metric in an XML file
	 * 
	 * @param description
	 * @param value
	 */
	public static void writePairedMetricInXML(String description, String assertedvalue, String inferredValue) {
		XMLOutputWriter.startTag("metric");
		XMLOutputWriter.startAndCloseTag("description", description);
		if(assertedvalue!=null)
			XMLOutputWriter.startAndCloseTag("asserted-value", assertedvalue);
		else
			XMLOutputWriter.startAndCloseTag("asserted-value", "-");
		if(inferredValue!=null)
			XMLOutputWriter.startAndCloseTag("inferred-value", inferredValue);
		else
			XMLOutputWriter.startAndCloseTag("inferred-value", "-");
		
		XMLOutputWriter.closeTag("metric");
	}
	
	public static void writeClusterMetric(String description, String classification, String value){
		XMLOutputWriter.startTag("Cluster");
		XMLOutputWriter.startAndCloseTag("description", description);
		XMLOutputWriter.startAndCloseTag("classification", classification);
		XMLOutputWriter.startAndCloseTag("value", value);
		XMLOutputWriter.closeTag("Cluster");
	}
	
	public static void writeMultiMetricInXML(String description, String entityName, String value){
		XMLOutputWriter.startTag("multi-metric");
		XMLOutputWriter.startAndCloseTag("description", description);
		XMLOutputWriter.startAndCloseTag("entity", entityName);
		XMLOutputWriter.startAndCloseTag(type + "-value", value);
		XMLOutputWriter.closeTag("multi-metric");
	}

	public static String parseNodeByTag(Node node, String tagName) {
		String value = "";

		// if (node.getNodeType() == Node.ELEMENT_NODE) {

		Element fstElmnt = (Element) node;
		NodeList fstNmElmntLst = fstElmnt.getElementsByTagName(tagName);
		if (!fstNmElmntLst.equals(null)) {
			Element fstNmElmnt = (Element) fstNmElmntLst.item(0);

			if (fstNmElmnt != null) {
				NodeList fstNm = fstNmElmnt.getChildNodes();

				if (!fstNm.equals(null))
					value = ((Node) fstNm.item(0)).getNodeValue();
			}
		}
		return value;
	}
	
	/**
	 * 
	 * @return
	 */
	public static Element getRootNode() {
		Document doc = parseXMLRootNode();

		Element root = doc.getDocumentElement();

		return root;
	}


	/**
	 * Method used by the jsp page for the generation of the metrics.
	 * 
	 * @return an array with the results
	 */
	public static void getMetricValues() {
		Element root = getRootNode();
		xlsfile = new XlsRenderer();

		xlsfile.createExcelFile(xmlfilename + ".xls");
		xlsfile.initialiseDataSheet(xlsfile.sheet1);
		xlsfile.initialiseDataSheet(xlsfile.sheet2);

		recurseXMLNodes(root);

		xlsfile.writeXls();
		xlsfile.closeXlsFile();
	}
	
	//variables for parsing the ontology
	private static XPathFactory factory;
	private static XPath xpath;

	/**
	 * Modifies the XML so both asserted and inferred values of the metrics to
	 * be in the same node
	 */
	public static void editXMLToFinalForm() {
		String description = "";
		String value = "";

		try {
			//open the saved xml file
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(xmlfilename);
			doc.getDocumentElement().normalize();

			// create the XPath
			factory = XPathFactory.newInstance();
			xpath = factory.newXPath();
			
			 XPathExpression expr = xpath.compile("Ontology-Metrics/Inferred-Metrics");
			
			//get the inferred node
			 NodeList inferredList = (NodeList) expr.evaluate(doc,
				        XPathConstants.NODESET);
			 
			 expr = xpath.compile("Ontology-Metrics/Inferred-Metrics/metric");
			 //get the nodeList of the metrics under this node
			 NodeList inferredMetrics = (NodeList) expr.evaluate(doc,
				        XPathConstants.NODESET); 			 

			if (inferredMetrics != null && inferredMetrics.getLength() > 0) {
				for (int i = 0; i < inferredMetrics.getLength(); i++) {
					Node childNode = inferredMetrics.item(i);
					// System.out.println("TEST " + childNode);
					if (childNode.getNodeType() == Node.ELEMENT_NODE) {
						// check first if it is in the multimetrics
						if (childNode.getNodeName().equals("metric")) {
							// get the description of the metric
							description = parseNodeByTag(childNode,
									"description");
							System.out.println("description" + " of metric : "
									+ description);
							//check for duplicate
							Node duplicateNode = checkForDuplicate(description);
							if (duplicateNode!= null){
								System.out.println("Duplicate was found");
								//add the inferred value in the metric
								Element fstElmnt = (Element) childNode;
								NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("inferred-value");
								//System.out.println(child.getNodeValue());
								duplicateNode.appendChild(fstNmElmntLst.item(0));
								
								//now remove the child from the inferred metrics
								inferredList.item(0).removeChild(childNode);
								
							}

						}
					}
				}
			}

			//save the modified file
			Transformer xformer = TransformerFactory.newInstance().newTransformer();
		    xformer.transform(new DOMSource(doc), new StreamResult(new File(xmlfilename)));
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static Node checkForDuplicate(String description) {
		//Node duplicateNode = null;
		try {
			NodeList owlMetricsList = doc.getElementsByTagName("OWLMetrics");
			
			//get the metric nodes with the use of XPath
			 XPathExpression expr = xpath.compile("Ontology-Metrics/OWLMetrics//metric");
			 NodeList nodes = (NodeList) expr.evaluate(doc,
				        XPathConstants.NODESET);
			 for(int i=0; i<nodes.getLength(); i++){
				 Node childNode =  nodes.item(i);
				 String assdescription = parseNodeByTag(childNode,
					"description");
				if(assdescription.equalsIgnoreCase(description)){
					//make the substitution
					return childNode;
					
				}
			 }
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}

	/**
	 * It parses the nodes of the xml and 
	 * then passes its content on an xlsfile.
	 * 
	 * @param node
	 */
	public static void recurseXMLNodes(Node node) {
		NodeList metricElmList = node.getChildNodes();

		String value = "";
		
		//initialise xls sheet
		xlsfile.createExcelCaption(1, 0, "Asserted",
				xlsfile.sheet1);
		xlsfile.createExcelCaption(2, 0, "Inferred",
				xlsfile.sheet1);

		for (int i = 0; i < metricElmList.getLength(); i++) {
			Node childNode = metricElmList.item(i);
			// System.out.println("TEST " + childNode);
			if (childNode.getNodeType() == Node.ELEMENT_NODE) {
				if (childNode.getNodeName().equals("multi-metric") || childNode.getNodeName().equals("Multi-metrics")) {
					//do nothing
				} else if(childNode.getNodeName().equals("Cluster")){
					String description = parseNodeByTag(childNode, "description");
					xlsfile.createExcelLabel(0, xlrow, description,
							xlsfile.sheet1);
					String classification = parseNodeByTag(childNode, "classification");
					xlsfile.createExcelLabel(1, xlrow, classification,
							xlsfile.sheet1);
					value = parseNodeByTag(childNode, "value");
					xlsfile.createExcelLabel(2, xlrow, value,
							xlsfile.sheet1);
					xlrow++;
				}
				else if (childNode.getNodeName().equals("metric")) {
					value = parseNodeByTag(childNode, "description");
					//System.out.println("description" + " of metric : " + value);

						xlsfile.createExcelLabel(0, xlrow, value,
								xlsfile.sheet1);
						value = parseNodeByTag(childNode, "asserted-value");
						if(value.equals("")){
							value = parseNodeByTag(childNode, "value");
						}
						System.out.println("value" + " of metric : " + value);
						xlsfile.createExcelLabel(1, xlrow, value,
								xlsfile.sheet1);
						String infValue = parseNodeByTag(childNode, "inferred-value");
						xlsfile.createExcelLabel(2, xlrow, infValue,
								xlsfile.sheet1);
						xlrow++;


				} else {

					String tagName = childNode.getNodeName().replaceAll("-",
							" ");
					xlsfile.createExcelCaption(0, xlrow, tagName,
					xlsfile.sheet1);
					xlrow++;

					if (tagName.equals("Asserted Metrics")) {

					} else if (tagName.equals("Inferred Metrics")) {
						// do nothing
					} 

					recurseXMLNodes(childNode);
				}
			}
		}
	}

	/**
	 * method that parses the xml root node. Used as a method for initialising
	 * the xml file.
	 * 
	 * @return
	 */
	public static Document parseXMLRootNode() {
		String rootNode;

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(xmlfilename);
			doc.getDocumentElement().normalize();
			rootNode = doc.getDocumentElement().getNodeName();
			System.out.println("Root element " + rootNode);

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doc;
	}

	/**
	 * Closes the xml file.
	 */
	public static void saveChangesAndCloseXMLFile() {
		try {
			writer.endDocument();
			out.close();
			// p.close();
			System.out.println("The xml file was saved with name "
					+ xmlfilename);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}// closeXMLFile()
	
	public static void openXMLFile(){
		try {
			//open the saved xml file
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(xmlfilename);
			doc.getDocumentElement().normalize();

			// create the XPath
			factory = XPathFactory.newInstance();
			xpath = factory.newXPath();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Object[] getOntologyGeneralInfoFromXML(){
		openXMLFile();
		
		ArrayList<String> generalInfo = new ArrayList<String>();
		try {
			XPathExpression expr = xpath.compile("Ontology-Metrics/General-Information/metric");
			NodeList nodes = (NodeList) expr.evaluate(doc,
				        XPathConstants.NODESET);
			if (nodes != null && nodes.getLength() > 0) {
				for (int i = 0; i < nodes.getLength(); i++) {
					Node childNode = nodes.item(i);
					// System.out.println("TEST " + childNode);
					if (childNode.getNodeType() == Node.ELEMENT_NODE) {
						// check first if it is in the multimetrics
						if (childNode.getNodeName().equals("metric")) {
							// get the description of the metric
							generalInfo.add(parseNodeByTag(childNode,
									"description"));
							generalInfo.add(parseNodeByTag(childNode, "value"));
						}
					}
					
				}
			}
			
			for(int i=0; i<generalInfo.size(); i++){
				System.out.println(generalInfo.get(i));
			}
			
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return generalInfo.toArray();
	}

	public static Object[] getOntologyClassificationInfoFromXML() {
		
		return getValuesOfAMetricNode("Ontology-Metrics/Clusters/Cluster", "value").toArray();
	}
	
	private static ArrayList<String> getValuesOfAMetricNode(String xpathSearch, String valueType){
		ArrayList<String> results = new ArrayList<String>();
		try {
			XPathExpression expr = xpath.compile(xpathSearch);
			NodeList nodes = (NodeList) expr.evaluate(doc,
				        XPathConstants.NODESET);
			if (nodes != null && nodes.getLength() > 0) {
				for (int i = 0; i < nodes.getLength(); i++) {
					Node childNode = nodes.item(i);
					// System.out.println("TEST " + childNode);
					if (childNode.getNodeType() == Node.ELEMENT_NODE) {
						// check first if it is in the multimetrics
						if (childNode.getNodeName().equals("metric")) {
							// get the description of the metric
							results.add(parseNodeByTag(childNode,
									"description"));
							results.add(parseNodeByTag(childNode, valueType));
						}
					}
					
				}
			}
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		
		return results;
		
	}
	
	
	public static DefaultMutableTreeNode getOWLMetricsTree(){
		DefaultMutableTreeNode metricTree = new DefaultMutableTreeNode();
		

		// create the tree from the
		NodeList rootnodes = getXpathNodeList("Ontology-Metrics/OWLMetrics");
		
		//do the procedure for the root node
		for(int i=0; i<rootnodes.getLength(); i++){
			Node childNode = rootnodes.item(i);
			if (childNode.getNodeType() == Node.ELEMENT_NODE) {
				if (childNode.getNodeName().equals("OWLMetrics")){
					recurseOWLMetricTree(metricTree,childNode);
				}		
			}
		}
		return metricTree;	
	}
	

	/**
	 * Parses the OWLMetrics tree in the xml and 
	 * saves it for the jtree view  
	 * @param jtreeNode
	 * @param xmlNode
	 */
	private static void recurseOWLMetricTree(DefaultMutableTreeNode metricTree,
			Node xmlParentNode) {
		
		// if the xmlnode is a metric
		if(xmlParentNode.getNodeName().equals("metric")){
			//parse the description
			String description = parseNodeByTag(xmlParentNode, "description");
			String value = "AS: " + parseNodeByTag(xmlParentNode, "asserted-value");
			if(value.equals("AS: "))
				value = "";
			value = " (" + value + " IN: " + parseNodeByTag(xmlParentNode, "inferred-value") + ")";
			String metricNodeName = description + value;
			DefaultMutableTreeNode metricNode = new DefaultMutableTreeNode(metricNodeName);
			metricTree.add(metricNode);
			return;
		}
		//skip the multi-metric values 
		else if(xmlParentNode.getNodeName().equals("multi-metric")){
			return;
		}
			
			//add the node in the tree
			String tagName = xmlParentNode.getNodeName().replaceAll("-"," ");
			DefaultMutableTreeNode jchildNode = new DefaultMutableTreeNode(tagName);

			metricTree.add(jchildNode);

			NodeList children= xmlParentNode.getChildNodes();
			for(int i=0; i<children.getLength(); i++){
				Node childNode = children.item(i);
				if (childNode.getNodeType() == Node.ELEMENT_NODE) {
					recurseOWLMetricTree(jchildNode,childNode);
				}
			}
	}

	private static NodeList getXpathNodeList(String expression){
		NodeList nodes = null;
		try {
			XPathExpression expr = xpath.compile(expression);
			nodes = (NodeList) expr.evaluate(doc,
				        XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nodes;
	}

	public static void close() {
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static String getExistentialRestrictionsWithDepthFromXML(){
		
		NodeList metricList = getXpathNodeList("Ontology-Metrics/OWLMetrics//multi-metric");
		
		String text="";
		int order = 1;
		
		for(int i=0; i<metricList.getLength(); i++){
			Node multimetric = metricList.item(i);
			if (multimetric.getNodeType() == Node.ELEMENT_NODE) {
				String description = parseNodeByTag(multimetric, "description");
				if(description.equals("Depth of existential restriction")){
					String entity = parseNodeByTag(multimetric, "entity");
					String assValue = parseNodeByTag(multimetric, "asserted-value");
					//String infValue = parseNodeByTag(multimetric, "inferred-value");
					//add content in the text
					text +=  String.valueOf(order) + ". \n";
					text += entity + "\n";
					text += description + ":";
					text += assValue + "\n\n";  
					
					order++;
				}
				
			}
		}
		
		return text;
	}
	
	public static String getUniversalRestrictionsWithDepthFromXML(){
		
		NodeList metricList = getXpathNodeList("Ontology-Metrics/OWLMetrics//multi-metric");
		
		String text="";
		int order = 1;
		
		for(int i=0; i<metricList.getLength(); i++){
			Node multimetric = metricList.item(i);
			if (multimetric.getNodeType() == Node.ELEMENT_NODE) {
				String description = parseNodeByTag(multimetric, "description");
				if(description.equals("Depth of universal restriction")){
					String entity = parseNodeByTag(multimetric, "entity");
					String assValue = parseNodeByTag(multimetric, "asserted-value");
					//String infValue = parseNodeByTag(multimetric, "inferred-value");
					//add content in the text
					text +=  String.valueOf(order) + ". \n";
					text += entity + "\n";
					text += description + ":";
					text += assValue + "\n\n";  
					
					order++;
				}
				
			}
		}
		
		return text;
	}

	public static void writeSetOfPairsOfMetrics(List<OWLMetric> clasm,
			List<OWLMetric> infpairList, String category) {
		
		String tag = category.replaceAll(" ", "-");
		
		startTag(tag);
		
		for(OWLMetric metric : clasm){
			MetricLogger.log("Writing " + metric.getName() + "...");
			System.out.println("Writing " + metric.getName() + "...");
			String inferredValue = null;
			for(OWLMetric infmetric : infpairList){
				if(metric.getName().equals(infmetric.getName())){
					if(infmetric.getValue() == null)
						inferredValue = "-";
					else
						inferredValue = infmetric.getValue().toString();
				}
			}
			
			if(inferredValue != null && metric.getValue() !=null)
				writePairedMetricInXML(metric.getName(), 
						metric.getValue().toString(), inferredValue);
			else if(inferredValue == null && metric.getValue() !=null)
				writeMetricInXML(metric.getName(), 
						metric.getValue().toString());
			else
				writeMetricInXML(metric.getName(), 
						"-");
		
		}
		
		closeTag(tag);
	}

	public static void writeSetOfMetrics(List<OWLMetric> entList,
			String category, boolean inferred) {
		if(inferred)
			type = "inferred";
		else
			type = "asserted";
		
		String tag = category.replaceAll(" ", "-");
		
		startTag(tag);
		for(OWLMetric metric : entList){
			MetricLogger.log("Writing " + metric.getName() + "...");
			System.out.println("Writing " + metric.getName() + "...");
			if(metric.getValue()!=null)
				writeMetricInXML(metric.getName(), 
						metric.getValue().toString());
			else
				writeMetricInXML(metric.getName(), "-");
		}
		
		closeTag(tag);
		
	}

	public static void writeClassificationSetOfMetrics(
			List<AbstractClassificationMetric> classificationList) {
		
		startTag("Clusters");
		for(AbstractClassificationMetric m: classificationList){
			MetricLogger.log("Writing " + m.getName() + "...");
			System.out.println("Writing " + m.getName() + "...");
			writeClusterMetric(m.getName(), m.getClassification(), m.getValue().toString());
		}
		closeTag("Clusters");
		
	}
	

}
