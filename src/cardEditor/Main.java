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

	public static void main(String[] args) {



		System.out.println(workingDir + "/file1.json");

		MainWindow window = new MainWindow();
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SwingUtilities.updateComponentTreeUI(window.frame);


	}


	public static void saveToJSON(JSONArray itemList, String filename) {
		//Write to JSON File

		System.out.println(workingDir + "/" + filename);
		try (FileWriter file = new FileWriter(workingDir + "/" + filename)) 
		{

			file.write(itemList.toString());

		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static JSONArray openFromJSON(File file) {
		//Open JSON File

		System.out.println(file);
		JSONParser parser = new JSONParser();
		JSONArray jArray = null;
		
		//Parse JSON File
		try {
			jArray = (JSONArray) parser.parse(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //the location of the file
		
		
		return jArray;

	}
}
