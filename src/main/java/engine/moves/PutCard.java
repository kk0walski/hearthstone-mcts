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
        hand.remove(cardInHandIndex);
        performSpecialAbility(board);
        self.manaDecrease(self.getHand().get(cardInHandIndex).getCost());
    }
    
    private void performSpecialAbility(List<Card> board)
    {
    	Minion card=(Minion) board.get(board.size());
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

  
}
