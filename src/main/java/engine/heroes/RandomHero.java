package engine.heroes;

import java.util.List;

import engine.Card;
import engine.Game;
import engine.Move;
import engine.moves.EndRound;

public class RandomHero extends AbstractHero implements HeuristicHero {
	
    public RandomHero(Game game, String name, List<Card> initialDeck, int initialHandSize) {
		super(game, name, initialDeck, initialHandSize);
		// TODO Auto-generated constructor stub
	}
    
    public void chooseHeuristicMove() {
    	Move toDo=null;
    	while (!(toDo instanceof EndRound))
    	{
    		int bestFound = -1;
    		int bestFoundValue = Integer.MIN_VALUE;
    		for(int index=0; index<availableMoves.size(); index++)
    		{
    			int currentValue = evaluate(availableMoves.get(index));
    			if(currentValue > bestFoundValue) {
    				bestFound = index;
    				bestFoundValue = currentValue;
    			}
    		}
    		toDo = availableMoves.get(bestFound);
    		performMove(toDo);
    	}
    }

	public boolean performMove(Move moveToDo) {
        if (availableMoves.contains(moveToDo)) {
            if (moveToDo instanceof EndRound) {
                endRound();
              return true;
            }
            moveToDo.performMove();
            movesInRound.add(moveToDo);
            generateAvailableMoves();
            notifyIfDeadHero();
            return true;
        } else {
            return false;
        }
    }

	@Override
	public int evaluate(Move toDo) {
		return (int) (Math.random()*100);
	}
}
