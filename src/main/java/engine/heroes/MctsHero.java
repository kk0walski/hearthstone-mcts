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
        Move bestMove =null;
        while(end - start <= 10 * 1000) { //game.getActiveHero() == this
            game.checkForGameEnd();
            if(game.isGameOver() || bestMove instanceof EndRound) {
                return;
            }
            MctsAlgorithm mctsAlgorithm = new MctsAlgorithm(new Node(game));
            bestMove = mctsAlgorithm.run();
            // System.out.println(bestMove);
            printMoveInfo(bestMove);
            boolean performed = performMove(bestMove);
            if(!performed) {
                System.out.println("Nie wykonany bo był błędny! LOL");
            }
            end = System.currentTimeMillis();
        }
        game.checkForGameEnd();
        if(game.isGameOver()) {
            System.out.println("Gierka skonczona");
            return;
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
            System.out.println("[Ruch MCTS] UseSpell " + move.getCard());
        } else if (move instanceof AttackHero) {
            System.out.println("[Ruch MCTS] AttackHero - cel " + ((AttackHero) move).getHeroToGetAttacked().getName());
        } else if (move instanceof AttackMinion) {
            System.out.println("[Ruch MCTS] AttackMinion - cel " + ((AttackMinion) move).getMinionToGetAttacked().getName());
        } else {
            System.out.println("[Ruch MCTS] EndRound");
        }
    }
}
