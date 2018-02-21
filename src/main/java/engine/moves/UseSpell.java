package engine.moves;

import java.util.List;

import engine.Card;
import engine.Hero;
import engine.Move;
import engine.cards.Minion;

public class UseSpell implements Move  {
    private int cardInHandIndex;
    private Hero self;
    private Hero enymy;
    
    private Minion targetMinion;//only one is not null, i don't have better idea
    private Hero targetHero;
	
	public UseSpell(int cardInHandIndex, Hero self, Hero enymy, Minion targetMinion, Hero targetHero) {
		super();
		this.cardInHandIndex = cardInHandIndex;
		this.self = self;
		this.enymy = enymy;
		this.targetMinion = targetMinion;
		this.targetHero = targetHero;
	}
	@Override
	public void performMove() {
		 List<Card> hand=self.getHand();
		hand.get(cardInHandIndex).doAction(self, enymy, targetHero, targetMinion);
        hand.remove(cardInHandIndex);
        self.manaDecrease(self.getHand().get(cardInHandIndex).getCost());
	}
	@Override
	public boolean isMovePossible() {
		
		if((self.getMana()) >= (self.getHand().get(cardInHandIndex).getCost()))
			return true;
		else 
			return false;
	}
	
	

}
