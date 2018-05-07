package engine.cards.abilities;

import engine.Hero;
import engine.cards.Ability;

public class BattlecryHealSelfHero implements Ability {

    public static final int HERO_HEAL_VALUE = 8;

    private int increasedHealth = 0;

    @Override
    public void performAbility(Hero self, Hero enemy) {
        increasedHealth = self.increaseHealth(HERO_HEAL_VALUE);
    }

    @Override
    public void revertAbility(Hero self, Hero enemy) {
        self.decreaseHealth(increasedHealth);
    }
}
