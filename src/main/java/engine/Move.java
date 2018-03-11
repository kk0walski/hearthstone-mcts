package engine;

public interface Move {

    void performMove();

    void rollback();

    boolean isMovePossible();

    Card getCard();

    int getCardIndex();
    
}