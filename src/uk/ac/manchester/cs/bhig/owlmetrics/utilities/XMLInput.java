package uk.ac.manchester.cs.bhig.owlmetrics.utilities;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLInput {
	
	private Document doc;
	private XPath xpath; 
	private FileReader inReader;
	
	public void open(String filename){
		try {
			//open the saved xml file
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(filename);
			doc.getDocumentElement().normalize();

			// create the XPath
			XPathFactory factory = XPathFactory.newInstance();
			xpath = factory.newXPath();
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Map<String, String> getClusterMetrics(){
		Map<String, String> metrics = new LinkedHashMap<String, String>();
		try {
			XPathExpression expr = xpath.compile("Ontology-Metrics/Clusters/Cluster");
			NodeList nodes = (NodeList) expr.evaluate(doc,
				        XPathConstants.NODESET);
			if (nodes != null && nodes.getLength() > 0) {
				for (int i = 0; i < nodes.getLength(); i++) {
					Node childNode = nodes.item(i);
					if (childNode.getNodeType() == Node.ELEMENT_NODE) {
						if (childNode.getNodeName().equals("Cluster")) {
							// get the title of the metric
							String category = parseNodeByTag(childNode,"description");
							metrics.put(category+ "class", parseNodeByTag(childNode, "classification"));
							metrics.put(category, parseNodeByTag(childNode, "value"));
						}
					}
					
				}
			}
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return metrics;
	}
	
	public ArrayList<String> getClusterValues(){
		ArrayList<String> values = new ArrayList<String>();
		try {
			XPathExpression expr = xpath.compile("Ontology-Metrics/Clusters/Cluster");
			NodeList nodes = (NodeList) expr.evaluate(doc,
				        XPathConstants.NODESET);
			if (nodes != null && nodes.getLength() > 0) {
				for (int i = 0; i < nodes.getLength(); i++) {
					Node childNode = nodes.item(i);
					if (childNode.getNodeType() == Node.ELEMENT_NODE) {
						if (childNode.getNodeName().equals("Cluster")) {
							// get the title of the metric
							parseNodeByTag(childNode,"description");
							values.add(parseNodeByTag(childNode, "classification"));
							values.add(parseNodeByTag(childNode, "value"));
						}
					}
					
				}
			}
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return values;
	}
	
	public static String parseNodeByTag(Node node, String tagName) {
		String value = "";

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

	public ArrayList<String> getClusterCategories() {
		ArrayList<String> categories = new ArrayList<String>();
		try {
			XPathExpression expr = xpath.compile("Ontology-Metrics/Clusters/Cluster");
			NodeList nodes = (NodeList) expr.evaluate(doc,
				        XPathConstants.NODESET);
			if (nodes != null && nodes.getLength() > 0) {
				for (int i = 0; i < nodes.getLength(); i++) {
					Node childNode = nodes.item(i);
					if (childNode.getNodeType() == Node.ELEMENT_NODE) {
						if (childNode.getNodeName().equals("Cluster")) {
							// get the category of the cluster
							String category = parseNodeByTag(childNode,"description");
							categories.add(category);
							categories.add(category + "-value");
							//categories.add(category + "_value");
							//categories.add(parseNodeByTag(childNode, "inferred-value"));
						}
					}
					
				}
			}
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return categories;
	}

	public ArrayList<String> getOntologyClassification(String xpathSearch) {
		ArrayList<String> values = new ArrayList<String>();
		try {
			XPathExpression expr = xpath.compile(xpathSearch);
			NodeList nodes = (NodeList) expr.evaluate(doc,
				        XPathConstants.NODESET);
			if (nodes != null && nodes.getLength() > 0) {
				for (int i = 0; i < nodes.getLength(); i++) {
					Node childNode = nodes.item(i);
					if (childNode.getNodeType() == Node.ELEMENT_NODE) {
						if (childNode.getNodeName().equals("Cluster")) {
							// get the category of the cluster
							parseNodeByTag(childNode,"description");
							values.add(parseNodeByTag(childNode, "classification"));
							//values.add(parseNodeByTag(childNode, "value"));
							//categories.add(parseNodeByTag(childNode, "inferred-value"));
						}
					}
					
				}
			}
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return values;
	}

	public ArrayList<String> getFullMetricAttributes() {
		ArrayList<String> attributes = new ArrayList<String>();
		
		try {
			XPathExpression expr = xpath.compile("Ontology-Metrics//metric");
			NodeList nodes = (NodeList) expr.evaluate(doc,
				        XPathConstants.NODESET);
			if (nodes != null && nodes.getLength() > 0) {
				for (int i = 0; i < nodes.getLength(); i++) {
					Node childNode = nodes.item(i);
					if (childNode.getNodeType() == Node.ELEMENT_NODE) {
						if (childNode.getNodeName().equals("metric")) {
							// get the category of the cluster
							String category = parseNodeByTag(childNode,"description");
							if(!category.equals("Imports")){
								attributes.add(category.replaceAll(" ", "-") + "-assert");
								attributes.add(category + "-infer");
							}
							
						}
					}
					
				}
			}
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		
		return attributes;
	}

	public Map<String, String> getFullMetrics() {
		Map<String, String> metrics = new LinkedHashMap<String, String>();

		try {
			XPathExpression expr = xpath.compile("Ontology-Metrics//metric");
			NodeList nodes = (NodeList) expr.evaluate(doc,
					XPathConstants.NODESET);
			if (nodes != null && nodes.getLength() > 0) {
				for (int i = 0; i < nodes.getLength(); i++) {
					Node childNode = nodes.item(i);
					if (childNode.getNodeType() == Node.ELEMENT_NODE) {
						if (childNode.getNodeName().equals("metric")) {
							// get the category of the cluster
							String description = parseNodeByTag(childNode,"description");
							String assMetric = description.replaceAll(" ", "-") + "-assert";
							String infMetric = description.replaceAll(" ", "-") + "-infer";
							if(!description.equals("Imports")){
								String assvalue = parseNodeByTag(childNode,"asserted-value");
								String infvalue = parseNodeByTag(childNode,"inferred-value");
								if(assvalue.equals("") && infvalue.equals("")){
									metrics.put(assMetric, parseNodeByTag(childNode,"value"));
									metrics.put(infMetric, "-");
								}
								else if(assvalue.equals("") && !infvalue.equals("")){
									metrics.put(assMetric,"-");
									metrics.put(infMetric, infvalue);
								} else{
									metrics.put(assMetric, assvalue);
									metrics.put(infMetric, infvalue);
								}
							}
							
						}
					}

				}
			}
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return metrics;
	}
	
	public ArrayList<String> getFullMetricValues() {
		ArrayList<String> metrics = new ArrayList<String>();

		try {
			XPathExpression expr = xpath.compile("Ontology-Metrics//metric");
			NodeList nodes = (NodeList) expr.evaluate(doc,
					XPathConstants.NODESET);
			if (nodes != null && nodes.getLength() > 0) {
				for (int i = 0; i < nodes.getLength(); i++) {
					Node childNode = nodes.item(i);
					if (childNode.getNodeType() == Node.ELEMENT_NODE) {
						if (childNode.getNodeName().equals("metric")) {
							// get the category of the cluster
							String description = parseNodeByTag(childNode,"description");
//							String assMetric = description.replaceAll(" ", "-") + "-assert";
//							String infMetric = description.replaceAll(" ", "-") + "-infer";
							if(!description.equals("Imports")){
								String assvalue = parseNodeByTag(childNode,"asserted-value");
								String infvalue = parseNodeByTag(childNode,"inferred-value");
								if(assvalue.equals("") && infvalue.equals("")){
									metrics.add(parseNodeByTag(childNode,"value"));
									metrics.add("-");
								}
								else if(assvalue.equals("") && !infvalue.equals("")){
									metrics.add("-");
									metrics.add(infvalue);
								} else{
									metrics.add(assvalue);
									metrics.add(infvalue);
								}
							}
							
						}
					}

				}
			}
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return metrics;
	}

}
