package engine;

import java.util.List;

public interface Hero {

    boolean isDead();
    void performMoves();
    void receiveDamage(int damage);
    List<Card> getBoard();
}
