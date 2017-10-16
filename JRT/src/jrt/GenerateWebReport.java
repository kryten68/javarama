package jrt;

import java.util.HashMap;
import java.util.Properties;

public class GenerateWebReport {	
	
	static HashMap<String, Object> dataMap = new HashMap<String, Object>();
	static String propertiesFile = "properties/report.properties";
	static String htmlTemplate = "templates/template.twig";
	static String instructionsJson = "properties/instructions.json";

	public static void main(String[] args) {		
			
		Properties prop = FuncsLib.loadProperties(propertiesFile);	
			
		dataMap.put("valOne", "Hello");
		dataMap.put("valTwo", " World");
		
		String ss = prop.getProperty("someStuff");
		
		dataMap.put("woop", ss);
		
		String instrJsonString = FuncsLib.readInstructionsJson(instructionsJson);
		//System.out.println(instrJsonString);
		
		//JsonMethods.getSingleInstruction(instrJsonString, "2");
		
		JsonMethods.processInstructions(instrJsonString);
		
		//FuncsLib.renderTemplate(htmlTemplate, dataMap);
		
		System.out.println(dataMap);
		
	}
	
}
