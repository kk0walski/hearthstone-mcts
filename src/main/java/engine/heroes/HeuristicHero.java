package engine.heroes;

import engine.Move;

public interface HeuristicHero {
	void chooseHeuristicMove();
	int evaluate(Move toDo);
}
