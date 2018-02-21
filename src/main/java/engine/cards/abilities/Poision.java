package engine.cards.abilities;

import engine.Hero;
import engine.cards.Ability;
import engine.heroes.DefaultHero;

/**
 * Poison causes any minion to die if they take damage from the minion that posesses Poison.
 *
 * If a minion has 0 attack and it has Poison, it will not kill the minion that attacked it.
 *
 * Poison cannot penetrate a Divine Shield.
 */
public class Poision implements Ability {

	@Override
	public void performAbility(Hero self, Hero hero2) {
		// TODO Auto-generated method stub
		
	}
}
