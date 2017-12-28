package cardEditor;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CardData {
	
	@JsonProperty("item")
	String item;
	
	@JsonProperty("description")
	String description;
	
	@JsonProperty("category")
	String category;
	
	@JsonProperty("price")
	String price;
	
	@JsonProperty("coverURL")
	String coverURL;
	
	public String toString() {
		return "\n\nItem: " + item + "\nDescription: " + description + "\nCategory: " + category + "\nPrice: "
				+ price + "\nCover URL: " + coverURL;
		
	}
	
}
