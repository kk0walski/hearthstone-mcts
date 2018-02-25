package engine.cards.spells;


import engine.Hero;
import engine.cards.Minion;
import engine.cards.Spell;

/**
 * Restore n Health.
 */
public class HealingTouch extends Spell {

    public HealingTouch() {
        setCost(3);
        setName("Healing Touch");
    }

    @Override
    public void doAction(Hero owner, Hero enemy, Hero targetHero, Minion targetMinion) {
        if (targetHero != null) {
            targetHero.increaseHealth(8);
        }
        if (targetMinion != null) {
            targetMinion.increaseHealth(8);
        }
    }
}
