package engine.cards.abilities;

import engine.Hero;
import engine.cards.Ability;
import engine.heroes.DefaultHero;

/**
 * When a minion has Stealth, it is untargettable by any minions, Heroes or Spells until it has attacked a target,
 * which removes it's stealth.
 *
 * A Stealthed minion cannot be silenced as it is untargettable.
 *
 * A Stealthed minion can take damage from Spells that do not target a single minion.
 * Any Spells or minions that deal damage to all minions,
 * or deal damage to random minions can still hit a Stealthed minion.
 */
public class Stealth implements Ability {

	@Override
	public void performAbility(Hero self, Hero hero2) {
		// TODO Auto-generated method stub
		
	}
}
