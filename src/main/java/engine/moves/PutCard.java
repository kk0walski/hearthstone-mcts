package engine.moves;

import engine.Card;
import engine.Move;

import java.util.List;

public class PutCard implements Move {

    private int cardInHandIndex;
    private List<Card> hand;
    private List<Card> board;

    public PutCard(int cardInHandIndex, List<Card> hand, List<Card> board) {
        this.cardInHandIndex = cardInHandIndex;
        this.hand = hand;
        this.board = board;
    }

    @Override
    public void performMove() {
        board.add(hand.get(cardInHandIndex));
        hand.remove(cardInHandIndex);
    }

    public int getCardInHandIndex() {
        return cardInHandIndex;
    }

    public void setCardInHandIndex(int cardInHandIndex) {
        this.cardInHandIndex = cardInHandIndex;
    }

    public List<Card> getBoard() {
        return board;
    }

    public void setBoard(List<Card> board) {
        this.board = board;
    }
}
