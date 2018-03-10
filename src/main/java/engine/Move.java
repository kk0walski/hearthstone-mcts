package engine;

public interface Move {

    void performMove();

    boolean isMovePossible();

    Card getCard();
   
    int getCardIndex();
    
}