package engine.cards.abilities;

import engine.Hero;
import engine.cards.Ability;
import engine.heroes.DefaultHero;

public class BattlecryHealSelfHero implements Ability {

	@Override
	public void performAbility(Hero self, Hero hero2) {
		if(self.getHealth()+8>20)
			self.setHealth(20);
		else
			self.setHealth(self.getHealth()+8);
	}

}
