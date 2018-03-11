package engine.heroes;

import java.util.ArrayList;
import java.util.List;

import engine.Card;
import engine.Game;
import engine.Hero;
import engine.Move;
import engine.cards.Minion;
import engine.cards.Spell;
import engine.cards.abilities.BattlecryHealSelfHero;
import engine.cards.minions.AntiqueHealbot;
import engine.cards.spells.DeadlyShot;
import engine.cards.spells.Fireball;
import engine.cards.spells.HealingTouch;
import engine.moves.AttackHero;
import engine.moves.AttackMinion;
import engine.moves.EndRound;
import engine.moves.PutCard;
import engine.moves.UseSpell;

public class AgresiveHero extends AbstractHero implements HeuristicHero {
	
	private static int healthEdge = 4;
	private static int manaEdge = 3;
	private static int lowHealthMul = 100;
	private static int attackHeroMul = 100;
	private static int attackMinionMul = 10;
	private static int cardsMul=10;
	private static int deadlyShotMul=3;
	private static int endRoundMul=1;
	
    public AgresiveHero(Game game, String name, List<Card> initialDeck, int initialHandSize) {
		super(game, name, initialDeck, initialHandSize);
		// TODO Auto-generated constructor stub
	}
    
    public AgresiveHero() {
		super(null,null,null,-1);
	}

	public void chooseHeuristicMove() {
    	Move toDo=null;
    	while (!(toDo instanceof EndRound))
    	{
    		int bestFound = -1;
    		int bestFoundValue = Integer.MIN_VALUE;
    		for(int index=0; index<availableMoves.size(); index++)
    		{
    			int currentValue = evaluate(availableMoves.get(index));
    			if(currentValue > bestFoundValue) {
    				bestFound = index;
    				bestFoundValue = currentValue;
    			}
    		}
    		toDo = availableMoves.get(bestFound);
    		performMove(toDo);
    	}
    }

	@Override
	public int evaluate(Move toDo) {
		if(health <=healthEdge)
		{
			if(toDo instanceof PutCard)
			{
				if(getHand().get(toDo.getCardIndex()) instanceof AntiqueHealbot)
					return BattlecryHealSelfHero.HERO_HEAL_VALUE*lowHealthMul;
			}
			if(toDo instanceof UseSpell)
			{
				if(getHand().get(toDo.getCardIndex()) instanceof HealingTouch)
					return HealingTouch.HERO_HEAL_VALUE*lowHealthMul;
			}
		}
		else
		{
			int boardSize=getBoard().size()==0?1:getBoard().size();
			if(toDo instanceof AttackHero)
			{
				Card card=getBoard().get(toDo.getCardIndex());
				return attackHeroMul*((Minion) card).getAttack() - (1/boardSize)*cardsMul;
			}
			
			if(toDo instanceof AttackMinion)
			{
				Card card=getBoard().get(toDo.getCardIndex());
				return attackMinionMul*((Minion) card).getAttack() - (1/boardSize)*cardsMul;
			}
			
			if(toDo instanceof PutCard)
			{
				Card card=getHand().get(toDo.getCardIndex());
				return attackMinionMul*((Minion) card).getAttack() - card.getCost() + (1/boardSize)*cardsMul;
			}
			
			if(toDo instanceof UseSpell)
			{
				Card card=getHand().get(toDo.getCardIndex());
				if(card instanceof Fireball)
				{
					if(((UseSpell)toDo).getTargetHero() !=null)
						return attackHeroMul*((Fireball) card).DAMAGE_TO_DEAL - card.getCost() -  (1/boardSize)*cardsMul;
					else
						return attackMinionMul*((Fireball) card).DAMAGE_TO_DEAL - card.getCost() -  (1/boardSize)*cardsMul;
				}
				if(card instanceof DeadlyShot)
				{
					return attackMinionMul*deadlyShotMul - card.getCost() - (1/boardSize)*cardsMul;
				}
			}
			
			if(toDo instanceof EndRound)
			{
				if(!anyAttackAvaliable() )
						return endRoundMul;
			}
		}
		
		return -1;
	}
	
	boolean anyAttackAvaliable()
	{
		for(int i=0;i<availableMoves.size();i++)
		{
			if(availableMoves.get(i) instanceof AttackHero || availableMoves.get(i) instanceof AttackMinion)
				return true;
		}
		return false;
	}

	
}
