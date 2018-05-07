package engine.moves;

import engine.Card;
import engine.Move;
import engine.cards.Minion;

import java.util.List;

public class AttackMinion implements Move {

    private int cardInBoardIndex;
    private List<Card> board;
    private Minion minionToGetAttacked;
    private int minionIndex;

    public AttackMinion(int cardInBoardIndex, List<Card> board, Card minionToGetAttacked) {
        this.cardInBoardIndex = cardInBoardIndex;
        this.board = board;
        this.minionToGetAttacked = (Minion) minionToGetAttacked;
    }

    public AttackMinion(int cardInBoardIndex, List<Card> board, Card minionToGetAttacked, int minionIndex) {
        this.cardInBoardIndex = cardInBoardIndex;
        this.board = board;
        this.minionToGetAttacked = (Minion) minionToGetAttacked;
        this.minionIndex = minionIndex;
    }

    @Override
    public void performMove() {
        ((Minion) board.get(cardInBoardIndex)).attack(minionToGetAttacked);
        ((Minion) board.get(cardInBoardIndex)).deactivate();
    }

    @Override
    public void rollback() {
        ((Minion) board.get(cardInBoardIndex)).revertAttack(minionToGetAttacked);
        ((Minion) board.get(cardInBoardIndex)).activate();
    }

    @Override
    public boolean isMovePossible() {
        return true;
    }

    @Override
    public Card getCard() {
        return board.get(cardInBoardIndex);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((board == null) ? 0 : board.hashCode());
        result = prime * result + cardInBoardIndex;
        result = prime * result + ((minionToGetAttacked == null) ? 0 : minionToGetAttacked.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AttackMinion other = (AttackMinion) obj;
        if (board == null) {
            if (other.board != null)
                return false;
        } else if (!board.equals(other.board))
            return false;
        if (cardInBoardIndex != other.cardInBoardIndex)
            return false;
        if (minionToGetAttacked == null) {
            if (other.minionToGetAttacked != null)
                return false;
        } else if (!minionToGetAttacked.equals(other.minionToGetAttacked))
            return false;
        return true;
    }

    @Override
    public int getCardIndex() {
        return cardInBoardIndex;
    }

    public int getCardInBoardIndex() {
        return cardInBoardIndex;
    }

    public void setCardInBoardIndex(int cardInBoardIndex) {
        this.cardInBoardIndex = cardInBoardIndex;
    }

    public List<Card> getBoard() {
        return board;
    }

    public void setBoard(List<Card> board) {
        this.board = board;
    }

    public Minion getMinionToGetAttacked() {
        return minionToGetAttacked;
    }

    public void setMinionToGetAttacked(Minion minionToGetAttacked) {
        this.minionToGetAttacked = minionToGetAttacked;
    }

    public int getMinionIndex() {
        return minionIndex;
    }

    public void setMinionIndex(int minionIndex) {
        this.minionIndex = minionIndex;
    }
}
