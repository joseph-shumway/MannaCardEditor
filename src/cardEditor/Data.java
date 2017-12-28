package cardEditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.json.simple.JSONArray;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Data {


	public static String getValue(String inputString, int pairIndex) {

		System.out.println(inputString);
		return "";
	}




	public static Object rowData[][] = { 
			//                   Data for each column:
			//                   Column 1  
			//				     Column 2
			//				     Column 3
			//				     Column 4
			//				     Column 5
			//				     ...etc   
			{
				"Biscuits",
				"Buttery goodies!",
				"https://s13.postimg.org/qxaqxrfjb/Biscuits.jpg",
				"2.75",
				"Traditional Breads"
			},
			{
				"Butter Rolls",
				"I Love Butter!",
				"https://s13.postimg.org/6d5wza7hz/B-_Rolls.jpg",
				"1.00",
				"Traditional Breads"
			},
			{
				"Country Soft French Bread",
				"Like typical french bread, but better",
				"https://s13.postimg.org/gn8bymctz/Fr-_Bread.jpg",
				"5.50",
				"Traditional Breads"
			},
			{
				"Herb Breadsticks",
				"Fluffy, Garlicy, Herby, Yummy",
				"https://s13.postimg.org/kwd20q37r/B-_Stix.jpg",
				"6.50",
				"Traditional Breads"
			},
			{
				"Pretzel Rolls",
				"Great for burgers!",
				"https://s13.postimg.org/i29wnbqrr/P-_Rolls.jpg",
				"1.25",
				"Traditional Breads"
			},
			{
				"Sun and Moon Bread",
				"Sweet, buttery, fluffy goodness",
				"https://s13.postimg.org/ra2540aon/S-_Lunn.jpg",
				"7.00",
				"Traditional Breads"
			},
			{
				"Whole Wheat Bread",
				"For you healthy people out there",
				"https://s13.postimg.org/hpiih50s7/WWB.jpg",
				"7.50",
				"Traditional Breads"
			},
			{
				"Ciabatta",
				"One big square artisan loaf with garlic salt",
				"https://s13.postimg.org/ce3lwe4ev/Ciabatta.jpg",
				"7.00",
				"Artisan Breads"
			},
			{
				"Focaccia",
				"Topped with herbs, chewy, and made with pure Olive Oil",
				"https://s13.postimg.org/4y4calofb/Focaccia.jpg",
				"7.50",
				"Artisan Breads"
			},
			{
				"Rosemary Bread",
				"An artisan loaf infused with Rosemary herbs",
				"https://s13.postimg.org/hpiih5nxj/Rose_Bread.jpg",
				"6.50",
				"Artisan Breads"
			},
			{
				"Short Loaf French Bread",
				"A true European French loaf",
				"https://s13.postimg.org/boktk1r0n/Short_Loaf.jpg",
				"6.00",
				"Artisan Breads"
			},
			{
				"Brownie",
				"Chocolate chewy goodness",
				"https://s13.postimg.org/trdwb8kaf/Brownies.jpg",
				"3.50",
				"Pastries"
			},
			{
				"Cheese Danish",
				"Crispy, layered pastry filled with sweetened and fluffed cream cheese",
				"https://s13.postimg.org/qkjcrls4n/Danish.jpg",
				"2.75",
				"Pastries"
			},
			{
				"Cinnamon Roll",
				"Our hottest item, these rolls are the epitome of delicious",
				"https://s13.postimg.org/sp3psom1j/C-_Rolls.jpg",
				"3.75",
				"Pastries"
			},
			{
				"Cream Puff",
				"One messy, yet delicious cousin to the Eclaire",
				"https://s13.postimg.org/7s7ho0gav/C-_Puffs.jpg",
				"4.50",
				"Pastries"
			},
			{
				"Everest",
				"Take a fork and climb your way up this giant Cinnamon Roll challenge",
				"https://s13.postimg.org/fxpjm89pj/Everest.jpg",
				"12.00",
				"Pastries"
			},
			{
				"Lemon Snap",
				"Lemony cookies that will please the buds of taste",
				"https://s13.postimg.org/k6u9of047/Lemmy.jpg",
				"0.75",
				"Pastries"
			},

			{
				"Scones",
				"Blueberry or Lemon Scones that are light and fluffy, unlike other bakeries",
				"https://s13.postimg.org/gzzq4r5dj/Scones.jpg",					
				"2.50",
				"Pastries"
			}
	};



	//Clean JSON for import------------------------
	public static boolean cleanJSON(File fileToClean) throws FileNotFoundException {
		JSONArray array = Main.openFromJSON(fileToClean);
		System.out.println("Obj to clean: " + array);
		String output = array.toString();
		output = output.replace("[", "");
		output = output.replace("]", "");

		System.out.println("Cleaned: " + output);


		try (FileWriter fw = new FileWriter(Main.workingDir + "/" + "Export1.json")) {
			fw.write(output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}


		return true;
	}



	
	
	
	
	
	//Convert JSON to POJO--------------------------
	public static List<CardData> convert(File jsonFile) {
		
		ObjectMapper objectMapper = new ObjectMapper();

		Scanner scan = null;
		try {
			scan = new Scanner(jsonFile);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		
		String jsonString = scan.nextLine();
		
		try {
			
			List<CardData> cards = objectMapper.readValue(
					jsonString, 
					objectMapper.getTypeFactory().constructCollectionType(List.class, CardData.class));
			return cards;
			
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		

	}







	//Clean Card (remove labels in front of data)
	public static List<CardData> cleanCard(List<CardData> cardList) {
		
		List<CardData> newCardList;
		
		
		for (CardData cards: cardList) {
			System.out.println(cards.category);
			cards.category = cards.category.substring(10);
			newCardList.remove(0);
			newCardList.get(0).add(1, cards.category);
		}
		
		
		
		return cardList;
	}

}






















