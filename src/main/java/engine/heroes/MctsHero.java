package engine.heroes;

import engine.Card;
import engine.Game;
import engine.Hero;
import engine.Move;
import engine.cards.Minion;
import engine.mcts.MctsAlgorithm;
import engine.mcts.Node;
import engine.moves.*;

import java.util.List;

public class MctsHero extends AbstractHero implements HeuristicHero {

    public MctsHero() {
        super(null,null,null,-1);
    }

    public MctsHero(Game game, String name, List<Card> initialDeck, int initialHandSize) {
        super(game, name, initialDeck, initialHandSize);
    }

    @Override
    public void chooseHeuristicMove() {
        // MctsAlgorithm mctsAlgorithm = new MctsAlgorithm(new Node(game));
        long start = System.currentTimeMillis();
        long end = start;
        while(end - start <= 10 * 1000) { //game.getActiveHero() == this
            game.checkForGameEnd();
            if(game.isGameOver()) {
                return;
            }
            MctsAlgorithm mctsAlgorithm = new MctsAlgorithm(new Node(game));
            Move bestMove = mctsAlgorithm.run();
            printMoveInfo(bestMove);
            performMove(bestMove);
            end = System.currentTimeMillis();
        }
        game.checkForGameEnd();
        if(game.isGameOver()) {
            System.out.println("Gierka skonczona");
            return;
        } else {
            if(game.getActiveHero().getName().equals(this.name)) {
                performMove(new EndRound(this));
            }
        }
    }

    @Override
    public int evaluate(Move toDo) {
        return 0;
    }

    private void printMoveInfo(Move move) {
        if(move instanceof PutCard) {
            System.out.println("[Ruch MCTS] PutCard " + ((Minion) move.getCard()).getName());
        } else if (move instanceof UseSpell) {
            System.out.println("[Ruch MCTS] UseSpell " + ((UseSpell) move.getCard()).getClass().getName());
        } else if (move instanceof AttackHero) {
            System.out.println("[Ruch MCTS] AttackHero (" + ((AttackHero) move.getCard()).getClass().getName() + ") - cel " + ((AttackHero) move.getCard()).getHeroToGetAttacked().getName());
        } else if (move instanceof AttackMinion) {
            System.out.println("[Ruch MCTS] AttackMinion (" + ((AttackMinion) move.getCard()).getClass().getName() + ") - cel " + ((AttackMinion) move.getCard()).getMinionToGetAttacked().getName());
        } else {
            System.out.println("[Ruch MCTS] EndRound");
        }

    }
}
