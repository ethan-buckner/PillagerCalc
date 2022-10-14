import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;


class StateNodeTest {
	StateNode root = new StateNode(new ArrayList<Card>(), 0, 30);
	ArrayList<Card> testHand = new ArrayList<>();
	boolean foundStep;
	boolean foundFoxy;
	boolean foundTenwu;
	boolean foundShark;
	boolean foundScabbs;
	boolean foundPotion;
	boolean foundPillager;

	@BeforeEach
	void setUpBeforeEach() {
		testHand.add(new Card("step", 0));
		testHand.add(new Card("foxy", 2));
		testHand.add(new Card("tenwu", 2));
		testHand.add(new Card("shark", 4));
		testHand.add(new Card("scabbs", 4));
		testHand.add(new Card("potion", 4));
		testHand.add(new Card("pillager", 6));

		foundStep = false;
		foundFoxy = false;
		foundTenwu = false;
		foundShark = false;
		foundScabbs = false;
		foundPotion = false;
		foundPillager = false;
	}

	@AfterEach
	void tearDownAfterEach() {
		testHand.clear();
	}
/**
	@org.junit.jupiter.api.Test
	void test_generateChildren_4mana_noModifiers() throws TargetingException {
		StateNode bench = new StateNode(root, new Card("pillager", 6), "untargeted",
				4, 30, 0, 0, 0,
				testHand, new ArrayList<Card>());
		ArrayList<StateNode> children = bench.generateChildren();
		for (StateNode child : children) {
			if (child.played.name.equals("step")) foundStep = true;
			if (child.played.name.equals("foxy")) foundFoxy = true;
			if (child.played.name.equals("tenwu")) foundTenwu = true;
			if (child.played.name.equals("shark")) foundShark = true;
			if (child.played.name.equals("scabbs")) foundScabbs = true;
			if (child.played.name.equals("potion")) foundPotion = true;
			if (child.played.name.equals("pillager")) foundPillager = true;
		}
		assertAll(
				() -> assertFalse(foundStep),
				() -> assertTrue(foundFoxy),
				() -> assertFalse(foundTenwu),
				() -> assertTrue(foundShark),
				() -> assertTrue(foundScabbs),
				() -> assertTrue(foundPotion),
				() -> assertFalse(foundPillager)
		);
	}

	@org.junit.jupiter.api.Test
	void test_generateChildren_0mana_4combo() throws TargetingException {
		StateNode bench = new StateNode(root, new Card("pillager", 6), "untargeted",
				0, 30, 4, 0, 0,
				testHand, new ArrayList<Card>());
		ArrayList<StateNode> children = bench.generateChildren();
		for (StateNode child : children) {
			if (child.played.name.equals("step")) foundStep = true;
			if (child.played.name.equals("foxy")) foundFoxy = true;
			if (child.played.name.equals("tenwu")) foundTenwu = true;
			if (child.played.name.equals("shark")) foundShark = true;
			if (child.played.name.equals("scabbs")) foundScabbs = true;
			if (child.played.name.equals("potion")) foundPotion = true;
			if (child.played.name.equals("pillager")) foundPillager = true;
		}
		assertAll(
				() -> assertFalse(foundStep),
				() -> assertFalse(foundFoxy),
				() -> assertFalse(foundTenwu),
				() -> assertFalse(foundShark),
				() -> assertTrue(foundScabbs),
				() -> assertFalse(foundPotion),
				() -> assertFalse(foundPillager)
		);
	}

	@org.junit.jupiter.api.Test
	void test_generateChildren_0mana_6nextCard() throws TargetingException {
		StateNode bench = new StateNode(root, new Card("pillager", 6), "untargeted",
				0, 30, 0, 6, 6,
				testHand, new ArrayList<Card>());
		ArrayList<StateNode> children = bench.generateChildren();
		for (StateNode child : children) {
			if (child.played.name.equals("step")) foundStep = true;
			if (child.played.name.equals("foxy")) foundFoxy = true;
			if (child.played.name.equals("tenwu")) foundTenwu = true;
			if (child.played.name.equals("shark")) foundShark = true;
			if (child.played.name.equals("scabbs")) foundScabbs = true;
			if (child.played.name.equals("potion")) foundPotion = true;
			if (child.played.name.equals("pillager")) foundPillager = true;
		}
		assertAll(
				() -> assertFalse(foundStep),
				() -> assertTrue(foundFoxy),
				() -> assertFalse(foundTenwu),
				() -> assertTrue(foundShark),
				() -> assertTrue(foundScabbs),
				() -> assertTrue(foundPotion),
				() -> assertTrue(foundPillager)
		);
	}

	@org.junit.jupiter.api.Test
	void test_generateChildren_0mana_manyReductions() throws TargetingException {
		StateNode bench = new StateNode(root, new Card("pillager", 6), "untargeted",
				0, 30, 4, 3, 3,
				testHand, new ArrayList<Card>());
		ArrayList<StateNode> children = bench.generateChildren();
		for (StateNode child : children) {
			if (child.played.name.equals("step")) foundStep = true;
			if (child.played.name.equals("foxy")) foundFoxy = true;
			if (child.played.name.equals("tenwu")) foundTenwu = true;
			if (child.played.name.equals("shark")) foundShark = true;
			if (child.played.name.equals("scabbs")) foundScabbs = true;
			if (child.played.name.equals("potion")) foundPotion = true;
			if (child.played.name.equals("pillager")) foundPillager = true;
		}
		assertAll(
				() -> assertFalse(foundStep),
				() -> assertTrue(foundFoxy),
				() -> assertFalse(foundTenwu),
				() -> assertFalse(foundShark),
				() -> assertTrue(foundScabbs),
				() -> assertFalse(foundPotion),
				() -> assertTrue(foundPillager)
		);
	}

	@org.junit.jupiter.api.Test
	void test_generateChildren_0mana_step() throws TargetingException {
		ArrayList<Card> testBattlefield = new ArrayList<>();
		testBattlefield.add(new Card("scabbs", 4));
		testBattlefield.add(new Card("tenwu", 2));

		StateNode bench = new StateNode(root, new Card("pillager", 6), "untargeted",
				0, 30, 0, 0, 0,
				testHand, testBattlefield);
		ArrayList<StateNode> children = bench.generateChildren();
		boolean foundSecondStep = false;
		boolean foundThirdStep = false;
		for (StateNode child : children) {
			if (child.played.name.equals("step")) {
				if (!foundStep) foundStep = true;
				else if (!foundSecondStep) foundSecondStep = true;
				else foundThirdStep = true;
			}
			if (child.played.name.equals("foxy")) foundFoxy = true;
			if (child.played.name.equals("tenwu")) foundTenwu = true;
			if (child.played.name.equals("shark")) foundShark = true;
			if (child.played.name.equals("scabbs")) foundScabbs = true;
			if (child.played.name.equals("potion")) foundPotion = true;
			if (child.played.name.equals("pillager")) foundPillager = true;
		}
		boolean finalFoundThirdStep = foundThirdStep;
		boolean finalFoundSecondStep = foundSecondStep;
		assertAll(
				() -> assertTrue(foundStep),
				() -> assertTrue(finalFoundSecondStep),
				() -> assertFalse(finalFoundThirdStep),
				() -> assertFalse(foundFoxy),
				() -> assertFalse(foundTenwu),
				() -> assertFalse(foundShark),
				() -> assertFalse(foundScabbs),
				() -> assertFalse(foundPotion),
				() -> assertFalse(foundPillager)
		);
	}

	@org.junit.jupiter.api.Test
	void test_generateChildren_fullBattlefield() throws TargetingException {
		ArrayList<Card> testBattlefield = new ArrayList<>();
		testBattlefield.add(new Card("scabbs", 4));
		testBattlefield.add(new Card("tenwu", 2));
		testBattlefield.add(new Card("shark", 4));
		testBattlefield.add(new Card("foxy", 2));
		testBattlefield.add(new Card("foxy", 2));
		testBattlefield.add(new Card("pillager", 6));
		testBattlefield.add(new Card("pillager", 6));

		StateNode bench = new StateNode(root, new Card("pillager", 6), "untargeted",
				10, 30, 0, 0, 0,
				testHand, testBattlefield);
		ArrayList<StateNode> children = bench.generateChildren();
		for (StateNode child : children) {
			if (child.played.name.equals("step")) foundStep = true;
			if (child.played.name.equals("foxy")) foundFoxy = true;
			if (child.played.name.equals("tenwu")) foundTenwu = true;
			if (child.played.name.equals("shark")) foundShark = true;
			if (child.played.name.equals("scabbs")) foundScabbs = true;
			if (child.played.name.equals("potion")) foundPotion = true;
			if (child.played.name.equals("pillager")) foundPillager = true;
		}
		assertAll(
				() -> assertTrue(foundStep),
				() -> assertFalse(foundFoxy),
				() -> assertFalse(foundTenwu),
				() -> assertFalse(foundShark),
				() -> assertFalse(foundScabbs),
				() -> assertTrue(foundPotion),
				() -> assertFalse(foundPillager)
		);
	}
	*/
}