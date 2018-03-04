package engine.cards.spells;


import engine.Hero;
import engine.cards.Minion;
import engine.cards.Spell;

/**
 * Restore n Health.
 */
public class HealingTouch extends Spell {

    public static final int HERO_HEAL_VALUE = 8;
	
    public HealingTouch() {
        setCost(3);
        setName("Healing Touch");
    }

    @Override
    public void doAction(Hero owner, Hero enemy, Hero targetHero, Minion targetMinion) {
        if (targetHero != null) {
            targetHero.increaseHealth(HERO_HEAL_VALUE);
        }
        if (targetMinion != null) {
            targetMinion.increaseHealth(HERO_HEAL_VALUE);
        }
    }
}
