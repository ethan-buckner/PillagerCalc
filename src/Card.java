import java.util.ArrayList;

public class Card {
	final String[] cardNames = {"pillager", "scabbs", "foxy", "shark", "step", "potion", "tenwu"};
	String name;
	int cost;
	ArrayList<String> keywords;

	public Card(String effect, int manaCost) {
		cost = manaCost;
		keywords = new ArrayList<>();
		name = effect;
		switch (effect) {
			case "tenwu" -> keywords.add("targeted");
			case "potion" -> keywords.add("spell");
			case "pillager", "scabbs" -> keywords.add("combo");
			case "step" -> {
				keywords.add("spell");
				keywords.add("targeted");
			}
		}

	}



	public void setCostToDefault() {
		switch (name) {
			case "pillager" -> cost = 6;
			case "scabbs", "shark", "potion" -> cost = 4;
			case "foxy", "tenwu" -> cost = 2;
			case "step" -> cost = 0;
		}
	}


	public boolean isCombo() {
		boolean found = false;
		for (String s : keywords) {
			if (s.equals("combo")) {
				found = true;
				break;
			}
		}
		return found;
	}

	public boolean isSpell() {
		boolean found = false;
		for (String s : keywords) {
			if (s.equals("spell")) {
				found = true;
				break;
			}
		}
		return found;
	}
}
