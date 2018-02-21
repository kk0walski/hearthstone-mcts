package engine;

import engine.cards.Minion;

import java.util.List;

public interface Hero {

    boolean isDead();
    void performMoves();
    void receiveDamage(int damage);
    List<Card> getBoard();
    List<Card> getDeck();
    void deadMinionNotification(Minion minion);
}
