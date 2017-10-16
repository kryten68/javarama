package jrt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;

public class JsonMethods {
	
	public static void getSingleInstruction(String jsonData, String instructionNumber) {
		
		// Create new string using the instruction number passed to method
		String jpath = "$.instructions." + instructionNumber;
		System.out.println("Searching using Jpath: " + jpath);
		
		// Create a document out of the parsed Json
		Object document = Configuration.defaultConfiguration().jsonProvider().parse(jsonData);
		
		// Create a new linked HashMap to contain keys and values
		LinkedHashMap<String, Object> valsHashMap =  new LinkedHashMap<String, Object>();
	
		// Finally parse the json document using the jsonpath passed in and save the results into the linked hashmap
		try {
			valsHashMap = JsonPath.read(document, jpath);
		} catch (Exception e) {
			System.out.println("\n>>Oops. Failed to read document. Here's the exception: ");
			e.printStackTrace();			
		}
		
		System.out.println("Found " + valsHashMap.size() + " keys.");
		
		if (valsHashMap.size() != 0 )  {			
			// Get an entry set for use with iterator
			Set set = valsHashMap.entrySet();			
			// Get an interator which can be used to traverse the Linked Hashmap
			Iterator iterator = set.iterator();			
			// While the Linked HashMap has another value....
			while ( iterator.hasNext()) {
				// Extract the Keys and Values from the Linked HashMap
				 Map.Entry me = (Map.Entry)iterator.next();				 
				 String a = (String) me.getKey();
				 String b = (String) me.getValue();					 
				 System.out.println("Key: " + a + "\tValue: " + b);				 
			} 			
		} else {			
			System.out.println("No instruction number " + instructionNumber + " found");			
		}        
	}

	public static void processInstructions(String jsonData) {		
		
		// Get all the instructions
		Object document = Configuration.defaultConfiguration().jsonProvider().parse(jsonData);
		
		// Create a linked HashMap for the all the instructions broken by instruction number
		LinkedHashMap<String, String> valsHashMap =  new LinkedHashMap<String, String>();
		
		// Read the json and extract the instructions
		valsHashMap = JsonPath.read(document,"$.instructions");
		
		// Create a set
		Set set = valsHashMap.entrySet();
		
		// Create an interator
		Iterator iterator = set.iterator();	
		
		// Walk through each instruction number....
		while ( iterator.hasNext() ) {
			
			 // Create a HashMap to contain each instructions values  
			 HashMap<String, String> tempHash = new HashMap<String, String>();
			 
			 // Get each block of instructions
			 Map.Entry me = (Map.Entry)iterator.next();	
			 
			 // Extract the key, which is is instruction number 1,2, etc.
			 String instructionNumber = (String) me.getKey();
			 
			 // Unpack the instructions for this key into a HashMap
			 tempHash = (HashMap<String, String>) me.getValue();
			 
			 // Dump the tempHash in current form:
			 //System.out.println("Temp hash for instruction : " + instructionNumber + " " + tempHash);
			 
			 if (	tempHash.get("endpoint").length() >  0 	&&
					tempHash.get("queryname").length() > 0  &&
					tempHash.get("datalabel").length() > 0
				) {
				 	if ( tempHash.get("method").equals("get") ) {
				 		System.out.println("\nInstruction #" + instructionNumber + " is a GET .. calling HttpGet Method" );
				 		try {
							HttpMethods.executeGet(instructionNumber, tempHash);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				 	}
				 	
				 	if ( tempHash.get("method").equals("post") ) {
				 		System.out.println("\nInstruction #" + instructionNumber + " is a POST .. calling HttpPost Method");
				 		HttpMethods.executePost(instructionNumber, tempHash);
				 	}				 		
			 }			 
				 
		}	
		
	}	

	public static String getEndpoint(HashMap instructions) {
		String endpoint = (String) instructions.get("endpoint");
		if (endpoint.length() >= 1 ) {
			return endpoint;
		} else {
			return "null";
		}
	}
	
	public static String getInstructionValue(HashMap instructions, String key) {
		String val = "";
		
		try {
			val = (String) instructions.get(key);
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return val;	
	}

	public static void assignDataToLabel(String response, String jsonPath, String dataLabel) {
		
		// Create a document out of the parsed Json
		Object document = Configuration.defaultConfiguration().jsonProvider().parse(response);
		
		String parseResult = JsonPath.read(document, jsonPath);	
		
		GenerateWebReport.dataMap.put(dataLabel, parseResult );
	}
}
