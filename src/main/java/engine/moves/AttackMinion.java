package engine.moves;

import engine.Card;
import engine.Hero;
import engine.Move;
import engine.cards.Minion;

import java.util.List;

public class AttackMinion implements Move {

    private int cardInBoardIndex;
    private List<Card> board;
    private Minion minionToGetAttacked;

    public AttackMinion(int cardInBoardIndex, List<Card> board, Card minionToGetAttacked) {
        this.cardInBoardIndex = cardInBoardIndex;
        this.board = board;
        this.minionToGetAttacked = (Minion) minionToGetAttacked;
    }

    @Override
    public void performMove() {
        ((Minion) board.get(cardInBoardIndex)).attack(minionToGetAttacked);
    }

	@Override
	public boolean isMovePossible() {
		// TODO Auto-generated method stub
		return true;
	}
}
