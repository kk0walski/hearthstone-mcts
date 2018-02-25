package engine.cards.abilities;

import engine.Hero;
import engine.cards.Ability;
import engine.heroes.DefaultHero;

public class BattlecryHealSelfHero implements Ability {


    public static final int HERO_HEAL_VALUE = 8;

    @Override
    public void performAbility(Hero self, Hero enemy) {
        self.increaseHealth(HERO_HEAL_VALUE);
    }

}
