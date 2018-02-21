package engine.cards;


import engine.Hero;
import engine.heroes.DefaultHero;

public interface Ability {
	public void performAbility(Hero self,Hero enymy);
	
}
