package engine;

import engine.cards.Minion;

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

    List<Move> getMovesInRound();

    void endRound();

    String getName();

    void revertDamage(int damage);

    void revertDeadMinionNotification(Minion minion);

    void decreaseHealth(int value);

    void increaseMana(int value);

    void restoreMovesInRound();

    void revertStartRound();
}
