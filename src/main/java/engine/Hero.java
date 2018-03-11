package engine;

import engine.cards.Minion;
import engine.heroes.AbstractHero;

import java.util.List;

public interface Hero {

    boolean isDead();

    boolean performMove(Move moveToDo);

    void receiveDamage(int damage);

    List<Card> getBoard();

    List<Card> getDeck();

    void deadMinionNotification(Minion minion);

    List<Card> getHand();

    int getMana();

    void decreaseMana(int value);

    void increaseHealth(int value);

    void startRound();

    int getHealth();

    int getPunishForEmptyDeck();

    void generateAvailableMoves();

    List<Move> getAvailableMoves();

    List<Move> getMovesInRound();

    void endRound();

    String getName();
    
    Hero deepCopy();
    
    List<Move> copyMovesTo(AbstractHero target, List<Move> toCopy);

	void setMovesInRound(List<Move> copyMovesTo);

	void setAvailableMoves(List<Move> copyMovesTo);

	void setGame(Game resoult);

   
}
