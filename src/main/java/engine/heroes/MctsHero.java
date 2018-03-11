package engine.heroes;

import engine.Card;
import engine.Game;
import engine.Move;
import engine.mcts.MctsAlgorithm;
import engine.mcts.Node;
import engine.moves.EndRound;

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
        MctsAlgorithm mctsAlgorithm = new MctsAlgorithm(new Node(game));
        long start = System.currentTimeMillis();
        long end = start;
        while(end - start <= 40 * 1000) { //game.getActiveHero() == this
            performMove(mctsAlgorithm.run());
            end = System.currentTimeMillis();
        }
        performMove(new EndRound(this));
    }

    @Override
    public int evaluate(Move toDo) {
        return 0;
    }
}
