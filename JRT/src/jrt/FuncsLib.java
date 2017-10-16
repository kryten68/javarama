package jrt;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Properties;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

public class FuncsLib {
	
	public static Properties loadProperties(String propertiesFile) {
		Properties prop = new Properties();
		InputStream input = null;
		
		try {
			input = new FileInputStream(propertiesFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			System.out.println("Attempting to load properties from: " + propertiesFile);
			prop.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
	
	public static void renderTemplate(String htmlTemplate, HashMap<String, Object> dataMap) {		
		JtwigTemplate template = JtwigTemplate.classpathTemplate(htmlTemplate);        
        JtwigModel model = JtwigModel.newModel(dataMap);        
        template.render(model, System.out);			
	}

	public static String readInstructionsJson(String instructionsJsonFile) {
		String a = "";
		Path path = Paths.get(instructionsJsonFile);
		try {
			a = new String(java.nio.file.Files.readAllBytes(path));			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return a;		
	}

}
