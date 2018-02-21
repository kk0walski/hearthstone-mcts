package engine.cards.abilities;

import java.util.List;

import engine.Card;
import engine.Hero;
import engine.cards.Ability;
import engine.cards.minions.Tabbycat;
import engine.heroes.DefaultHero;

public class BattleCrySummonTabbycat implements Ability {

	@Override
	public void performAbility(Hero self, Hero hero2) {
		List<Card> temp=self.getBoard();
		if(temp.size()<10)
			temp.add(new Tabbycat());
		
	}
}
