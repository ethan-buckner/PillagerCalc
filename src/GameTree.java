import java.util.ArrayList;
import java.util.LinkedList;


public class GameTree {
	public StateNode root;

	public GameTree(StateNode newRoot) {
		root = newRoot;
	}

	public StateNode treeSearch() throws TargetingException {
		LinkedList<StateNode> searchQueue = new LinkedList<>();
		root.explored = true;
		searchQueue.addLast(root);
		while (searchQueue.size() > 0) {
			StateNode n = searchQueue.poll();
			if (n.lifeTotal <= 0) return n;
			for (StateNode child : n.generateChildren()) {
				if (!child.explored) {
					child.explored = true;
					searchQueue.addLast(child);
				}
			}
		}
		return null;
	}

	public StateNode treeSearchID() throws TargetingException {
		int maxDepth = ((int) root.lifeTotal / 4) + 5;
		for (int depth = 0; depth < maxDepth+1; depth++) {
			Pair<StateNode, Boolean> data = dls(root, depth);
			if (data.first != null) {
				return data.first;
			}
			else if (!data.second) {
				return null;
			}
		}
		return null;
	}

	private Pair<StateNode, Boolean> dls(StateNode node, int depth) throws TargetingException {
		boolean lethal = node.lifeTotal <= 0;
		if (depth == 0) {
			if (lethal) {
				return new Pair<>(node, true);
			}
			else return new Pair<>(null, true);
		}
		else if (depth > 0) {
			boolean any_remaining = false;
			ArrayList<StateNode> children = node.generateChildren();
			for (StateNode child : children) {
				Pair<StateNode, Boolean> data = dls(child, depth-1);
				if (data.first != null) return new Pair<>(data.first, true);
				if (data.second) any_remaining = true;
			}
			return new Pair<>(null, any_remaining);
		}
		return null;
	}

}

class Pair<type1, type2> {
	public type1 first;
	public type2 second;

	public Pair(type1 first, type2 second) {
		this.first = first;
		this.second = second;
	}
}
