package jrt;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpMethods {

	public static void executeGet(String instructionNumber, HashMap instructions) throws IOException {
		System.out.println("Hello from http get method!");	
		
		// HTTP Client Work Starts Here
		OkHttpClient client = new OkHttpClient();
		
		Request request =	new Request.Builder()
				.url(JsonMethods.getInstructionValue(instructions, "endpoint"))
				.build();
		Response response = client.newCall(request).execute();
		
		String responseJson = response.body().string();
		
		System.out.println(responseJson);
		
		String jsonPath  =  JsonMethods.getInstructionValue(instructions, "jsonpath");
		String dataLabel =  JsonMethods.getInstructionValue(instructions, "datalabel");
		
		JsonMethods.assignDataToLabel(responseJson, jsonPath, dataLabel);
		
	}

	public static void executePost(String instructionNumber, HashMap instructions) {
		System.out.println("Hello from http post method!");

		// Print the Key
		System.out.println("\nKey: " + instructionNumber);

		String endpoint = JsonMethods.getInstructionValue(instructions, "endpoint");
		String datalabel = JsonMethods.getInstructionValue(instructions, "datalabel");

		System.out.println("Got: " + endpoint);
		System.out.println("Got: " + datalabel);

	}
}
