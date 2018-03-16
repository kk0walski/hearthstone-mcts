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

    Game getGame();

    List<Card> getHand();

    int getMana();

    void decreaseMana(int value);

    int increaseHealth(int value);

    void startRound();

    int getHealth();

    int getPunishForEmptyDeck();

    void generateAvailableMoves();

    List<Move> getAvailableMoves();

    void endRound();

    String getName();

    Hero deepCopy();

    List<Move> copyMovesTo(AbstractHero target, List<Move> toCopy);

    void setAvailableMoves(List<Move> copyMovesTo);

    void setGame(Game resoult);

    void revertDamage(int damage);

    void revertDeadMinionNotification(Minion minion);

    void decreaseHealth(int value);

    void increaseMana(int value);

    void revertStartRound();

    void chooseRandomSimulationalMove();

    void rollback(Move moveToRevert);
}
