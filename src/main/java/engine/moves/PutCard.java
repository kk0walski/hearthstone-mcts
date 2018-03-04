package engine.moves;

import engine.Card;
import engine.Hero;
import engine.Move;
import engine.cards.Minion;

import java.util.List;

public class PutCard implements Move {

    private int cardInHandIndex;
    private Hero self;
    private Hero enemy;


    public PutCard(int cardInHandIndex, Hero self, Hero enemy) {
        this.cardInHandIndex = cardInHandIndex;
        this.self = self;
        this.enemy = enemy;
    }

    @Override
    public void performMove() {
        List<Card> hand = self.getHand();
        List<Card> board = self.getBoard();
        board.add(hand.get(cardInHandIndex));

        performSpecialAbility(board);
        self.decreaseMana(self.getHand().get(cardInHandIndex).getCost());
        hand.remove(cardInHandIndex);
    }

    @Override
    public Card getCard() {
        return self.getHand().get(cardInHandIndex);
    }

    private void performSpecialAbility(List<Card> board) {
        Minion card = (Minion) board.get(board.size() - 1);
        if (card.getAbility() != null)
            card.getAbility().performAbility(self, enemy);
    }

    public int getCardInHandIndex() {
        return cardInHandIndex;
    }

    public void setCardInHandIndex(int cardInHandIndex) {
        this.cardInHandIndex = cardInHandIndex;
    }

    @Override
    public boolean isMovePossible() {

        if ((self.getMana()) >= (self.getHand().get(cardInHandIndex).getCost()))
            return true;
        else
            return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + cardInHandIndex;
        result = prime * result + ((enemy == null) ? 0 : enemy.hashCode());
        result = prime * result + ((self == null) ? 0 : self.hashCode());
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
        PutCard other = (PutCard) obj;
        if (cardInHandIndex != other.cardInHandIndex)
            return false;
        if (enemy == null) {
            if (other.enemy != null)
                return false;
        } else if (!enemy.equals(other.enemy))
            return false;
        if (self == null) {
            if (other.self != null)
                return false;
        } else if (!self.equals(other.self))
            return false;
        return true;
    }
    
    @Override
	public int getCardIndex() {
		// TODO Auto-generated method stub
		return cardInHandIndex;
	}
}
