package engine.mcts;

import engine.Game;
import engine.Move;
import engine.cards.spells.Fireball;
import engine.heroes.RandomHero;

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
        while (end - start <= 10 * 1000) {
            Node child = treePolicy(root);
            int delta = defaultPolicy(child);
            backup(child, delta);
            end = System.currentTimeMillis();
        }

        return bestFound;
    }

    Node treePolicy(Node root) {
        while (nodeIsNonTerminal(root)) {
            if (nodeIsNotFullyExpanded(root)) {
                return expand(root);
            } else {
                root = bestChild(root, CP_FACTOR); // tu sie moze cos wykrzaczac z refkami
                // root.getMoveInNode().performMove(); // też perform move na activehero powinno być
                root.getGame().getActiveHero().performMove(root.getMoveInNode());
            }
        }
        return root;
    }

    Node expand(Node root) {
        Move move = root.getUntriedMoves().pop();
        Node child = new Node(root, move);
        root.addChild(child);
        // TODO - wywołać performMove na activeHero z parametrem child.getMoveInNode()
        root.getGame().getActiveHero().performMove(child.getMoveInNode());
        // child.getMoveInNode().performMove(); // czyli to niepotrzebne
        return child;
    }

    Node bestChild(Node root, int cFactor) {
        Node bestChild = null;
        double bestFoundChildFormulaValue = Integer.MIN_VALUE;
        for(Node child: root.getChilds()) {
            double current = evaluateBestChildFormula(child, cFactor);
            if(current > bestFoundChildFormulaValue) {
                bestChild = child;
            }
        }
        return bestChild;
    }

    private double evaluateBestChildFormula(Node child, int cFactor) {
        int q = 0;

        if (child.getGame().getFirstHero() == child.getGame().getActiveHero()) {
            q = child.getFirstHeroWins();
        } else {
            q = child.getSecondHeroWins();
        }

        if (child.getTotalGames() == 0) {
            return Integer.MAX_VALUE;
        }

        return q / child.getTotalGames() + cFactor * Math.sqrt(
                        (2 * Math.log(child.getParent().getTotalGames()))
                                / child.getTotalGames()
        );
    }

    int defaultPolicy(Node root) {

        Game copy = root.getGame().deepCopy();
        while (!(copy.isGameOver())) {
            copy.getActiveHero().chooseRandomSimulationalMove();
        }
        if(root.getGame().getActiveHero().equals(copy.getWinner())) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * If detla == 1, then activeHero in input node has won.
     * If delta == -1, then activeHero in input node has lose.
     *
     * @param nodeToBackup input node
     * @param delta binary value of simulation (defaultPolicy) result
     */
    void backup(Node nodeToBackup, int delta) {
        if(nodeToBackup.getGame().isGameOver()) {
            nodeToBackup.getGame().setGameOver(false);
            nodeToBackup.getGame().setWinner(null);
        }
        while (nodeToBackup != null) {
            if (delta == 1) {
                nodeToBackup.addFirstHeroWin();
            } else {
                nodeToBackup.addSecondHeroWin();
            }

            root.getGame().getActiveHero().rollback(nodeToBackup.getMoveInNode());
            // nodeToBackup.getMoveInNode().rollback();

            nodeToBackup = nodeToBackup.getParent();
        }

        // TODO - prowadzimy gre do konca w defaultPolicy i w root.getGame().getWinner() mamy zwyciezce
        // TODO -  musimy tylko pamietac o tym, zeby przy rollbackowaniu ruchu EndRound zmieniac aktywnego gracza na przeciwnego
    }

    private boolean nodeIsNotFullyExpanded(Node root) {
        return !(root.getUntriedMoves().isEmpty());
    }

    private boolean nodeIsNonTerminal(Node root) {
        return !(root.getGame().isGameOver());
    }
}
