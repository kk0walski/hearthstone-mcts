package engine.heroes;

import engine.Card;
import engine.Game;
import engine.Hero;
import engine.Move;
import engine.cards.Minion;
import engine.cards.Spell;
import engine.cards.spells.DeadlyShot;
import engine.cards.spells.Fireball;
import engine.cards.spells.HealingTouch;
import engine.moves.*;

import java.util.ArrayList;
import java.util.List;

public class DefaultHero extends AbstractHero {

	public DefaultHero(Game game, String name, List<Card> initialDeck, int initialHandSize) {
		super(game, name, initialDeck, initialHandSize);
		// TODO Auto-generated constructor stub
	}
	
	public DefaultHero() //helpful in copying
	{
		super(null,null,null,-1);
	}
}
