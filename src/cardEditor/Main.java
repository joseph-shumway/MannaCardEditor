package cardEditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {

	public static String workingDir = System.getProperty("user.dir");
	public static String ACCESS_TOKEN = "bpHcTOJhqHAAAAAAAAABZASWYVkvPgxEQbqwmuMGvi2qUv6fGzwdfFL4rs_Th_Ky";
	
	
	public static void main(String[] args) throws Exception {



		System.out.println(workingDir + "/file1.json");

		MainWindow window = new MainWindow();
		
		//try {
		//	UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		//} catch (Exception e) {e.printStackTrace();}
		
		SwingUtilities.updateComponentTreeUI(window.frame);


	}


	public static void saveToJSON(JSONArray itemList, String filename) {
		//Write to JSON File

		System.out.println(workingDir + "/" + filename);
		try (FileWriter file = new FileWriter(workingDir + "/" + filename)) 
		{
			file.write(itemList.toString());
		} catch (Exception e) {e.printStackTrace();}

	}
	
	public static JSONArray openFromJSON(File file) {
		//Open JSON File

		System.out.println(file);
		JSONParser parser = new JSONParser();
		JSONArray jArray = null;
		
		//Parse JSON File
		try {
			jArray = (JSONArray) parser.parse(new FileReader(file));
		} catch (Exception e) {e.printStackTrace();}
		
		
		return jArray;

	}
}
