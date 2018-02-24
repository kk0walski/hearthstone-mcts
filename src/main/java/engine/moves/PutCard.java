package engine.moves;

import engine.Card;
import engine.Hero;
import engine.Move;
import engine.cards.Minion;
import engine.heroes.DefaultHero;

import java.util.List;

public class PutCard implements Move {

    private int cardInHandIndex;
    private Hero self;
    private Hero enymy;
    

    public PutCard(int cardInHandIndex, Hero self, Hero enymy) {
        this.cardInHandIndex = cardInHandIndex;
        this.self=self;
        this.enymy=enymy;
    }

    @Override
    public void performMove() {
         List<Card> hand=self.getHand();
         List<Card> board=self.getBoard();
        board.add(hand.get(cardInHandIndex));
       
        performSpecialAbility(board);
        self.manaDecrease(self.getHand().get(cardInHandIndex).getCost());
        hand.remove(cardInHandIndex);
    }
    
    private void performSpecialAbility(List<Card> board)
    {
    	Minion card=(Minion) board.get(board.size()-1);
    	if(card.getAbility()!=null)
    	card.getAbility().performAbility(self,enymy);
    }

    public int getCardInHandIndex() {
        return cardInHandIndex;
    }

    public void setCardInHandIndex(int cardInHandIndex) {
        this.cardInHandIndex = cardInHandIndex;
    }

	@Override
	public boolean isMovePossible() {
		
		if((self.getMana()) >= (self.getHand().get(cardInHandIndex).getCost()))
			return true;
		else 
			return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cardInHandIndex;
		result = prime * result + ((enymy == null) ? 0 : enymy.hashCode());
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
		if (enymy == null) {
			if (other.enymy != null)
				return false;
		} else if (!enymy.equals(other.enymy))
			return false;
		if (self == null) {
			if (other.self != null)
				return false;
		} else if (!self.equals(other.self))
			return false;
		return true;
	}

  
}
