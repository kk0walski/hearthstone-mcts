package engine.cards.spells;

import engine.Hero;
import engine.cards.Minion;
import engine.cards.Spell;

/**
 * Deal x damage.
 */
public class Fireball extends Spell {

    public static final int DAMAGE_TO_DEAL = 4;

    public Fireball() {
        setCost(4);
        setName("Fireball");
    }

    public Fireball(Hero activeHero) {
        this();
        setOwner(activeHero);
    }

    @Override
    public void doAction(Hero owner, Hero enemy, Hero targetHero, Minion targetMinion) {
        if (targetHero != null) {
            targetHero.receiveDamage(DAMAGE_TO_DEAL);
        }
        if (targetMinion != null) {
            targetMinion.receiveDamage(DAMAGE_TO_DEAL);
        }
    }

    @Override
    public void revertSpell(Hero owner, Hero enemy, Hero targetHero, Minion targetMinion) {
        if (targetHero != null) {
            targetHero.revertDamage(DAMAGE_TO_DEAL);
        }
        if (targetMinion != null) {
            targetMinion.revertDamage(DAMAGE_TO_DEAL);
        }
    }

}
