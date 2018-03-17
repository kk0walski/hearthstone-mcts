package engine.mcts;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

import engine.Card;
import engine.Game;
import engine.Move;
import engine.cards.spells.Fireball;
import engine.heroes.AbstractHero;
import engine.heroes.RandomHero;
import engine.moves.PutCard;

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
        while (end - start <= 3 * 1000) { // originally end - start <= 10 * 1000, temporary true end - start <= 10 * (Integer.MAX_VALUE/11)
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
                root = bestChild(root, CP_FACTOR); // tu sie moze cos wykrzaczac z refkami
                // root.getMoveInNode().performMove(); // też perform move na activehero powinno być
                root.getGame().getActiveHero().performMove(root.getMoveInNode());
                // todo root.getGame().checkForGameEnd(); // todo - cofniety
                // todo if(!(root.getGame().isGameOver())) {
                    root.setUntriedMoves(new ArrayDeque<Move>(root.getGame().getActiveHero().getAvailableMoves())); // tego brakowalo
                // todo }
                // root.setUntriedMoves(new ArrayDeque<Move>(root.getGame().getActiveHero().getAvailableMoves())); // zdaje sie ze tutaj tez brakowalo
            }
        }
        return root;
    }

    Node expand(Node root) {
        Move move = root.getUntriedMoves().pop();
        Node child = new Node(root, move);
        // System.out.println("[EXPAND] root move: " + root.getMoveInNode() + " | child move: " + child.getMoveInNode() + " | child move cost: " + child.getMoveInNode().getCard());
//        if(root.getMoveInNode()!=null)
//     	   System.out.println();
        root.addChild(child);
        // TODO - wywołać performMove na activeHero z parametrem child.getMoveInNode()
        root.getGame().getActiveHero().performMove(child.getMoveInNode());
        // todo root.getGame().checkForGameEnd();
        // todo if(!(root.getGame().isGameOver())) {
            child.setUntriedMoves(new ArrayDeque<Move>(root.getGame().getActiveHero().getAvailableMoves())); // tego brakowalo // todo - moze produkowac errorsy
        // todo }
        // child.getMoveInNode().performMove(); // czyli to niepotrzebne
        return child;
    }

    Node bestChild(Node root, int cFactor) {
        Node bestChild = null;
        double bestFoundChildFormulaValue = Integer.MIN_VALUE;
        for (Node child : root.getChilds()) {
            double current = evaluateBestChildFormula(child, cFactor);
            if (current > bestFoundChildFormulaValue) {
                bestChild = child;
                bestFoundChildFormulaValue = current; //tego brakowalo
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
     *
     * @param root
     * @return 1 if firstHero wins, -1 if secondHero wins.
     */
    int defaultPolicy(Node root) {
        // todo root.getGame().checkForGameEnd();
        // todo if(!root.getGame().isGameOver()) {
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
        /* todo } else {
            if(root.getGame().getWinner().getName() == root.getGame().getFirstHero().getName()) {
                return 1;
            } else if (root.getGame().getWinner().getName() == root.getGame().getSecondHero().getName()){
                return -1;
            } else {
                throw new IllegalStateException("Invalid winner. #2");
            }
        } */
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
                nodeToBackup.getGame().getActiveHero().setAvailableMoves( ((AbstractHero) nodeToBackup.getGame().getActiveHero()).possibleMoves()); /// tutaj luju
            }
            // nodeToBackup.getMoveInNode().rollback();
            List<Card> xx = nodeToBackup.getGame().getActiveHero().getBoard();
            nodeToBackup = nodeToBackup.getParent();
        }

        // TODO - prowadzimy gre do konca w defaultPolicy i w root.getGame().getWinner() mamy zwyciezce
        // TODO -  musimy tylko pamietac o tym, zeby przy rollbackowaniu ruchu EndRound zmieniac aktywnego gracza na przeciwnego
    }

    private boolean nodeIsNotFullyExpanded(Node root) {
        return !(root.getUntriedMoves().isEmpty());
    }

    private boolean nodeIsNonTerminal(Node root) {
        root.getGame().checkForGameEnd();
        return !(root.getGame().isGameOver());
    }
}
