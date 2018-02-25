package engine.cards.spells;

import java.util.List;
import java.util.Random;

import engine.Card;
import engine.Hero;
import engine.cards.Minion;
import engine.cards.Spell;

/**
 * Destroy a random enemy minion.
 */
public class DeadlyShot extends Spell {

    public DeadlyShot() {
        setCost(3);
        setName("Deadly Shot");
    }

    @Override
    public void doAction(Hero owner, Hero enemy, Hero targetHero, Minion targetMinion) {
        Random r = new Random();
        Minion toDestroy = (Minion) enemy.getBoard().get(r.nextInt(enemy.getBoard().size()));
        toDestroy.receiveDamage(toDestroy.getHealth());
    }

}
