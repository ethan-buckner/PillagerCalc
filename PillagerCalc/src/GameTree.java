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
			if (n.lethal) return n;
			for (StateNode child : n.generateChildren()) {
				if (!child.explored) {
					child.explored = true;
					searchQueue.addLast(child);
				}
			}
		}
		return null;
	}
}
