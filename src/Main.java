import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws TargetingException {
		Scanner inputScan = new Scanner(System.in);
		System.out.println("Enter your current combo pieces seperated by spaces");
		String[] uInput = inputScan.nextLine().split("\\s+");
		ArrayList<Card> hand = new ArrayList<>();
		for (String cardName : uInput) {
			int modcost = -1;
			for (int i = 0; i < cardName.length(); i++) {
				if (cardName.charAt(i) == '(') {
					modcost = Character.getNumericValue(cardName.charAt(i+1));
					cardName = cardName.substring(0,i);
				}
			}
			boolean costModified = modcost != -1;
			switch (cardName) {
				case "step", "prep", "coin" -> hand.add(new Card(cardName, costModified ? modcost : 0));
				case "foxy", "tenwu", "dancer" -> hand.add(new Card(cardName, costModified ? modcost : 2));
				case "shark", "potion", "scabbs" -> hand.add(new Card(cardName, costModified ? modcost : 4));
				case "pillager" -> hand.add(new Card(cardName, costModified ? modcost : 6));
			}
		}
		System.out.println("How much mana do you have?");
		int mana = inputScan.nextInt();
		System.out.println("What is your opponent's life total?");
		int lifeTotal = inputScan.nextInt();
		System.out.println("How many extra (non-combo) cards have you played this turn?");
		int extraCards = inputScan.nextInt();
		boolean success = false;
		for (int m = mana; m <= 10; m++) {
			StateNode root = new StateNode(hand, m, lifeTotal);
			root.cardsPlayed += extraCards;
			GameTree tree = new GameTree(root);
			StateNode lethalNode = tree.treeSearchID();
			if (lethalNode != null) {
				System.out.println("Your combo: ");
				System.out.println(lethalNode.backtrack());
				success = true;
				break;
			}
			System.out.println("Failed to find a lethal combo at " + m + " mana, trying again with " + (m+1) + " mana.");
		}
		if (!success) System.out.println("Failed to find any lethal combos at any mana with this hand.");
	}
}