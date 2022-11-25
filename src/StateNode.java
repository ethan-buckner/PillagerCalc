import java.util.ArrayList;

public class StateNode {
	public int mana;
	public int lifeTotal;
	public int cardsPlayed;
	public int nextComboReduction;
	public int nextSpellReduction;
	public int nextCardReduction;
	public int nextNextCardReduction;
	public ArrayList<Card> hand;
	public ArrayList<Card> battlefield;
	public Card played;
	public String targeted;
	public StateNode previous;

	public boolean explored;

	public int getCardsPlayed() {
		return cardsPlayed;
	}

	public StateNode(ArrayList<Card> startHand, int startMana, int startLifeTotal) {  // Root node constructor
		explored = false;
		previous = null;
		played = null;
		targeted = "untargeted";
		mana = startMana;
		cardsPlayed = 0;
		nextComboReduction = 0;
		nextSpellReduction = 0;
		nextCardReduction = 0;
		nextNextCardReduction = 0;
		lifeTotal = startLifeTotal;
		hand = startHand;
		battlefield = new ArrayList<>();
	}

	public StateNode(StateNode copy) { // Copy constructor
		explored = copy.explored;
		previous = copy.previous;
		targeted = copy.targeted;
		mana = copy.mana;
		cardsPlayed = copy.cardsPlayed;
		nextComboReduction = copy.nextComboReduction;
		nextSpellReduction = copy.nextSpellReduction;
		nextCardReduction = copy.nextCardReduction;
		nextNextCardReduction = copy.nextNextCardReduction;
		lifeTotal = copy.lifeTotal;
		hand = new ArrayList<>(copy.hand);
		battlefield = new ArrayList<>(copy.battlefield);
	}

	public StateNode(StateNode parent, Card lastPlayed, String lastTarget) throws TargetingException { // Standard Constructor
		explored = false;
		previous = parent;
		played = lastPlayed;
		targeted = lastTarget;
		cardsPlayed = previous.cardsPlayed;
		lifeTotal = previous.lifeTotal;
		int modifiedCost = played.cost - ((played.isCombo() ? previous.nextComboReduction : 0) + previous.nextCardReduction);
		if (modifiedCost < 0) modifiedCost = 0;
		mana = parent.mana - modifiedCost;
		hand = new ArrayList<>(previous.hand);
		hand.remove(played);
		battlefield = new ArrayList<>(previous.battlefield);
		boolean shark = previous.isSharkActive();
		Card targetedMinion = findTargetedMinion(lastTarget);
		nextComboReduction = played.isCombo() ? 0 : previous.nextComboReduction;
		nextSpellReduction = played.isSpell() ? 0 : previous.nextSpellReduction;
		nextCardReduction = previous.nextNextCardReduction;
		nextNextCardReduction = 0;
		switch (played.name) {
			case "pillager" -> lifeTotal -= (cardsPlayed * (shark ? 2 : 1));
			case "scabbs" -> {
				if (cardsPlayed > 0) {
					nextCardReduction += (shark ? 6 : 3);
					nextNextCardReduction += (shark ? 6 : 3);
				}
			}
			case "foxy" -> nextComboReduction += (shark ? 4 : 2);
			case "step" -> {
				battlefield.remove(targetedMinion);
				Card newCard = new Card(targetedMinion.name, 0);
				newCard.setCostToDefault();
				newCard.cost -= 2;
				hand.add(newCard);
			}
			case "prep" -> nextSpellReduction = 2;
			case "potion" -> {
				for (Card minion : battlefield) {
					if (hand.size() >= 10) break;
					hand.add(new Card(minion.name, 1));
				}
			}
			case "tenwu" -> {
				battlefield.remove(targetedMinion);
				hand.add(new Card(targetedMinion.name, 1));
			}
			case "dancer" -> {
				int reps = shark ? 2 : 1;
				for (int i = 0; i < reps; i++) {
					if (hand.size() < 10) {
						hand.add(new Card("coin", 0));
					}
				}
			}
			case "coin" -> mana += 1;
		}
		cardsPlayed = parent.getCardsPlayed() + 1;
		if (!played.isSpell()) battlefield.add(played);
	}

	public boolean isSharkActive() {
		boolean found = false;
		for (Card c : battlefield) {
			if (c.name.equals("shark")) {
				found = true;
				break;
			}
		}
		return found;
	}

	private boolean canAfford(Card choice) {
		int modifiedCost = choice.cost - (
				(choice.isCombo() ? nextComboReduction : 0) + (choice.isSpell() ? nextSpellReduction : 0) + nextCardReduction
		);
		if (modifiedCost < 0) modifiedCost = 0;
		return (modifiedCost <= mana);
	}

	private Card findTargetedMinion(String target) throws TargetingException {
		Card targetedMinion = null;
		if (target.equals("untargeted")) {
			return null;
		}
		boolean found = false;
		String[] cardNames = new String[]{"pillager", "scabbs", "foxy", "shark", "step", "potion", "tenwu", "coin", "dancer", "prep"};
		for (String s : cardNames) {
			if (target.equals(s)) {
				found = true;
				break;
			}
		}
		if (!found) throw new TargetingException("Unrecognized target for targeted effect");
		for (Card minion : battlefield) {
			if (minion.name.equals(target)) targetedMinion = minion;
		}
		if (targetedMinion == null) throw new TargetingException("failed to target");
		return targetedMinion;
	}


	public ArrayList<StateNode> generateChildren() throws TargetingException {
		ArrayList<StateNode> childList = new ArrayList<>();
		for (Card choice : hand) {
			if (choice.name.equals("coin") && nextSpellReduction == 0 && nextCardReduction == 0) {
				childList = new ArrayList<>();
				childList.add(new StateNode(this, choice, "untargeted"));
				return childList;
			}
			if (canAfford(choice)) {
				if (choice.isSpell() || battlefield.size() < 7) {
					if (choice.keywords.contains("targeted")) {
						for (Card c : battlefield) {
							childList.add(new StateNode(this, choice, c.name));
						}
					} else {
						childList.add(new StateNode(this, choice, "untargeted"));
					}
				}
			}
		}
		return childList;
	}

	public String backtrack() {
		StringBuilder outString = new StringBuilder();
		StateNode selected = this;
		while (selected != null && selected.played != null) {
			if (!selected.targeted.equals("untargeted")) {
				outString.insert(0, selected.played.name + "(" + selected.targeted + ") ");
			}
			else {
				outString.insert(0, selected.played.name + " ");
			}
			selected = selected.previous;
		}
		return outString.toString();
	}
}
