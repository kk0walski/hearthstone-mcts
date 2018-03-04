package engine.moves;

import java.util.List;

import engine.Card;
import engine.Hero;
import engine.Move;
import engine.cards.Minion;

public class UseSpell implements Move {

    private int cardInHandIndex;
    private Hero self;
    private Hero enemy;

    private Minion targetMinion;//only one is not null, i don't have better idea
    private Hero targetHero;

    public UseSpell(int cardInHandIndex, Hero self, Hero enemy, Minion targetMinion, Hero targetHero) {
        super();
        this.cardInHandIndex = cardInHandIndex;
        this.self = self;
        this.enemy = enemy;
        this.targetMinion = targetMinion;
        this.targetHero = targetHero;
    }

    @Override
    public void performMove() {
        List<Card> hand = self.getHand();
        hand.get(cardInHandIndex).doAction(self, enemy, targetHero, targetMinion);
        self.decreaseMana(self.getHand().get(cardInHandIndex).getCost());
        hand.remove(cardInHandIndex);
    }

    @Override
    public boolean isMovePossible() {
        return (self.getMana()) >= (self.getHand().get(cardInHandIndex).getCost());
    }

    @Override
    public Card getCard() {
        return self.getHand().get(cardInHandIndex);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + cardInHandIndex;
        result = prime * result + ((enemy == null) ? 0 : enemy.hashCode());
        result = prime * result + ((self == null) ? 0 : self.hashCode());
        result = prime * result + ((targetHero == null) ? 0 : targetHero.hashCode());
        result = prime * result + ((targetMinion == null) ? 0 : targetMinion.hashCode());
        return result;
    }

    public Hero getTargetHero()
    {
    	return targetHero;
    }
    public Minion getTargetMinion()
    {
    	return targetMinion;
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

    @Override
 	public int getCardIndex() {
 		// TODO Auto-generated method stub
 		return cardInHandIndex;
 	}
    
}
