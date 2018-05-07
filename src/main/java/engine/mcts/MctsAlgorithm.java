package engine.mcts;

import engine.Card;
import engine.Game;
import engine.Move;
import engine.heroes.AbstractHero;

import java.util.ArrayDeque;
import java.util.List;

public class MctsAlgorithm {

    private static final int CP_FACTOR = 1;
    private Node root;
    private int timeForMctsMove;

    public MctsAlgorithm(Node root, int timeForMctsMove) {
        this.root = root;
        this.timeForMctsMove = timeForMctsMove;
    }

    public Move run() {
        long start = System.currentTimeMillis();
        long end = start;
        Move bestFound = null;
        while (end - start <= timeForMctsMove * 1000) {
            Node child = treePolicy(root);
            int delta = defaultPolicy(child);
            backup(child, delta);
            end = System.currentTimeMillis();
        }
        bestFound = bestChild(root, 0).getMoveInNode();
        return bestFound;
    }

    Node treePolicy(Node root) {
        while (nodeIsNonTerminal(root)) {
            if (nodeIsNotFullyExpanded(root)) {
                return expand(root);
            } else {
                root = bestChild(root, CP_FACTOR);
                root.getGame().getActiveHero().performMove(root.getMoveInNode());
                root.setUntriedMoves(new ArrayDeque<Move>(root.getGame().getActiveHero().getAvailableMoves()));
            }
        }
        return root;
    }

    Node expand(Node root) {
        Move move = root.getUntriedMoves().pop();
        Node child = new Node(root, move);
        root.addChild(child);
        root.getGame().getActiveHero().performMove(child.getMoveInNode());
        child.setUntriedMoves(new ArrayDeque<Move>(root.getGame().getActiveHero().getAvailableMoves()));
        return child;
    }

    Node bestChild(Node root, int cFactor) {
        Node bestChild = null;
        double bestFoundChildFormulaValue = Integer.MIN_VALUE;
        for (Node child : root.getChilds()) {
            double current = evaluateBestChildFormula(child, cFactor);
            if (current > bestFoundChildFormulaValue) {
                bestChild = child;
                bestFoundChildFormulaValue = current;
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

    /**
     * @param root
     * @return 1 if firstHero wins, -1 if secondHero wins.
     */
    int defaultPolicy(Node root) {
        Game copy = root.getGame().deepCopy(root);
        while (!(copy.isGameOver())) {
            copy.getActiveHero().chooseRandomSimulationalMove();
        }
        if (copy.getWinner().getName().equals(root.getGame().getFirstHero().getName())) {
            return 1;
        } else if (copy.getWinner().getName().equals(root.getGame().getSecondHero().getName())) {
            return -1;
        } else {
            throw new IllegalStateException("Invalid winner. #1");
        }
    }

    /**
     * If detla == 1, then firstHero has won.
     * If delta == -1, then secondHero has won.
     *
     * @param nodeToBackup input node
     * @param delta        binary value of simulation (defaultPolicy) result
     */
    void backup(Node nodeToBackup, int delta) {
        if (nodeToBackup.getGame().isGameOver()) {
            nodeToBackup.getGame().setGameOver(false);
            nodeToBackup.getGame().setWinner(null);
        }
        while (nodeToBackup != null) {
            if (delta == 1) {
                nodeToBackup.addFirstHeroWin();
            } else {
                nodeToBackup.addSecondHeroWin();
            }

            if (nodeToBackup.getParent() != null) {
                List<Card> xx = nodeToBackup.getGame().getActiveHero().getBoard();
                Move e = nodeToBackup.getMoveInNode();
                nodeToBackup.getGame().getActiveHero().rollback(nodeToBackup.getMoveInNode());
                nodeToBackup.getGame().getActiveHero().setAvailableMoves(((AbstractHero) nodeToBackup.getGame().getActiveHero()).possibleMoves());
            }
            List<Card> xx = nodeToBackup.getGame().getActiveHero().getBoard();
            nodeToBackup = nodeToBackup.getParent();
        }
    }

    private boolean nodeIsNotFullyExpanded(Node root) {
        return !(root.getUntriedMoves().isEmpty());
    }

    private boolean nodeIsNonTerminal(Node root) {
        root.getGame().checkForGameEnd();
        return !(root.getGame().isGameOver());
    }

    private int getTreeDepth() {
        return maxDepth(root);
    }

    private int maxDepth(Node root) {
        if (root == null)
            return 0;

        if (root.getChilds() != null && !root.getChilds().isEmpty()) {
            int leftDepth = 0;

            if (root.getChilds().get(0) != null) {
                leftDepth = maxDepth(root.getChilds().get(0));
            }

            int rightDepth = 0;

            if (root.getChilds().size() > 1 && root.getChilds().get(1) != null) {
                rightDepth = maxDepth(root.getChilds().get(1));
            }

            int bigger = Math.max(leftDepth, rightDepth);

            return bigger + 1;
        } else {
            return 0;
        }
    }

    public int getNumberOfPlayouts() {
        return root.getTotalGames();
    }

    public int getMaximumTreeDepth() {
        return getTreeDepth();
    }
}
