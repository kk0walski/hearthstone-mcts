package engine.cards.spells;

import engine.Hero;
import engine.cards.Minion;
import engine.cards.Spell;

/**
 * Destroy a random enemy minion.
 */
public class ArcaneShot extends Spell {

    public static final int DAMAGE_TO_DEAL = 2;

    public ArcaneShot() {
        setCost(1);
        setName("ArcaneShot");
    }

    public ArcaneShot(Hero activeHero) {
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
