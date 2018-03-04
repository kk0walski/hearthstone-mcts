package engine.mcts;

import engine.Move;
import engine.cards.spells.Fireball;

public class MctsAlgorithm {
	
	private static final int CP_FACTOR = 1;
	private Node root;

	public MctsAlgorithm(Node root) {
		this.root = root;
	}
	
	public Move run() {
		long start = System.currentTimeMillis();
		long end = start;
		Move bestFound = null;
		while(end - start >= 10 * 1000) {
			Node child = treePolicy(root);
			int delta = defaultPolicy(child);
			backup(child, delta);
			end = System.currentTimeMillis();
		}
		
		return bestFound;
	}
	
	Node treePolicy(Node root) {
		while(nodeIsNonTerminal(root)) {
			if(nodeIsNotFullyExpanded(root)) {
				return expand(root);
			} else {
				root = bestChild(root, CP_FACTOR); // tu sie moze cos wykrzaczac z refkami
				root.getMoveInNode().performMove();
			}
		}
		return root;
	}
	
	Node expand(Node root) {
		Move move = root.getUntriedMoves().pop();
		Node child = new Node(root, move);
		root.addChild(child);
		child.getMoveInNode().performMove();
		return child;
	}
	
	Node bestChild(Node root, int cFactor) {
		return null; // TODO wzorek
	}
	
	int defaultPolicy(Node root) {
		while(nodeIsNonTerminal(root)) {
			// TODO - kopiujemy ca³¹ grê (deep copy trzeba naklepaæ do ca³ego obiektu gry i w ogole wszystkiego
			// i rozgrywamy gierke do konca i zwracamy 0 lub 1 w zaleznosci od tego czy wygrana
		}
	}
	
	void backup(Node root, int delta) {
		while(root != null) {
			if(root.getGame().getWinner() == root.getGame().getFirstHero()) {
				root.addFirstHeroWin();
			} else {
				root.addSecondHeroWin();
			}
		}
		
		// winner = null
		
		// TODO - delta jest niepotrzebna, bo prowadzimy i tak gre do konca w defaultPolicy i w root.getGame().getWinner() mamy zwyciezce
		// musimy tylko pamietac o tym, zeby rollbackowac winnera na null i gameOver na false i zeby przy rollbackowaniu ruchu EndRound zmieniac aktywnego gracza na przeciwnego
	}

	private boolean nodeIsNotFullyExpanded(Node root) {
		return !(root.getUntriedMoves().isEmpty());
	}

	private boolean nodeIsNonTerminal(Node root) {
		return !(root.getGame().isGameOver());
	}
}
