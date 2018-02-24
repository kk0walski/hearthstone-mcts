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
        
        self.manaDecrease(self.getHand().get(cardInHandIndex).getCost());
        hand.remove(cardInHandIndex);
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
		result = prime * result + ((targetHero == null) ? 0 : targetHero.hashCode());
		result = prime * result + ((targetMinion == null) ? 0 : targetMinion.hashCode());
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
		UseSpell other = (UseSpell) obj;
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
		if (targetHero == null) {
			if (other.targetHero != null)
				return false;
		} else if (!targetHero.equals(other.targetHero))
			return false;
		if (targetMinion == null) {
			if (other.targetMinion != null)
				return false;
		} else if (!targetMinion.equals(other.targetMinion))
			return false;
		return true;
	}
	
	

}
