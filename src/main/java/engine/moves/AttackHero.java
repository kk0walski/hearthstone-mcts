package engine.moves;

import engine.Card;
import engine.Hero;
import engine.Move;
import engine.cards.Minion;

import java.util.List;

public class AttackHero implements Move {

    private int cardInBoardIndex;
    private List<Card> board;
    private Hero heroToGetAttacked;

    public AttackHero(int cardInBoardIndex, List<Card> board, Hero heroToGetAttacked) {
        this.cardInBoardIndex = cardInBoardIndex;
        this.board = board;
        this.heroToGetAttacked = heroToGetAttacked;
    }

    @Override
    public void performMove() {
        ((Minion) board.get(cardInBoardIndex)).attack(heroToGetAttacked);
    }

	@Override
	public boolean isMovePossible() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((board == null) ? 0 : board.hashCode());
		result = prime * result + cardInBoardIndex;
		result = prime * result + ((heroToGetAttacked == null) ? 0 : heroToGetAttacked.hashCode());
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
		AttackHero other = (AttackHero) obj;
		if (board == null) {
			if (other.board != null)
				return false;
		} else if (!board.equals(other.board))
			return false;
		if (cardInBoardIndex != other.cardInBoardIndex)
			return false;
		if (heroToGetAttacked == null) {
			if (other.heroToGetAttacked != null)
				return false;
		} else if (!heroToGetAttacked.equals(other.heroToGetAttacked))
			return false;
		return true;
	}
	
}
